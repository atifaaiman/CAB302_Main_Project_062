
import java.awt.*;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.util.Base64;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.ls.LSOutput;
import org.xml.sax.SAXException;

/**
 * The Class GUIPreview that encapsulates a graphical preview.
 */
public class GUIPreview extends JPanel {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Constant to store number of milliseconds between server connections.
	 */

	//public final static Dimension SCREEN_SIZE = new Dimension(400, 400);          // Old
	static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	private static int screenHeight = screenSize.height;
	private static int  screenWidth = screenSize.width;
	public final static Dimension SCREEN_SIZE = new Dimension(screenWidth-100, screenHeight-120 );

	/** The Constant WIDTH_GAP. */
	public final static double WIDTH_GAP = 0.125;

	/** The Constant HEIGHT_GAP. */
	public final static double HEIGHT_GAP = 0.25;

	/** The main panel. */
	private JPanel mainPnl = new JPanel();

	/** The message label. */
	private JLabel msgLbl = new JLabel("", SwingConstants.CENTER);

	/** The picture label. */
	private JLabel picLbl = new JLabel();

	/** The information label. */
	private JLabel infLbl = new JLabel("", SwingConstants.CENTER);

	/**
	 * Instantiates a new GUI preview.
	 */
	public GUIPreview() {

		setLayout(new BorderLayout());

		setPreferredSize(SCREEN_SIZE);

		mainPnl.setLayout(new GridBagLayout());

		add(mainPnl, BorderLayout.CENTER);
	}

	/**
	 * Parses the XML.
	 *
	 * @param xmlPath the xml path
	 * @return the document
	 * @throws SAXException                 the SAX exception
	 * @throws IOException                  Signals that an I/O exception has
	 *                                      occurred.
	 * @throws ParserConfigurationException the parser configuration exception
	 */
	public Document parseXML(Path xmlPath) throws SAXException, IOException, ParserConfigurationException {
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = dbFactory.newDocumentBuilder();
		Document doc = docBuilder.parse(xmlPath.toFile());
		doc.getDocumentElement().normalize();
		return doc;
	}

	/**
	 * Updates billboard using input Document.
	 *
	 * @param doc the document
	 * @throws MalformedURLException the malformed URL exception
	 * @throws DOMException          the DOM exception
	 */
	public void updateBillboard(Document doc) throws MalformedURLException, DOMException {

		// If the billboard node has a ‘background’ attribute, this will be interpreted
		// as the colour to display for the entire billboard’s background (that is, all
		// parts of the screen that are not text or an image.)
		// If the ‘background’ attribute is not supplied, use an appropriate default.
		String background = doc.getDocumentElement().getAttribute("background");
		if (background != null && !background.isEmpty()) {
			mainPnl.setBackground(Color.decode(background));
		}

		// Define variables to keep track what tags are present inside
		// <billboard>.
		boolean msgAttr = false;
		boolean picAttr = false;
		boolean infAttr = false;

		NodeList nodeList = doc.getDocumentElement().getChildNodes();
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node node = nodeList.item(i);
			if (node.getNodeName().equals("message")) {

				msgAttr = true;

				// Used to show the primary text of the billboard.
				// This text will be displayed in a large font size so it
				// will be clearly visible- however, the text must all fit
				// on one line, with no line breaks.
				msgLbl.setText(node.getTextContent());
				msgLbl.setFont(new Font("Times New Roman", Font.BOLD, 12));

				// If the message node has a ‘colour’ attribute, this will
				// be interpreted as the colour to display the message text using.
				// If the ‘colour’ attribute is not supplied, use an appropriate default.
				Node colour = node.getAttributes().getNamedItem("colour");
				if (colour != null) {
					msgLbl.setForeground(Color.decode(colour.getTextContent()));
				}

				// Used to show a picture, which will be scaled to an appropriate size
				// and displayed on the billboard.
			} else if (node.getNodeName().equals("picture")) {

				picAttr = true;

				// The picture node will have one of two attributes: url and data.
				// A picture node with neither of these attributes is invalid, as
				// is a picture node with both.
				Node url = node.getAttributes().getNamedItem("url");
				Node data = node.getAttributes().getNamedItem("data");
				if (url != null && data == null) {
					URL picURL = new URL(url.getTextContent());
					picLbl.setIcon(new ImageIcon(picURL));
				} else if (data != null && url == null) {
					picLbl.setIcon(new ImageIcon(Base64.getDecoder().decode(data.getTextContent())));
				}
			} else if (node.getNodeName().equals("information")) {

				infAttr = true;

				// Used to show larger amounts of text information. This text can
				// be broken across multiple lines for display purposes. This text
				// should be shown at a smaller font size than the message tag.
				infLbl.setText(node.getTextContent());

				// If the information node has a ‘colour’ attribute, this
				// will be interpreted as the colour to display the information
				// text using. If the ‘colour’ attribute is not supplied, use an appropriate
				// default.
				Node colour = node.getAttributes().getNamedItem("colour");
				if (colour != null) {
					infLbl.setForeground(Color.decode(colour.getTextContent()));
				}
			}
		}

