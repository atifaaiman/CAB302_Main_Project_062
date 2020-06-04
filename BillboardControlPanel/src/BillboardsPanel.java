import common.Billboard;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.imageio.ImageIO;
import javax.sql.rowset.serial.SerialException;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.Base64;
import java.util.List;

/**
 * The Class BillboardsPanel encapsulates view for Billboard panel where users
 * with permissions {@link common.Permission#EDIT_ALL_BILLBOARDS} and
 * {@link common.Permission#CREATE_BILLBOARDS} are able to view, delete, edit or
 * add {@link common.Billboard}
 */
public class BillboardsPanel extends JPanel {

	/** Billboard Panel components Main Panel*/
	private final JButton btnLogout 			= new JButton("Logout");
	private final JButton btnHome 				= new JButton("Home");
	private final JButton btnAddBillboard 		= new JButton("Add  ");
	private final JButton btnShowBillboards		= new JButton("Show Billboards");
	private final JButton btnEditBillboard 		= new JButton("Edit");
	private final JButton btnDeleteBillboard 	= new JButton("Delete");
	private final JButton btnPreviewBillboard 	= new JButton("Preview");

	/**
	 * The default color. It's used to check whether the user selects 'colour'
	 * attributes for billboard or not.
	 */
	private final Color DEFAULT_COLOR = getBackground();

