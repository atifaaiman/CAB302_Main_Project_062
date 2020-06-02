import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.Base64;
import java.util.List;

import javax.imageio.ImageIO;
import javax.sql.rowset.serial.SerialException;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import common.Billboard;

/**
 * The Class BillboardsPanel encapsulates view for Billboard panel where users
 * with permissions {@link common.Permission#EDIT_ALL_BILLBOARDS} and
 * {@link common.Permission#CREATE_BILLBOARDS} are able to view, delete, edit or
 * add {@link common.Billboard}
 */
public class BillboardsPanel extends JPanel {

	/** The logout button. */
	private JButton btnLogout = new JButton("Logout");

	/** The button add billboard. */
	private JButton btnAddBillboard = new JButton("Add Billboard");

	/**
	 * The default color. It's used to check whether the user selects 'colour'
	 * attributes for billboard or not.
	 */
	private final Color DEFAULT_COLOR = getBackground();

	/** The table model all billboards. */
	private DefaultTableModel tblMdlAllBillboards = new DefaultTableModel(
			new String[] { "Name", "Author", "Preview", "Edit", "Delete" }, 0) {

		private static final long serialVersionUID = 1L;

		/*
		 * Prevent editing table cells.
		 */
		@Override
		public boolean isCellEditable(int row, int column) {
			return false;
		}
	};