		scale(msgAttr, picAttr, infAttr);
	}

	/**
	 * Scales message, picture and information attributes according to the rules of
	 * their presence.
	 * <p>
	 * <b>Rules:</b>
	 * <ul>
	 * <li>If only <b>message</b> is present, the message should be displayed almost
	 * as large as possible, within the constraints that the text cannot be broken
	 * across multiple lines and it must all fit on the screen.</li>
	 * <li>If only <b>picture</b> is present, the image should be scaled up to half
	 * the width and height of the screen and displayed in the centre. Note that
	 * this scaling up should not distort the aspect ratio of the image. If the
	 * screen is 1000 pixels wide and 750 pixels high, a 100x100 image should be
	 * displayed at 375x375. On the other hand, a 100x50 image should be displayed
	 * at 500x250. In each case the image is scaled, preserving the aspect ratio, to
	 * the largest size that can fit in a 500x375 (50% of the screen’s width and
	 * height) rectangle.</li>
	 * <li>If only <b>information</b> is present, the text should be displayed in
	 * the centre, with word wrapping and font size chosen so that the text fills up
	 * no more than 75% of the screen’s width and 50% of the screen’s height.</li>
	 * <li>If only <b>message</b> and <b>picture</b> are present, the picture should
	 * be the same size as before, but instead of being drawn in the centre of the
	 * screen, it should be drawn in the middle of the bottom 2/3 of the screen. The
	 * message should then be sized to fit in the remaining space between the top of
	 * the image and the top of the screen and placed in the centre of that gap.
	 * </li>
	 * <li>If only <b>message</b> and <b>information</b> are present, the message
	 * text should be sized to fit in the top half of the screen and the information
	 * text sized to fit in the bottom half of the screen.</li>
	 * <li>If only <b>picture</b> and <b>information</b> are present, the picture
	 * should be the same size as before, but instead of being drawn in the centre
	 * of the screen, it should be drawn in the middle of the top 2/3 of the screen.
	 * The information text should then be sized to fit in the remaining space
	 * between the bottom of the image and the bottom of the screen and placed in
	 * the centre of that gap (within the constraint that the information text
	 * should not fill up more than 75% of the screen’s width.)</li>
	 * <li>If <b>message</b>, <b>picture</b> and <b>information</b> are all present,
	 * all three should be drawn. The picture should be drawn in the centre, but
	 * this time at 1/3 of screen width and screen height (once again, scaled to
	 * preserve aspect ratio). The message should be sized and centred to fit in the
	 * gap between the top of the picture and the top of the screen. The information
	 * should be sized and centred to fit in the gap between the bottom of the
	 * picture and the bottom of the screen.</li>
	 * </ul>
	 * </p>
	 * 
	 * @param msgAttr true if message attribute is present, otherwise false
	 * @param picAttr true if picture attribute is present, otherwise false
	 * @param infAttr true if information attribute is present, otherwise false
	 */
	public void scale(boolean msgAttr, boolean picAttr, boolean infAttr) {

		/*
		 * If only message is present.
		 */
		if (msgAttr && !picAttr && !infAttr) {

			msgLbl.setPreferredSize(SCREEN_SIZE);
			int scaledFont = getScaledFont();
			msgLbl.setFont(new Font("Times New Roman", Font.BOLD, scaledFont));
			mainPnl.add(msgLbl);

			/*
			 * If only picture is present.
			 */
		} else if (picAttr && !msgAttr && !infAttr) {

			Image scaledImg = getScaledImage();
			picLbl.setIcon(new ImageIcon(scaledImg));
			mainPnl.add(picLbl);

			/*
			 * If only information is present.
			 */
		} else if (infAttr && !msgAttr && !picAttr) {
			infLbl.setPreferredSize(SCREEN_SIZE);

			String txt = "<html><p style='font-size: xx-large; margin: " + SCREEN_SIZE.getHeight() * HEIGHT_GAP + "px "
					+ SCREEN_SIZE.getWidth() * WIDTH_GAP + "px;'>" + infLbl.getText() + "</p></html>";

			infLbl.setText(txt);
			mainPnl.add(infLbl);

			/*
			 * If only message and picture are present.
			 */
		} else if (msgAttr && picAttr && !infAttr) {
			mainPnl.setLayout(new BorderLayout());
			Image scaledImg = getScaledImage();
			picLbl.setIcon(new ImageIcon(scaledImg));
			JPanel southPnl = new JPanel();
			southPnl.setOpaque(false);
			southPnl.add(picLbl);
			mainPnl.add(southPnl, BorderLayout.CENTER);

			msgLbl.setPreferredSize(new Dimension(SCREEN_SIZE.width, SCREEN_SIZE.height / 3));
			int scaledFont = getScaledFont();
			msgLbl.setFont(new Font("Times New Roman", Font.BOLD, scaledFont));
			mainPnl.add(msgLbl, BorderLayout.NORTH);

			/*
			 * If only message and information are present.
			 */
		} else if (msgAttr && infAttr && !picAttr) {
			mainPnl.setLayout(new BorderLayout());
			infLbl.setPreferredSize(new Dimension(SCREEN_SIZE.width, SCREEN_SIZE.height / 2));

			String txt = "<html><p style='font-size: xx-large; margin: " + SCREEN_SIZE.getHeight() * HEIGHT_GAP + "px "
					+ SCREEN_SIZE.getWidth() * WIDTH_GAP + "px;'>" + infLbl.getText() + "</p></html>";

			infLbl.setText(txt);
			mainPnl.add(infLbl, BorderLayout.SOUTH);

			msgLbl.setPreferredSize(new Dimension(SCREEN_SIZE.width, SCREEN_SIZE.height / 2));
			int scaledFont = getScaledFont();
			msgLbl.setFont(new Font("Times New Roman", Font.BOLD, scaledFont));
			mainPnl.add(msgLbl, BorderLayout.NORTH);

			/*
			 * If only picture and information are present.
			 */
		} else if (picAttr && infAttr && !msgAttr) {
			mainPnl.setLayout(new BorderLayout());
			Image scaledImg = getScaledImage();
			picLbl.setIcon(new ImageIcon(scaledImg));
			JPanel topPnl = new JPanel();
			topPnl.setLayout(new GridBagLayout());
			topPnl.setOpaque(false);
			topPnl.add(picLbl);
			mainPnl.add(topPnl, BorderLayout.CENTER);

			infLbl.setPreferredSize(new Dimension(SCREEN_SIZE.width, SCREEN_SIZE.height / 3));
			String txt = "<html><p style='font-size: xx-large; margin: " + SCREEN_SIZE.getHeight() * HEIGHT_GAP + "px "
					+ SCREEN_SIZE.getWidth() * WIDTH_GAP + "px;'>" + infLbl.getText() + "</p></html>";
			infLbl.setText(txt);
			mainPnl.add(infLbl, BorderLayout.SOUTH);
			/*
			 * If message, picture and information are all present.
			 */
		} else {
			mainPnl.setLayout(new BorderLayout());
			msgLbl.setPreferredSize(new Dimension(SCREEN_SIZE.width, SCREEN_SIZE.height / 3));
			int scaledFont = getScaledFont();
			msgLbl.setFont(new Font("Times New Roman", Font.BOLD, scaledFont));
			mainPnl.add(msgLbl, BorderLayout.NORTH);

			Image scaledImg = getScaledImage();
			picLbl.setIcon(new ImageIcon(scaledImg));
			JPanel topPnl = new JPanel();
			topPnl.setLayout(new GridBagLayout());
			topPnl.setOpaque(false);
			topPnl.add(picLbl);
			mainPnl.add(topPnl, BorderLayout.CENTER);

			infLbl.setPreferredSize(new Dimension(SCREEN_SIZE.width, SCREEN_SIZE.height / 3));
			String txt = "<html><p style='font-size: xx-large; margin: " + SCREEN_SIZE.getHeight() * HEIGHT_GAP + "px "
					+ SCREEN_SIZE.getWidth() * WIDTH_GAP + "px;'>" + infLbl.getText() + "</p></html>";
			infLbl.setText(txt);
			mainPnl.add(infLbl, BorderLayout.SOUTH);
		}

		// Refresh panel UI.
		mainPnl.revalidate();
		mainPnl.updateUI();
	}

	/**
	 * Gets the scaled image.
	 *
	 * @return the scaled image
	 */
	public Image getScaledImage() {
		Image img = ((ImageIcon) picLbl.getIcon()).getImage();
		int imgWidth = img.getWidth(null);
		int imgHeight = img.getHeight(null);
		double halfScreenWidth = SCREEN_SIZE.getWidth() / 2;
		double halfScreenHeight = SCREEN_SIZE.getHeight() / 2;
		double widthRatio = halfScreenWidth / imgWidth;
		double heightRatio = halfScreenHeight / imgHeight;
		if (imgWidth > imgHeight) {
			imgWidth *= widthRatio;
			imgHeight *= widthRatio;
		} else if (imgWidth < imgHeight) {
			imgWidth *= heightRatio;
			imgHeight *= heightRatio;
		} else {
			imgWidth *= widthRatio;
			imgHeight *= heightRatio;
		}

		Image scaledImg = img.getScaledInstance(imgWidth, imgHeight, java.awt.Image.SCALE_SMOOTH);
		return scaledImg;
	}

	/**
	 * Gets the scaled font.
	 *
	 * @return the scaled font
	 */
	public int getScaledFont() {
		Font curFont = msgLbl.getFont();
		String text = msgLbl.getText();

		int textWidth = msgLbl.getFontMetrics(curFont).stringWidth(text);

		int lblWidth = (int) msgLbl.getPreferredSize().getWidth();

		// Calculate width scale.
		double widthScale = (double) lblWidth / (double) textWidth;

		int newSize = (int) (curFont.getSize() * widthScale);
		int componentHeight = (int) msgLbl.getPreferredSize().getHeight();
		int newFontSize = Math.min(newSize, componentHeight);
		return newFontSize - 10;  // Test
	}

	/**
	 * Displays error.
	 *
	 * @param error the error
	 */
	public void displayError(String error) {
		JOptionPane.showMessageDialog(this, error, "Billboard Viewer Failed", JOptionPane.ERROR_MESSAGE);
	}

}