	/** The table model all billboards. */
	private final DefaultTableModel tblMdlAllBillboards = new DefaultTableModel(
			new String[] { "Name", "Author", "Date", "Time" }, 0) {

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
	private final JTable tblAllBillboards = new JTable(tblMdlAllBillboards);

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** Stores current billboards, updates when click "Update List" button. */
	private List<Billboard> billboards;

	/** Billboard Create Panel components Edit Billboard Panel*/
	private final JPanel addBillboardPanel 	= new JPanel();
	private final JButton btnPreview 		= new JButton("Preview");
	private final JButton btnBackground 	= new JButton("Background Colour");
	private final JButton btnMsgColour 		= new JButton ("Text Colour");
	private final JButton btnInfoColour		= new JButton("Text Colour");
	private final JButton btnAddImage		= new JButton("Add ");
	private final JButton btnDeleteImage	= new JButton("Delete");
	private final JButton btnImportXml		= new JButton("Import XML");
	private final JButton btnExportXml		= new JButton("Export XML");
	private final JPanel pnlInfoColour 		= new JPanel();
	private final JPanel pnlMsgColour 		= new JPanel();
	private final JPanel pnlBackground		= new JPanel();
	private final JPanel pnlGap				= new JPanel();
	private final JTextField tfMsgText 		= new JTextField(15);
	private final JTextField tfInfoText 	= new JTextField(15);
	private final JRadioButton jrbBase64 	= new JRadioButton("Image", true);
	private final JRadioButton jrbURL 		= new JRadioButton("URL");
	private final JLabel lblSelectImage		= new JLabel("");
	private final JTextField tfPicURL 		= new JTextField(45);
	private final JPanel pnlPicture 		= new JPanel();
	private final JTextField tfBlbdName		= new JTextField(15);
	private byte[] imgData;
	private int imgWidth = 220;
	private int imgHeight = 100;

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

		//--------------------------------------------------------------------------------------------------------------
		/*
		* MAIN BILLBOARD PANEL
		*/
		//--------------------------------------------------------------------------------------------------------------
		setLayout(new BorderLayout());
		setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		// NORTH
		JPanel pnlNorthBillb = new JPanel(new GridLayout(1, 2));
		pnlNorthBillb.add(new JLabel("Billboards", SwingConstants.LEFT));
		JPanel pnlLogoutBillb = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		pnlLogoutBillb.add(btnHome);
		pnlLogoutBillb.add(btnLogout);
		pnlNorthBillb.add(pnlLogoutBillb);
		add(pnlNorthBillb, BorderLayout.NORTH);
		// CENTER
		add(new JScrollPane(tblAllBillboards), BorderLayout.CENTER);
		tblAllBillboards.getTableHeader().setReorderingAllowed(false);

		// SOUTH
		Box boxSouth = Box.createHorizontalBox();
		JPanel pnlLeftSouth = new JPanel(new FlowLayout(FlowLayout.CENTER));
		pnlLeftSouth.add(btnShowBillboards);
		pnlLeftSouth.add(btnAddBillboard);
		pnlLeftSouth.add(btnImportXml);
		pnlLeftSouth.add(btnExportXml);
		pnlLeftSouth.add(btnEditBillboard);
		pnlLeftSouth.add(btnPreviewBillboard);
		pnlLeftSouth.add(btnDeleteBillboard);
		boxSouth.add(pnlLeftSouth);
		add(boxSouth, BorderLayout.SOUTH);

		//--------------------------------------------------------------------------------------------------------------
		/*
		 * Init AddBillboard panel.
		 */
		//--------------------------------------------------------------------------------------------------------------
		Box mainPanel = Box.createVerticalBox();

		JPanel panel1 = new JPanel();
		panel1.setLayout(new FlowLayout(FlowLayout.LEFT));
		JPanel panel11 = new JPanel();
		panel11.setLayout(new FlowLayout(FlowLayout.LEFT,52,10));
		panel11.add(new JLabel("Billboard Name:"));
		JPanel panel12 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		panel12.add(tfBlbdName);
		panel12.add(pnlBackground);
		JPanel panel13= new JPanel(new FlowLayout(FlowLayout.RIGHT));
		panel13.add(btnBackground);
		panel1.add(panel11);
		panel1.add(panel12);
		panel1.add(panel13);

		JPanel panel2 = new JPanel();
		JPanel panel21 = new JPanel();
		panel21.setLayout(new FlowLayout(FlowLayout.LEFT, 50, 10));
		panel21.add(new JLabel("Message:"));
		JPanel panel22 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		panel22.add(tfMsgText);
		panel22.add(pnlMsgColour);
		JPanel panel23= new JPanel(new FlowLayout(FlowLayout.RIGHT));
		panel23.add(btnMsgColour);
		panel2.add(panel21);
		panel2.add(panel22);
		panel2.add(panel23);

		JPanel panel3 = new JPanel();
		JPanel panel31 = new JPanel();
		panel31.setLayout(new FlowLayout(FlowLayout.LEFT, 40, 10));
		panel31.add(new JLabel("Information:"));
		JPanel panel32 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		panel32.add(tfInfoText);
		panel32.add(pnlInfoColour);
		JPanel panel33= new JPanel(new FlowLayout(FlowLayout.RIGHT));
		panel33.add(btnInfoColour);
		panel3.add(panel31);
		panel3.add(panel32);
		panel3.add(panel33);

		JPanel panel4 = new JPanel();
		panel4.setLayout(new FlowLayout(FlowLayout.LEFT));
		panel4.setBorder(BorderFactory.createDashedBorder(Color.darkGray));
		panel4.setBorder(BorderFactory.createTitledBorder("Image:"));
		panel4.add(jrbBase64);
		panel4.add(pnlGap);
		panel4.add(lblSelectImage);
		panel4.add(pnlPicture);
		panel4.add(btnAddImage);
		panel4.add(btnDeleteImage);

		JPanel panel5 = new JPanel();
		panel5.setLayout(new FlowLayout(FlowLayout.LEFT));
		panel5.setBorder(BorderFactory.createDashedBorder(Color.darkGray));
		panel5.setBorder(BorderFactory.createTitledBorder("URL:"));
		panel5.add(jrbURL);
		panel5.add(tfPicURL);

		JPanel panel6 = new JPanel();
		panel6.setLayout(new FlowLayout(FlowLayout.RIGHT));
		panel6.add(btnPreview);

		setDefaultColors();
		mainPanel.add(panel1);
		mainPanel.add(panel2);
		mainPanel.add(panel3);
		mainPanel.add(panel4);
		mainPanel.add(panel5);
		mainPanel.add(panel6);
		addBillboardPanel.add(mainPanel);

		//--------------------------------------------------------------------------------------------------------------
		// Components setup
		//--------------------------------------------------------------------------------------------------------------
		pnlGap.setPreferredSize	(new Dimension (110,18));
		pnlInfoColour.setPreferredSize	(new Dimension (40,18));
		pnlMsgColour.setPreferredSize	(new Dimension (40,18));
		pnlBackground.setPreferredSize	(new Dimension (40,18));
		ButtonGroup bg = new ButtonGroup();
		bg.add(jrbBase64);
		bg.add(jrbURL);
		setDefaultImage();
		btnDeleteImage.addActionListener(e -> setDefaultImage());
		disableButtons();

	}