	/** The table all billboards. Contains all billboards. */
	private JTable tblAllBillboards = new JTable(tblMdlAllBillboards);

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** Stores current billboards, updates every {@link GUI#UPDATE_DELAY}. */
	private List<Billboard> billboards;

	/** The add billboard panel. */
	private JPanel addBillboardPanel = new JPanel();

	/** The button preview. */
	private JButton btnPreview = new JButton("Preview");

	/** The button background. */
	private JButton btnBackground = new JButton("Select background");

	/** The button message colour. */
	private JButton btnMsgColour = new JButton("Select message colour");

	/** The button info colour. */
	private JButton btnInfoColour = new JButton("Select information colour");

	/** The panel info colour. */
	private JPanel pnlInfoColour = new JPanel();

	/** The panel message colour. */
	private JPanel pnlMsgColour = new JPanel();

	/** The panel background. */
	private JPanel pnlBackground = new JPanel();

	/** The text field message text. */
	private JTextField tfMsgText = new JTextField();

	/** The text field info text. */
	private JTextField tfInfoText = new JTextField();

	/** The radio button base 64. */
	private JRadioButton jrbBase64 = new JRadioButton("Base 64", true);

	/** The radio button URL. */
	private JRadioButton jrbURL = new JRadioButton("URL");

	/** The label select image. */
	private JLabel lblSelectImage = new JLabel("Select picture...");

	/** The text field picture URL. */
	private JTextField tfPicURL = new JTextField(15);

	/** The panel picture. */
	private JPanel pnlPicture = new JPanel();

	/** The text field billboard name. */
	private JTextField tfBlbdName = new JTextField();

	/**
	 * The image data (when the user selects image using JFileChooser).
	 */
	private byte[] imgData;

	/**
	 * Instantiates a new billboards panel.
	 */
	public BillboardsPanel() {
		initGUIComponents();
	}

	/**
	 * Initialises the GUI components.
	 */
	private void initGUIComponents() {
		setLayout(new BorderLayout());
		setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		JPanel pnlNorthBillb = new JPanel(new GridLayout(1, 2));
		pnlNorthBillb.add(new JLabel("All Billboards", SwingConstants.LEFT));
		JPanel pnlLogoutBillb = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		pnlLogoutBillb.add(btnLogout);
		pnlNorthBillb.add(pnlLogoutBillb);
		add(pnlNorthBillb, BorderLayout.NORTH);
		add(new JScrollPane(tblAllBillboards), BorderLayout.CENTER);
		JPanel pnlSouthBillb = new JPanel(new FlowLayout(FlowLayout.LEFT));
		pnlSouthBillb.add(btnAddBillboard);
		add(pnlSouthBillb, BorderLayout.SOUTH);

		/*
		 * Init AddBillboard panel.
		 */
		pnlInfoColour.setBackground(DEFAULT_COLOR);
		pnlMsgColour.setBackground(DEFAULT_COLOR);
		pnlBackground.setBackground(DEFAULT_COLOR);
		ButtonGroup bg = new ButtonGroup();
		bg.add(jrbBase64);
		bg.add(jrbURL);

		pnlPicture.add(lblSelectImage);
		pnlPicture.add(tfPicURL);
		pnlPicture.setPreferredSize(new Dimension(100, 40));
		tfPicURL.setVisible(false); // Because base64 by default.

		addBillboardPanel.setLayout(new GridLayout(9, 2, 10, 10));
		addBillboardPanel.add(new JLabel("Enter Billboard name:"));
		addBillboardPanel.add(tfBlbdName);
		addBillboardPanel.add(btnBackground);
		addBillboardPanel.add(pnlBackground);
		addBillboardPanel.add(btnMsgColour);
		addBillboardPanel.add(pnlMsgColour);
		addBillboardPanel.add(btnInfoColour);
		addBillboardPanel.add(pnlInfoColour);
		addBillboardPanel.add(new JLabel("Add message text:"));
		addBillboardPanel.add(tfMsgText);
		addBillboardPanel.add(new JLabel("Add information text:"));
		addBillboardPanel.add(tfInfoText);
		addBillboardPanel.add(jrbBase64);
		addBillboardPanel.add(jrbURL);
		addBillboardPanel.add(new JLabel("Add Picture:"));
		addBillboardPanel.add(pnlPicture);
		addBillboardPanel.add(btnPreview);
		addBillboardPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
	}

	/**
	 * Selects image.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void selectImage() throws IOException {
		JFileChooser chooser = new JFileChooser(".");
		FileNameExtensionFilter filter = new FileNameExtensionFilter("JPG, PNG, BMP Images", "jpg", "png", "bmp");
		chooser.setFileFilter(filter);
		int returnVal = chooser.showOpenDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File img = chooser.getSelectedFile();
			imgData = Files.readAllBytes(img.toPath());

			BufferedImage bi = ImageIO.read(img);
			Image image = bi.getScaledInstance(100, 40, Image.SCALE_SMOOTH);
			lblSelectImage.setIcon(new ImageIcon(image));
			lblSelectImage.setText(null);
		}
	}

	/**
	 * Adds the info colour.
	 */
	public void addInfoColour() {
		Color color = JColorChooser.showDialog(null, "Select Information Colour", Color.LIGHT_GRAY);
		pnlInfoColour.setBackground(color);
	}

	/**
	 * Deletes billboard.
	 *
	 * @param row the row
	 * @return the billboard
	 */
	public Billboard deleteBillboard(int row) {
		return billboards.get(row);
	}

	/**
	 * Adds the message colour.
	 */
	public void addMsgColour() {
		Color color = JColorChooser.showDialog(null, "Select Message Colour", Color.LIGHT_GRAY);
		pnlMsgColour.setBackground(color);
	}

	/**
	 * Adds the background.
	 */
	public void addBackground() {
		Color color = JColorChooser.showDialog(null, "Select Background", Color.LIGHT_GRAY);
		pnlBackground.setBackground(color);
	}

	/**
	 * Shows billboard preview.
	 *
	 * @param row the row where user clicks to preview billboard data
	 * @throws ParserConfigurationException the parser configuration exception
	 * @throws SAXException                 the SAX exception
	 * @throws IOException                  Signals that an I/O exception has
	 *                                      occurred.
	 */
	public void preview(int row) throws ParserConfigurationException, SAXException, IOException {
		Billboard b = billboards.get(row);
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = dbFactory.newDocumentBuilder();
		Document doc = docBuilder.parse(new ByteArrayInputStream(b.getXmlData()));
		doc.getDocumentElement().normalize();
		showPreview(doc);
	}

	/**
	 * Previews the billboard. First, it creates XML document using the data entered
	 * by the user. Once created, it stores the document to temp/billboard.xml file.
	 *
	 * @throws Exception the exception
	 */
	public void preview() throws Exception {

		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = dbFactory.newDocumentBuilder();
		Document doc = docBuilder.newDocument();

		Element rootElement = doc.createElement("billboard");
		doc.appendChild(rootElement);

		int selectedAttributes = 0;

		if (pnlBackground.getBackground() != DEFAULT_COLOR) {
			Attr backgrAttr = doc.createAttribute("background");
			backgrAttr.setValue(String.format("#%02x%02x%02x", pnlBackground.getBackground().getRed(),
					pnlBackground.getBackground().getGreen(), pnlBackground.getBackground().getBlue()));
			rootElement.setAttributeNode(backgrAttr);
		}

		String msgText = tfMsgText.getText().trim();
		if (!msgText.isEmpty()) {
			selectedAttributes++;
			Element msg = doc.createElement("message");
			rootElement.appendChild(msg);
			if (pnlMsgColour.getBackground() != DEFAULT_COLOR) {
				Attr colMsgAttr = doc.createAttribute("colour");
				colMsgAttr.setValue(String.format("#%02x%02x%02x", pnlMsgColour.getBackground().getRed(),
						pnlMsgColour.getBackground().getGreen(), pnlMsgColour.getBackground().getBlue()));
				msg.setAttributeNode(colMsgAttr);
			}
			msg.setTextContent(msgText);
		}

		String infoText = tfInfoText.getText().trim();
		if (!infoText.isEmpty()) {
			selectedAttributes++;
			Element info = doc.createElement("information");
			rootElement.appendChild(info);
			if (pnlInfoColour.getBackground() != DEFAULT_COLOR) {
				Attr colInfoAttr = doc.createAttribute("colour");
				colInfoAttr.setValue(String.format("#%02x%02x%02x", pnlInfoColour.getBackground().getRed(),
						pnlInfoColour.getBackground().getGreen(), pnlInfoColour.getBackground().getBlue()));
				info.setAttributeNode(colInfoAttr);
			}
			info.setTextContent(infoText);
		}

		if (jrbBase64.isSelected()) {
			if (imgData != null) {
				selectedAttributes++;
				Element pic = doc.createElement("picture");
				rootElement.appendChild(pic);
				String base64 = Base64.getEncoder().encodeToString(imgData);
				Attr dataAttr = doc.createAttribute("data");
				dataAttr.setValue(base64);
				pic.setAttributeNode(dataAttr);
			}
		} else {
			String url = tfPicURL.getText().trim();
			if (!url.isEmpty()) {
				selectedAttributes++;
				Element pic = doc.createElement("picture");
				rootElement.appendChild(pic);
				Attr urlAttr = doc.createAttribute("url");
				urlAttr.setValue(url);
				pic.setAttributeNode(urlAttr);
			}
		}

		if (selectedAttributes == 0) {
			throw new Exception("Select at least 1 attribute!");
		}

		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource source = new DOMSource(doc);
		StreamResult result = new StreamResult(new File("temp/billboard.xml"));
		transformer.transform(source, result);

		lblSelectImage.setText("");

		showPreview(doc);
	}

	/**
	 * Shows preview. Creates instance of {@link GUIPreview} and shows it as a
	 * dialog box.
	 *
	 * @param doc the doc
	 * @throws MalformedURLException the malformed URL exception
	 * @throws DOMException          the DOM exception
	 */
	private void showPreview(Document doc) throws MalformedURLException, DOMException {
		GUIPreview guiPreview = new GUIPreview();
		guiPreview.updateBillboard(doc);
		guiPreview.revalidate();
		guiPreview.updateUI();
		JOptionPane.showMessageDialog(null, guiPreview);
	}

	/**
	 * Edits the billboard.
	 *
	 * @param row the row where user clicks to select required billboard entry
	 * @return the billboard to be edited
	 * @throws IOException                  Signals that an I/O exception has
	 *                                      occurred.
	 * @throws ParserConfigurationException the parser configuration exception
	 * @throws SAXException                 the SAX exception
	 */
	public Billboard editBillboard(int row) throws IOException, ParserConfigurationException, SAXException {
		Billboard blbrd = billboards.get(row);
		tfBlbdName.setText(blbrd.getName());

		tfBlbdName.setEditable(false); // Billboard name is unique and cannot be edited.
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = dbFactory.newDocumentBuilder();
		Document doc = docBuilder.parse(new ByteArrayInputStream(blbrd.getXmlData()));
		doc.getDocumentElement().normalize();
		String background = doc.getDocumentElement().getAttribute("background");
		if (background != null && !background.isEmpty()) {
			pnlBackground.setBackground(Color.decode(background));
		}
		NodeList nodeList = doc.getDocumentElement().getChildNodes();
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node node = nodeList.item(i);
			if (node.getNodeName().equals("message")) { // If <message> attribute is present.
				tfMsgText.setText(node.getTextContent());
				Node color = node.getAttributes().getNamedItem("colour");
				if (color != null) {
					pnlMsgColour.setBackground(Color.decode(color.getTextContent()));
				}
			} else if (node.getNodeName().equals("information")) { // If <information> attribute is present.
				tfInfoText.setText(node.getTextContent());
				Node color = node.getAttributes().getNamedItem("colour");
				if (color != null) {
					pnlInfoColour.setBackground(Color.decode(color.getTextContent()));
				}
			} else if (node.getNodeName().equals("picture")) { // If <picture> attribute is present.
				Node url = node.getAttributes().getNamedItem("url");
				Node data = node.getAttributes().getNamedItem("data");
				if (url != null && data == null) {
					URL picURL = new URL(url.getTextContent());

					BufferedImage bi = ImageIO.read(picURL);
					Image image = bi.getScaledInstance(100, 40, Image.SCALE_SMOOTH);
					lblSelectImage.setIcon(new ImageIcon(image));
					lblSelectImage.setText(null);
				} else if (data != null && url == null) {

					BufferedImage bi = ImageIO.read(
							new ByteArrayInputStream(imgData = Base64.getDecoder().decode(data.getTextContent())));
					Image image = bi.getScaledInstance(100, 40, Image.SCALE_SMOOTH);
					lblSelectImage.setIcon(new ImageIcon(image));
					lblSelectImage.setText(null);
				}
			}
		}
		int edit = JOptionPane.showConfirmDialog(this, addBillboardPanel, "Edit Billboard",
				JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
		if (edit == 0) { // If OK.

			blbrd.setXmlData(Files.readAllBytes(Paths.get("temp/billboard.xml")));

		}
		tfBlbdName.setEditable(true);
		return blbrd;
	}

	/**
	 * Adds the new {@link common.Billboard}.
	 *
	 * @return the billboard to be added
	 * @throws SerialException the serial exception
	 * @throws SQLException    the SQL exception
	 * @throws IOException     Signals that an I/O exception has occurred.
	 */
	public Billboard addBuilboard() throws SerialException, SQLException, IOException {

		Billboard billboard = null;
		int add = JOptionPane.showConfirmDialog(this, addBillboardPanel, "Add Billboard", JOptionPane.OK_CANCEL_OPTION,
				JOptionPane.QUESTION_MESSAGE);
		if (add == 0) { // If OK.
			String blbdName = tfBlbdName.getText().trim();
			if (blbdName.isEmpty()) {
				GUI.displayError("Enter name!");
				return null;
			}
			billboard = new Billboard();
			billboard.setXmlData(Files.readAllBytes(Paths.get("temp/billboard.xml")));
			billboard.setName(blbdName);
		}

		return billboard;
	}

	/**
	 * Updates tables with all billboards. This method is invoked by timer in
	 * {@link Controller} class every {@link GUI#UPDATE_DELAY}.
	 *
	 * @param billboards the billboards list to be iterated
	 */
	public void updateTable(List<Billboard> billboards) {
		this.billboards = billboards;
		tblMdlAllBillboards.setRowCount(0);
		for (Billboard b : billboards) {
			tblMdlAllBillboards.addRow(new Object[] { b.getName(), b.getUsername(), "Prevew", "Edit", "Delete" });
		}
		revalidate();
		updateUI();
	}

	/**
	 * Gets the button logout.
	 *
	 * @return the button logout
	 */
	public JButton getBtnLogout() {
		return btnLogout;
	}

	/**
	 * Gets the button add billboard.
	 *
	 * @return the button add billboard
	 */
	public JButton getBtnAddBillboard() {
		return btnAddBillboard;
	}

	/**
	 * Gets the button preview.
	 *
	 * @return the button preview
	 */
	public JButton getBtnPreview() {
		return btnPreview;
	}

	/**
	 * Gets the button background.
	 *
	 * @return the button background
	 */
	public JButton getBtnBackground() {
		return btnBackground;
	}

	/**
	 * Gets the button message colour.
	 *
	 * @return the button message colour
	 */
	public JButton getBtnMsgColour() {
		return btnMsgColour;
	}

	/**
	 * Gets the button info colour.
	 *
	 * @return the button info colour
	 */
	public JButton getBtnInfoColour() {
		return btnInfoColour;
	}

	/**
	 * Gets the radio button base 64.
	 *
	 * @return the radio button base 64
	 */
	public JRadioButton getJrbBase64() {
		return jrbBase64;
	}

	/**
	 * Gets the radio button URL.
	 *
	 * @return the radio button URL
	 */
	public JRadioButton getJrbURL() {
		return jrbURL;
	}

	/**
	 * Gets the label select image.
	 *
	 * @return the label select image
	 */
	public JLabel getLblSelectImage() {
		return lblSelectImage;
	}

	/**
	 * Gets the text field picture URL.
	 *
	 * @return the text field picture URL
	 */
	public JTextField getTfPicURL() {
		return tfPicURL;
	}

	/**
	 * Gets the adds the billboard panel.
	 *
	 * @return the adds the billboard panel
	 */
	public JPanel getAddBillboardPanel() {
		return addBillboardPanel;
	}

	/**
	 * Gets the table all billboards.
	 *
	 * @return the table all billboards
	 */
	public JTable getTblAllBillboards() {
		return tblAllBillboards;
	}

	/**
	 * Gets the panel picture. This getter is required to add listener to show
	 * JFileChooser when the user clicks on this panel.
	 *
	 * @return the panel picture
	 */
	public JPanel getPnlPicture() {
		return pnlPicture;
	}

}