	/**
	 * Set default colour for the gui interface.
	 */
	public void setDefaultColors(){
		pnlGap.setBackground(DEFAULT_COLOR);
		pnlBackground.setBackground(Color.white);
		pnlMsgColour.setBackground (Color.gray);
		pnlInfoColour.setBackground(Color.DARK_GRAY);
	}

	/**
	 * Set disable buttons if no row is selected.
	 */
	public void disableButtons(){
		btnDeleteBillboard.setEnabled(false);
		btnEditBillboard.setEnabled(false);
		btnPreviewBillboard.setEnabled(false);
		btnExportXml.setEnabled(false);
	}

	/**
	 * Selects image.
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void selectImage() throws IOException {
		JFileChooser chooser = new JFileChooser(".");
		FileNameExtensionFilter filter = new FileNameExtensionFilter("JPG, PNG, BMP Images",
				"jpg", "png", "bmp");
		chooser.setFileFilter(filter);
		int returnVal = chooser.showOpenDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File img = chooser.getSelectedFile();
			imgData = Files.readAllBytes(img.toPath());
			BufferedImage bi = ImageIO.read(img);
			Image image = bi.getScaledInstance(imgWidth, imgHeight, Image.SCALE_SMOOTH);
			lblSelectImage.setIcon(new ImageIcon(image));
			btnAddImage.setText("Change");
			//lblSelectImage.setText(null);
		}
	}

	/**
	 * Change for default picture image.
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void setDefaultImage() {
		try{
			BufferedImage bi = ImageIO.read(new File("./pictureIcon.png"));
			Image image = bi.getScaledInstance(imgWidth, imgHeight, Image.SCALE_SMOOTH);
			lblSelectImage.setIcon(new ImageIcon(image));
			btnAddImage.setText("Add");
			imgData = null;
		} catch (IOException e) {
			System.out.println(e.getMessage());
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
	 * @param row the row
	 * @return the billboard
	 */
	public Billboard deleteBillboard(int row) {
		disableButtons();
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
	 * @throws Exception the exception
	 */
	public void preview() throws Exception {
		Document doc = createXML();
		showPreview(doc);
	}

	/**
	 * Creates XML document using the data entered by the use rin the Add Billboard Panel,
	 * it stores the document to temp/billboard.xml file.
	 * @throws Exception the exception
	 */
	public Document createXML() throws Exception {

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

		lblSelectImage.setText("");
		return doc;
	}

	/**
	 * It stores the document to temp/billboard.xml file.
	 * @throws TransformerException
	 */
	public void setTempXML(Document doc) throws TransformerException {
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource source = new DOMSource(doc);
		StreamResult result = new StreamResult(new File("temp/billboard.xml"));
		transformer.transform(source, result);
	}

	/**
	 * Shows preview. Creates instance of {@link GUIPreview} and shows it as a
	 * dialog box.
	 * @param doc the doc
	 * @throws MalformedURLException the malformed URL exception
	 * @throws DOMException          the DOM exception
	 */
	private void showPreview(Document doc) throws MalformedURLException, DOMException {
		GUIPreview guiPreview = new GUIPreview();
		guiPreview.updateBillboard(doc);
		guiPreview.revalidate();
		guiPreview.updateUI();
		JOptionPane.showMessageDialog(null, guiPreview, "Billboard Preview",JOptionPane.PLAIN_MESSAGE );
		disableButtons();
	}

	/**
	 * Edits the billboard.
	 * @param row the row where user clicks to select required billboard entry
	 * @return the billboard to be edited
	 * @throws IOException                  Signals that an I/O exception has
	 *                                      occurred.
	 * @throws ParserConfigurationException the parser configuration exception
	 * @throws SAXException                 the SAX exception
	 */
	public Billboard editBillboard(int row) throws IOException, ParserConfigurationException, SAXException {
		jrbBase64.setSelected(true);
		lblSelectImage.setVisible(true);
		tfPicURL.setVisible(false);
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
					Image image = bi.getScaledInstance(imgWidth, imgHeight, Image.SCALE_SMOOTH);
					lblSelectImage.setIcon(new ImageIcon(image));
					lblSelectImage.setText(null);
				} else if (data != null && url == null) {

					BufferedImage bi = ImageIO.read(
							new ByteArrayInputStream(imgData = Base64.getDecoder().decode(data.getTextContent())));
					Image image = bi.getScaledInstance(imgWidth, imgHeight, Image.SCALE_SMOOTH);
					lblSelectImage.setIcon(new ImageIcon(image));
					lblSelectImage.setText(null);
				}
			}
		}
		int edit = JOptionPane.showConfirmDialog(this, addBillboardPanel, "Edit Billboard",
				JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

		// If clicked in OK.
		if (edit == 0) {
			try{
				Document doc1 = createXML();
				setTempXML(doc1);  				// test
				blbrd.setXmlData(Files.readAllBytes(Paths.get("temp/billboard.xml")));
			} catch (Exception e){
				System.out.println(e.getMessage());

			}
		}
		disableButtons();
		tfBlbdName.setEditable(true);
		//tfPicURL.setVisible(false);
		tfBlbdName.setText("");
		tfMsgText.setText("");
		tfInfoText.setText("");
		setDefaultImage();
		jrbBase64.setSelected(true);
		return blbrd;
	}

//------------------------------------------------- XML ----------------------------------------------------------------
	/**
	 * Export XML to user's computer
	 */
	public void exportXML(int row) throws ParserConfigurationException, IOException, SAXException, TransformerException {

		Billboard billboard = billboards.get(row);

		// Get xnl file from billboard
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = dbFactory.newDocumentBuilder();
		Document doc = docBuilder.parse(new ByteArrayInputStream(billboard.getXmlData()));
		doc.getDocumentElement().normalize();

		// Transform xml into string
		DOMSource domSource = new DOMSource(doc);
		Transformer transformer = TransformerFactory.newInstance().newTransformer();
		StringWriter sw = new StringWriter();
		StreamResult sr = new StreamResult(sw);
		transformer.transform(domSource, sr);
		String xmlString = sw.toString();

		// Open the save file Window
		JFileChooser fileChooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("XML files","xml");
		fileChooser.setFileFilter(filter);
		fileChooser.setDialogTitle("Save XML file");
		int userSelection = fileChooser.showSaveDialog(this);
		if (userSelection == JFileChooser.APPROVE_OPTION) {
			File fileToSave = fileChooser.getSelectedFile();

			// Check if the file already exist
			boolean exists = new File(fileToSave.getAbsolutePath()+".xml").exists();

			// If file does not exist
			if (!exists){
				FileWriter newFile = new FileWriter(fileToSave.getAbsolutePath()+".xml");
				newFile.write(xmlString);
				newFile.close();

			}// If file exists
			else {
				JOptionPane.showMessageDialog(new JFrame(), "File already exit!\nPlease choose another file name.", "Error Message",
						JOptionPane.ERROR_MESSAGE);
			}
		}
}

	/**
	 * Import XML from the user computer
	 */
	public Billboard importXML()  {

		JFileChooser chooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("XML files","xml");
		chooser.setFileFilter(filter);
		Document doc;
		Billboard billboard = null;

		int returnVal = chooser.showOpenDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File xml = chooser.getSelectedFile();
			//System.out.println(xml);
			try{
				DocumentBuilderFactory dbfac = DocumentBuilderFactory.newInstance();
				DocumentBuilder docBuilder = dbfac.newDocumentBuilder();
				doc = docBuilder.parse(xml);
				setTempXML(doc);
				billboard = addXml(doc);
				return billboard;
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Invalid XML format!");
			}
		}
		return billboard;
	}

	/**
	 * Add XML in the billboard.
	 * @return the billboard to be edited
	 * @throws IOException                  Signals that an I/O exception has
	 *                                      occurred.
	 * @throws ParserConfigurationException the parser configuration exception
	 * @throws SAXException                 the SAX exception
	 */
	public Billboard addXml(Document doc) throws IOException, ParserConfigurationException, SAXException {
		jrbBase64.setSelected(true);
		lblSelectImage.setVisible(true);
		Billboard billboard = new Billboard();
		tfBlbdName.setText("");

		doc.getDocumentElement().normalize();
		String background = doc.getDocumentElement().getAttribute("background");
		if (background != null && !background.isEmpty()) {
			pnlBackground.setBackground(Color.decode(background));
		}
		NodeList nodeList = doc.getDocumentElement().getChildNodes();
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node node = nodeList.item(i);
			// If <message> attribute is present.
			if (node.getNodeName().equals("message")) {
				tfMsgText.setText(node.getTextContent());
				Node color = node.getAttributes().getNamedItem("colour");
				if (color != null) {
					pnlMsgColour.setBackground(Color.decode(color.getTextContent()));
				}
			}// If <information> attribute is present.
			else if (node.getNodeName().equals("information")) {
				tfInfoText.setText(node.getTextContent());
				Node color = node.getAttributes().getNamedItem("colour");
				if (color != null) {
					pnlInfoColour.setBackground(Color.decode(color.getTextContent()));
				}
			}// If <picture> attribute is present.
			else if (node.getNodeName().equals("picture")) {
				Node url = node.getAttributes().getNamedItem("url");
				Node data = node.getAttributes().getNamedItem("data");
				if (url != null && data == null) {
					URL picURL = new URL(url.getTextContent());

					tfPicURL.setText(String.valueOf(picURL));
					tfPicURL.setVisible(true);
					jrbURL.setSelected(true);

				} else if (data != null && url == null) {

					BufferedImage bi = ImageIO.read(
							new ByteArrayInputStream(imgData = Base64.getDecoder().decode(data.getTextContent())));
					Image image = bi.getScaledInstance(imgWidth, imgHeight, Image.SCALE_SMOOTH);
					lblSelectImage.setIcon(new ImageIcon(image));
					lblSelectImage.setText(null);
					jrbBase64.setSelected(true);
				}
			}
		}
		int add = JOptionPane.showConfirmDialog(this, addBillboardPanel, "Add Billboard",
				JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

		// If user clicked OK.
		if (add == 0) {
			String blbdName = tfBlbdName.getText().trim();
			if (blbdName.isEmpty()) {
				GUI.displayError("Enter billboard name!");
				return null;
			}
			try {
				Document doc1 = createXML();
				setTempXML(doc1);  			// test
				billboard.setXmlData(Files.readAllBytes(Paths.get("temp/billboard.xml")));
				billboard.setName(blbdName);
			} catch (Exception e) {
				System.out.println(e.getMessage());;
			}

		}
		disableButtons();
		tfBlbdName.setText("");
		tfMsgText.setText("");
		tfInfoText.setText("");
		setDefaultImage();
		jrbBase64.setSelected(true);
		return billboard;
	}


//----------------------------------------------------------------------------------------------------------------------
	/**
	 * Adds the new {@link common.Billboard}.
	 *
	 * @return the billboard to be added
	 * @throws SerialException the serial exception
	 * @throws SQLException    the SQL exception
	 * @throws IOException     Signals that an I/O exception has occurred.
	 */
	public Billboard addBuilboard() throws SerialException, SQLException, IOException {
		setDefaultColors();
		jrbBase64.setSelected(true);
		lblSelectImage.setVisible(true);
		tfPicURL.setVisible(false);
		tfBlbdName.setText("");
		tfMsgText.setText("");
		tfInfoText.setText("");

		Billboard billboard = null;

		int add = JOptionPane.showConfirmDialog(this, addBillboardPanel, "Add Billboard",
				JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

		// If user clicked OK.
		if (add == 0) {
			String blbdName = tfBlbdName.getText().trim();
			if (blbdName.isEmpty()) {
				GUI.displayError("Enter billboard name!");
				return null;
			}
			billboard = new Billboard();
			try {
				Document doc = createXML();
				setTempXML(doc);  			// test
				//System.out.println("XML: " + doc);
				billboard.setXmlData(Files.readAllBytes(Paths.get("temp/billboard.xml")));
				billboard.setName(blbdName);
			} catch (Exception e) {
				System.out.println(e.getMessage());;
			}

		}
		disableButtons();
		tfBlbdName.setText("");
		tfMsgText.setText("");
		tfInfoText.setText("");
		setDefaultImage();
		jrbBase64.setSelected(true);
		return billboard;
	}

	/**
	 * Updates tables with all billboards.
	 * @param billboards the billboards list to be iterated
	 */
	public void updateTable(List<Billboard> billboards) {
		this.billboards = billboards;
		tblMdlAllBillboards.setRowCount(0);
		for (Billboard b : billboards) {
			// Format date and time
			String data = b.getDataTime();
			String dataSplit[] = data.split(" ");
			String time = dataSplit[1];
			String dateSplit[] = dataSplit[0].split("-");
			String date = dateSplit[2]+"/"+dateSplit[1]+"/"+dateSplit[0];
			tblMdlAllBillboards.addRow(new Object[] { b.getName(), b.getUsername(), date, time });
		}
		revalidate();
		updateUI();
	}

	/**
	 * Gets the button logout.
	 * @return the button logout
	 */
	public JButton getBtnLogout() {
		return btnLogout;
	}

	/**
	 * Gets the button add billboard.
	 * @return the button add billboard
	 */
	public JButton getBtnAddBillboard() {
		return btnAddBillboard;
	}

	/**
	 * Gets the button preview.
	 * @return the button preview
	 */
	public JButton getBtnPreview() {
		return btnPreview;
	}

	/**
	 * Gets the button background.
	 * @return the button background
	 */
	public JButton getBtnBackground() {
		return btnBackground;
	}

	/**
	 * Gets the button message colour.
	 * @return the button message colour
	 */
	public JButton getBtnMsgColour() {
		return btnMsgColour;
	}

	/**
	 * Gets the button info colour.
	 * @return the button info colour
	 */
	public JButton getBtnInfoColour() {
		return btnInfoColour;
	}

	/**
	 * Gets the radio button base 64.
	 * @return the radio button base 64
	 */
	public JRadioButton getJrbBase64() {
		return jrbBase64;
	}

	/**
	 * Gets the radio button URL.
	 * @return the radio button URL
	 */
	public JRadioButton getJrbURL() {
		return jrbURL;
	}

	/**
	 * Gets the label select image.
	 * @return the label select image
	 */
	public JLabel getLblSelectImage() {
		return lblSelectImage;
	}

	/**
	 * Gets the text field picture URL.
	 * @return the text field picture URL
	 */
	public JTextField getTfPicURL() {
		return tfPicURL;
	}

	/**
	 * Gets the adds the billboard panel.
	 * @return the adds the billboard panel
	 */
	public JPanel getAddBillboardPanel() {
		return addBillboardPanel;
	}

	/**
	 * Gets the table all billboards.
	 * @return the table all billboards
	 */
	public JTable getTblAllBillboards() {
		return tblAllBillboards;
	}

	/**
	 * Gets the panel picture. This getter is required to add listener to show
	 * JFileChooser when the user clicks on this panel.
	 * @return the panel picture
	 */
	public JPanel getPnlPicture() {
		return pnlPicture;
	}


	/**
	 * Gets the button Edit.
	 * @return the button Edit
	 */
	public  JButton getBtnEditBillboard(){
		return btnEditBillboard;
	}

	/**
	 * Gets the button Delete.
	 * @return the button Delete
	 */
	public  JButton getBtnDeleteBillboard(){
		return btnDeleteBillboard;
	}

	/**
	 * Gets the button Show.
	 * @return the button Show
	 */
	public  JButton getBtnShowBillboards(){
		return btnShowBillboards;
	}

	/**
	 * Gets the button Preview from Main Panel.
	 * @return the button Show
	 */
	public  JButton getBtnPreviewBillboard(){
		return btnPreviewBillboard;
	}


	/**
	 * Gets the button AddImage .
	 * @return the button Show
	 */
	public  JButton getBtnAddImage(){
		return btnAddImage;
	}
	/**
	 * Gets the button DeleteImage .
	 * @return the button Show
	 */
	public  JButton getBtnDEleteImage(){
		return btnDeleteImage;
	}

	/**
	 * Gets the button importXML .
	 * @return the button importXML
	 */
	public  JButton getBtnImportXml(){
		return btnImportXml;
	}

	/**
	 * Gets the button exporttXML .
	 * @return the button exportXML
	 */
	public  JButton getBtnExportXml(){
		return btnExportXml;
	}

	/**
	 * Gets home button
	 * @return home button.
	 */
	public  JButton getBtnHome(){
		return  btnHome;
	}

}