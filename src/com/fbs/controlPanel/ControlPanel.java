import javax.swing.*;
import javax.swing.text.DateFormatter;
import java.awt.Rectangle;
import java.awt.event.ActionListener;
import java.io.File;
import java.awt.event.ActionEvent;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.BorderLayout;
import java.awt.Color;
import java.util.Calendar;

public class ControlPanel extends JFrame {
	JFrame f; //This is our Frame where we will do all the stuff
	
	
	
	
	//Declaring all panels we will use in Out Control Panel
	JPanel UsersPanel, CreateBillBoard, ScheduleBillBoardPanel, ListBillBoard,     Scedule_Form, Update_BillBoard,
			AddUserTab, ViewBillBoard, UpdateExistingUser, UpdateForm, ChooseFromFile;
	
	
	
	
	
	
	
	
	
	//Declaring all the Controls we need in Control Panel
	private JTextField UserName_Txt_NewUSer;
	private JTextField Password_Details_NUSer;
	private JList list;
	private JList list_1;
	private JLabel lblNewLabel_1;
	private JLabel Title_Lbl_ListBillBoard;
	private JLabel TitleLabel_ScheduleBillBoard;
	private JButton EditNow_btn;
	private JButton UpdateNow_btn;
	private JButton UpdateNow_btn_ListBillBoard;
	private JButton ScheduleNow_btn;
	private JButton Back;
	private JButton Back_btn;
	private JLabel lblNewLabel_2;
	private JLabel lblNewLabel_3;
	private JLabel lblNewLabel_4;
	private JTextField textField_2;
	private JTextField Pasword_txt;
	private JCheckBox chckbxNewCheckBox_1;
	private JCheckBox chckbxNewCheckBox_2;
	private JCheckBox chckbxNewCheckBox_3;
	private JCheckBox chckbxNewCheckBox_4;
	private JButton btnNewButton;
	private JLabel lblNewLabel_5;
	private JButton UploadXML_Btn;
	private JTextField Name_txt_Ipd;
	private JTextField Name_txt_CreateBoard;
	private JLabel lblNewLabel_6;
	private JLabel lblNewLabel_7;
	private JLabel lblNewLabel_8;
	private JButton Color_Picker_Messagee;
	private JButton ColorPiker_Message_CreateBillBOard;
	private JButton Color_Picker_Info;
	private JButton ColorPicker_Information_CreateBillBOard;
	private JButton btnNewButton_6;
	private JButton Submit_btn_CreateBillBOard;
	private JButton Choose_Img;
	private JButton ChooseImage_Btn_CreateBillBoard;
	private JButton btn_Back_8;
	private JButton DeleteNow_btn_ListBillBoard;
	private JScrollPane scrollBar_BillBoard;
	private JList BillBoard_List;
	private JList BillBoard_List_ListBillBoard;
	private JButton ViewNow_btn_ListBillBoard;
	private JLabel lblNewLabel_9;
	private JLabel lblNewLabel_10;
	private JLabel lblNewLabel_11;
	private JButton Backbtn_5;
	private JTextField SceduleFrom_txt;
	private JTextField Scheduletill_txt;
	private JButton btnNewButton_2;
	private JButton btn_Back6;

	
	
	
	
	//Constructor which is calling a function  named Initiallize
	ControlPanel() {
		initialize();
	}
	
	
//Initallize function will in itiallize initiall properties for controlls
	
	private void initialize() {
		//Creating Frame variable name f
		f = new JFrame();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setResizable(false);

		
		
		//Creating Tabbed Pane for Tabs variable name tp
		JTabbedPane tp = new JTabbedPane();
		tp.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		tp.setBounds(0, 11, 700, 380);
		
		//Urer Panel declaration and adding it to out tabbed pan
		UsersPanel = new JPanel();
		UsersPanel.setSize(700, 380);
		UsersPanel.setBounds(0, 0, 700, 380);
		tp.add("Users", UsersPanel);
		UsersPanel.setLayout(null);
		//Declaring this Chooose file variable for choosing files from Computer to upload
		ChooseFromFile = new JPanel();
		
		
//Button on USer Panel  For creating new user
		JButton Create_User_UsersPanel = new JButton("CREATE USER");
		//Action on Create USer
		Create_User_UsersPanel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Going to add user tab
				tp.setComponentAt(0, AddUserTab);
				tp.repaint();

			}
		});
		
		Create_User_UsersPanel.setBounds(200, 76, 313, 37);
		UsersPanel.add(Create_User_UsersPanel);
		
		
		
//Button for Updating Existing user 
		JButton UpdateUser_UsersPanel = new JButton("UPDATE EXISTING USER DETAILS");
		UpdateUser_UsersPanel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Going towards Update Existing User Panel for getting the list of all Users
				tp.setComponentAt(0, UpdateExistingUser);
				tp.repaint();

			}
		});
		UpdateUser_UsersPanel.setBounds(200, 153, 313, 37);
		UsersPanel.add(UpdateUser_UsersPanel);

		
		//Scedule Bill Board Panel Setting 
		ScheduleBillBoardPanel = new JPanel();
		tp.add("ScheduleBillBoard", ScheduleBillBoardPanel);
		ScheduleBillBoardPanel.setLayout(null);

		//Title lable on this Panel
		TitleLabel_ScheduleBillBoard = new JLabel("Select a Billboard you want to Schedule");
		TitleLabel_ScheduleBillBoard.setBounds(153, 29, 337, 22);
		TitleLabel_ScheduleBillBoard.setFont(new Font("Tahoma", Font.PLAIN, 18));
		ScheduleBillBoardPanel.add(TitleLabel_ScheduleBillBoard);
//He we will get the list of all those bill boards which are not Sceduled yet
		
		//This is the button from where we
		ScheduleNow_btn = new JButton("Schedule Now");
		ScheduleNow_btn.setBounds(248, 298, 135, 23);
		ScheduleNow_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
//If user want to scedule a BillBoard then e will cick on this and he will be redirected to Scedule form
				tp.setComponentAt(1, Scedule_Form);
				tp.repaint();

			}
		});

		ScheduleBillBoardPanel.add(ScheduleNow_btn);

		scrollBar_BillBoard = new JScrollPane();
		scrollBar_BillBoard.setBounds(169, 74, 284, 196);
		ScheduleBillBoardPanel.add(scrollBar_BillBoard);
//THis is the list of Bill Board from wheere we are showing the non sceduled BillBoards
		//So in this list you will load unsceduled billboards from database
		BillBoard_List = new JList();
		BillBoard_List.setBounds(340, 85, -109, 208);
		scrollBar_BillBoard.setViewportView(BillBoard_List);
		DefaultListModel dfff = new DefaultListModel();
// Herer is dummy data you will get it from database here
		dfff.addElement("BillBoard A");
		dfff.addElement("BillBoard C");
		dfff.addElement("BillBoard D");
		dfff.addElement("BillBoard E");
		dfff.addElement("BillBoard g");
		dfff.addElement("BillBoard h");
		dfff.addElement("BillBoard i");
		dfff.addElement("BillBoard j");
		dfff.addElement("BillBoard k");
		dfff.addElement("BillBoard l");
		dfff.addElement("BillBoard m");
		dfff.addElement("BillBoard o");
		dfff.addElement("BillBoard q");

		BillBoard_List.setModel(dfff);
		CreateBillBoard = new JPanel();
		tp.add("Create BillBoards", CreateBillBoard);
		CreateBillBoard.setLayout(null);
// if a user want to upload an XML so he will click on this button
		UploadXML_Btn = new JButton("Add from A XML file ?");
		UploadXML_Btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tp.setComponentAt(2, ChooseFromFile);
				tp.repaint();

			}
		});
		
		
		UploadXML_Btn.setBounds(163, 11, 185, 37);
		CreateBillBoard.add(UploadXML_Btn);

		
		// here user is entring the infrmation of billboard
		Name_txt_CreateBoard = new JTextField();
		Name_txt_CreateBoard.setBounds(230, 74, 175, 20);
		CreateBillBoard.add(Name_txt_CreateBoard);
		Name_txt_CreateBoard.setColumns(10);

		lblNewLabel_6 = new JLabel("Name ");
		lblNewLabel_6.setBounds(98, 77, 88, 14);
		CreateBillBoard.add(lblNewLabel_6);

		lblNewLabel_7 = new JLabel("Bill Board Background");
		lblNewLabel_7.setBounds(98, 120, 133, 14);
		CreateBillBoard.add(lblNewLabel_7);

		lblNewLabel_8 = new JLabel("Message");
		lblNewLabel_8.setBounds(98, 161, 118, 14);
		CreateBillBoard.add(lblNewLabel_8);

		JTextArea MessageTxt_CreateBillBoard = new JTextArea();
		MessageTxt_CreateBillBoard.setBounds(228, 156, 197, 37);
		CreateBillBoard.add(MessageTxt_CreateBillBoard);

		JLabel lblNewLabel_8_1 = new JLabel("Picture");
		lblNewLabel_8_1.setBounds(98, 210, 118, 14);
		CreateBillBoard.add(lblNewLabel_8_1);

		JLabel lblNewLabel_8_1_1 = new JLabel("Infromation");
		lblNewLabel_8_1_1.setBounds(98, 247, 118, 14);
		CreateBillBoard.add(lblNewLabel_8_1_1);
		JTextArea Infromation_txt_CreateBillBoard = new JTextArea();
		Infromation_txt_CreateBillBoard.setBounds(228, 242, 197, 42);
		CreateBillBoard.add(Infromation_txt_CreateBillBoard);

		JButton ColorPicker_Background = new JButton(" ");
		ColorPicker_Background.setBackground(Color.GREEN);
		ColorPicker_Background.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				final JColorChooser colorChooser = new JColorChooser();
				//Here user will add background color 
				JDialog dialog = JColorChooser.createDialog(CreateBillBoard, "Choose BillBoard bg color", false,
						colorChooser, new ActionListener() {
							@Override
							public void actionPerformed(ActionEvent event) {
//You will add this color in data base for specific bill board bg
								ColorPicker_Background.setBackground(colorChooser.getColor());
							}
						}, new ActionListener() {
							@Override
							public void actionPerformed(ActionEvent event) {

							}
						});
				dialog.setVisible(true);
			}

		});
		ColorPicker_Background.setBounds(228, 122, 88, 23);
		CreateBillBoard.add(ColorPicker_Background);
//Here user will choose color of Message body
		ColorPiker_Message_CreateBillBOard = new JButton("");
		ColorPiker_Message_CreateBillBOard.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				final JColorChooser colorChooser = new JColorChooser();
				JDialog dialog = JColorChooser.createDialog(CreateBillBoard, "Coose the Color for Message body", false,
						colorChooser, new ActionListener() {
							@Override
							public void actionPerformed(ActionEvent event) {
//You will save this color for Message in database
								Color_Picker_Messagee.setBackground(colorChooser.getColor());
							}
						}, new ActionListener() {
							@Override
							public void actionPerformed(ActionEvent event) {

							}
						});
				dialog.setVisible(true);
			}

		});
		
		
		
		
		ColorPiker_Message_CreateBillBOard.setBackground(Color.GREEN);
		ColorPiker_Message_CreateBillBOard.setBounds(445, 161, 25, 20);
		CreateBillBoard.add(ColorPiker_Message_CreateBillBOard);

		
		
		
		//Here user wil choose information color
		ColorPicker_Information_CreateBillBOard = new JButton("");
		ColorPicker_Information_CreateBillBOard.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				final JColorChooser colorChooser = new JColorChooser();
				JDialog dialog = JColorChooser.createDialog(CreateBillBoard, "Choose Information Color", false,
						colorChooser, new ActionListener() {
							@Override
							public void actionPerformed(ActionEvent event) {
//Here you will set color of information in database for specific bill board
								Color_Picker_Info.setBackground(colorChooser.getColor());
							}
						}, new ActionListener() {
							@Override
							public void actionPerformed(ActionEvent event) {

							}
						});
				dialog.setVisible(true);

			}
		});
		
		
		
		
		
		ColorPicker_Information_CreateBillBOard.setBackground(Color.GREEN);
		ColorPicker_Information_CreateBillBOard.setBounds(445, 247, 25, 20);
		CreateBillBoard.add(ColorPicker_Information_CreateBillBOard);

		Submit_btn_CreateBillBOard = new JButton("Submit");
		Submit_btn_CreateBillBOard.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				// Here you will submit the bilboard to databse
				
				
			}
		});
		Submit_btn_CreateBillBOard.setBounds(230, 305, 89, 23);
		CreateBillBoard.add(Submit_btn_CreateBillBOard);
//Here user will choose image for Bill Board
		ChooseImage_Btn_CreateBillBoard = new JButton("Choose Image");

		ChooseImage_Btn_CreateBillBoard.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				JFileChooser fileChooser = new JFileChooser();
				int returnValue = fileChooser.showOpenDialog(null);
				if (returnValue == JFileChooser.APPROVE_OPTION) {
					File selectedFile = fileChooser.getSelectedFile();
					// System.out.println(selectedFile.getName());
					Choose_Img.setText(selectedFile.getName());
				}

			}

		});
		ChooseImage_Btn_CreateBillBoard.setBounds(228, 206, 158, 23);
		CreateBillBoard.add(ChooseImage_Btn_CreateBillBoard);
		
		
		
		//
		AddUserTab = new JPanel();
		UpdateForm = new JPanel();

		UpdateForm.setLayout(null);

		Back_btn = new JButton(" ");
		Back_btn.setIcon(
				new ImageIcon("C:\\Users\\sasit\\Desktop\\Billboard Control Panel\\src\\abc.png"));
		Back_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//back
				tp.setComponentAt(0, UsersPanel);
				tp.repaint();

			}
		});
		
		
		
		//Here  User data Will be Updated you will get selected User afrom db and then populate its data in these fields
		Back_btn.setAutoscrolls(true);
		Back_btn.setBounds(10, 11, 65, 23);
		UpdateForm.add(Back_btn);

		lblNewLabel_2 = new JLabel("Name");
		lblNewLabel_2.setBounds(116, 78, 46, 14);
		UpdateForm.add(lblNewLabel_2);

		lblNewLabel_3 = new JLabel("Password");
		lblNewLabel_3.setBounds(116, 134, 58, 14);
		UpdateForm.add(lblNewLabel_3);

		lblNewLabel_4 = new JLabel("Permissions");
		lblNewLabel_4.setBounds(116, 196, 89, 14);
		UpdateForm.add(lblNewLabel_4);

		textField_2 = new JTextField();
		textField_2.setBounds(209, 75, 215, 20);
		UpdateForm.add(textField_2);
		textField_2.setColumns(10);

		Pasword_txt = new JTextField();
		Pasword_txt.setText("123456");
		Pasword_txt.setColumns(10);
		Pasword_txt.setBounds(209, 131, 215, 20);
		UpdateForm.add(Pasword_txt);

		chckbxNewCheckBox_1 = new JCheckBox("Create BillBorads");
		chckbxNewCheckBox_1.setSelected(true);
		chckbxNewCheckBox_1.setBounds(206, 192, 97, 23);
		UpdateForm.add(chckbxNewCheckBox_1);

		chckbxNewCheckBox_2 = new JCheckBox("Edit BillBoards");
		chckbxNewCheckBox_2.setBounds(315, 192, 97, 23);
		UpdateForm.add(chckbxNewCheckBox_2);

		chckbxNewCheckBox_3 = new JCheckBox("Scedule BillBoards");
		chckbxNewCheckBox_3.setSelected(true);
		chckbxNewCheckBox_3.setBounds(421, 192, 97, 23);
		UpdateForm.add(chckbxNewCheckBox_3);

		chckbxNewCheckBox_4 = new JCheckBox("Edit Users");
		chckbxNewCheckBox_4.setBounds(533, 192, 97, 23);
		UpdateForm.add(chckbxNewCheckBox_4);

		//Here is the update button from where you will but new data in database
		btnNewButton = new JButton("Udate");
		btnNewButton.setBounds(262, 287, 89, 23);
		UpdateForm.add(btnNewButton);

		AddUserTab.setLayout(null);

		
		
		//Here use will create new BillBoard
		JCheckBox chckbxNewCheckBox = new JCheckBox("Create Billboards");
		chckbxNewCheckBox.setBounds(185, 185, 118, 23);
		AddUserTab.add(chckbxNewCheckBox);

		JLabel lblNewLabel = new JLabel("User Name");
		lblNewLabel.setBounds(102, 75, 69, 23);
		AddUserTab.add(lblNewLabel);

		UserName_Txt_NewUSer = new JTextField();
		UserName_Txt_NewUSer.setBounds(185, 76, 232, 20);
		AddUserTab.add(UserName_Txt_NewUSer);
		UserName_Txt_NewUSer.setColumns(10);

		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(102, 127, 64, 23);
		AddUserTab.add(lblPassword);

		Password_Details_NUSer = new JTextField();
		Password_Details_NUSer.setColumns(10);
		Password_Details_NUSer.setBounds(185, 128, 232, 20);
		AddUserTab.add(Password_Details_NUSer);

		JLabel lblPermissions = new JLabel("Permissions");
		lblPermissions.setBounds(102, 185, 77, 23);
		AddUserTab.add(lblPermissions);

		JCheckBox chckbxEditUsers = new JCheckBox("Edit Users");
		chckbxEditUsers.setBounds(573, 185, 116, 23);
		AddUserTab.add(chckbxEditUsers);

		JCheckBox chckbxEditAllBillboards = new JCheckBox("Edit All Billboards");
		chckbxEditAllBillboards.setBounds(305, 185, 123, 23);
		AddUserTab.add(chckbxEditAllBillboards);

		JCheckBox chckbxNewCheckBox_2_1 = new JCheckBox("Schedule Billboards");
		chckbxNewCheckBox_2_1.setBounds(430, 185, 141, 23);
		AddUserTab.add(chckbxNewCheckBox_2_1);
//Here is the Submit button though which you need to put data into database
		JButton Submit = new JButton("Submit");
		Submit.setBounds(275, 270, 89, 23);
		AddUserTab.add(Submit);

		
		//back
		
		JButton Back = new JButton(" ");
		Back.setIcon(
				new ImageIcon("C:\\Users\\sasit\\Desktop\\Billboard Control Panel\\src\\abc.png"));
		Back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				tp.setComponentAt(0, UsersPanel);
				tp.repaint();

			}
		});
		Back.setBounds(0, 11, 53, 23);
		AddUserTab.add(Back);
		//Label 
		JLabel lblAddDetailsOf = new JLabel("ADD DETAILS OF NEW USER");
		lblAddDetailsOf.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblAddDetailsOf.setSize(new Dimension(50, 50));
		lblAddDetailsOf.setBounds(203, 0, 327, 48);
		AddUserTab.add(lblAddDetailsOf);

		
		
		
		
		
		//Here is the list of user which A person will get for updating 
		DefaultListModel df = new DefaultListModel();
//YOu will load data in it from database
		df.addElement("User A");
		df.addElement("User B");
		df.addElement("User C");
		df.addElement("User D");
		df.addElement("User E");
		df.addElement("User F");
		df.addElement("User g");
		df.addElement("User h");
		df.addElement("User i");
		df.addElement("User j");
		df.addElement("User k");
		df.addElement("User l");

		f.getContentPane().add(tp);
		Scedule_Form = new JPanel();

		
		//Setting calander for Sceduling
		
		Scedule_Form.setLayout(null);
		Scedule_Form.setBounds(new Rectangle(25, 25, 8, 8));

		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 24); // 24 == 12 PM == 00:00:00
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);

		SpinnerDateModel model = new SpinnerDateModel();
		model.setValue(calendar.getTime());

		JSpinner Clock_1 = new JSpinner(model);
		Clock_1.setBounds(432, 43, 67, 20);

		// Setting Time Spinner
		JSpinner.DateEditor de_Clock_1 = new JSpinner.DateEditor(Clock_1, "HH:mm:ss");
		DateFormatter formatter = (DateFormatter) de_Clock_1.getTextField().getFormatter();
		formatter.setAllowsInvalid(false); // this makes what you want
		formatter.setOverwriteMode(true);

		Clock_1.setEditor(de_Clock_1);

		Scedule_Form.add(Clock_1);

		JButton Calander_btn = new JButton("\\_\\");
		Calander_btn.setBounds(369, 40, 41, 27);
		Scedule_Form.add(Calander_btn);

		SceduleFrom_txt = new JTextField(20);
		SceduleFrom_txt.setBounds(235, 40, 124, 26);
		Scedule_Form.add(SceduleFrom_txt);

		Scheduletill_txt = new JTextField(20);
		Scheduletill_txt.setBounds(235, 89, 124, 26);
		Scedule_Form.add(Scheduletill_txt);

		JButton Calander_btn1 = new JButton("...");
		Calander_btn1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				Scheduletill_txt.setText(new DatePicker(Scedule_Form).setPickedDate());

			}
		});
		Calander_btn1.setBounds(369, 89, 41, 27);
		Scedule_Form.add(Calander_btn1);

		JLabel lblNewLabel_12 = new JLabel("Scedule from");
		lblNewLabel_12.setBounds(83, 40, 104, 27);
		Scedule_Form.add(lblNewLabel_12);

		JLabel lblNewLabel_12_1 = new JLabel("Scedule Till");
		lblNewLabel_12_1.setBounds(83, 89, 104, 27);
		Scedule_Form.add(lblNewLabel_12_1);
		Calendar calendar1 = Calendar.getInstance();
		calendar1.set(Calendar.HOUR_OF_DAY, 24); // 24 == 12 PM == 00:00:00
		calendar1.set(Calendar.MINUTE, 0);
		calendar1.set(Calendar.SECOND, 0);

		SpinnerDateModel model_1 = new SpinnerDateModel();
		model_1.setValue(calendar1.getTime());

		JSpinner Clock_2 = new JSpinner(model_1);
		Clock_2.setBounds(432, 43, 67, 20);

		JSpinner.DateEditor de_Clock_2 = new JSpinner.DateEditor(Clock_2, "HH:mm:ss");
		DateFormatter formatter_1 = (DateFormatter) de_Clock_2.getTextField().getFormatter();
		formatter_1.setAllowsInvalid(false); // this makes what you want
		formatter_1.setOverwriteMode(true);

		Clock_2.setEditor(de_Clock_2);

		Clock_2.setBounds(432, 92, 67, 20);
		Scedule_Form.add(Clock_2);

		JLabel lblNewLabel_12_1_1 = new JLabel("Recur Every");
		lblNewLabel_12_1_1.setBounds(83, 166, 104, 27);
		Scedule_Form.add(lblNewLabel_12_1_1);

		JList list_2 = new JList();
		list_2.setBounds(289, 171, 1, 1);
		Scedule_Form.add(list_2);

		JCheckBox Hour_ChkBox = new JCheckBox("Hour");
		Hour_ChkBox.setBounds(314, 168, 83, 23);
		Scedule_Form.add(Hour_ChkBox);

		JCheckBox Minutes_ChkBox = new JCheckBox("Minutes");
		Minutes_ChkBox.setBounds(400, 168, 99, 23);
		Scedule_Form.add(Minutes_ChkBox);

		JCheckBox Day_ChkBox = new JCheckBox("Day");
		Day_ChkBox.setBounds(224, 168, 78, 23);
		Scedule_Form.add(Day_ChkBox);
//This is Submit button YOu will submit Scedule in db from here
		JButton Submit_Btn_Sceduling = new JButton("Submit");
		Submit_Btn_Sceduling.setBounds(259, 243, 89, 23);
		Scedule_Form.add(Submit_Btn_Sceduling);

		JButton Btn_BACK_1 = new JButton("");
		Btn_BACK_1.setIcon(
				new ImageIcon("C:\\Users\\sasit\\Desktop\\Billboard Control Panel\\src\\abc.png"));
		Btn_BACK_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				tp.setComponentAt(1, ScheduleBillBoardPanel);
				tp.repaint();

			}
		});
		Btn_BACK_1.setBounds(10, 11, 89, 23);
		Scedule_Form.add(Btn_BACK_1);

		Calander_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				SceduleFrom_txt.setText(new DatePicker(Scedule_Form).setPickedDate());
			}
		});

		ListBillBoard = new JPanel();

		
		// here user will se the List of all Bill Boards for view update or delete
		tp.add("ListBillBoard", ListBillBoard);
		ListBillBoard.setLayout(null);
		Title_Lbl_ListBillBoard = new JLabel("Select a Billboard you want to update , View or delete?");
		Title_Lbl_ListBillBoard.setBounds(103, 28, 444, 22);
		Title_Lbl_ListBillBoard.setFont(new Font("Tahoma", Font.PLAIN, 18));
		ListBillBoard.add(Title_Lbl_ListBillBoard);

		UpdateNow_btn_ListBillBoard = new JButton("Update now?");
		UpdateNow_btn_ListBillBoard.setBounds(54, 298, 135, 23);
		UpdateNow_btn_ListBillBoard.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				tp.setComponentAt(3, Update_BillBoard);
				tp.repaint();

			}
		});
		ListBillBoard.add(UpdateNow_btn_ListBillBoard);
		DefaultListModel dff = new DefaultListModel();
//You will load this from databse the whole list of Bill Boards
		dff.addElement("BillBoard A");
		dff.addElement("BillBoard B");
		dff.addElement("BillBoard C");
		dff.addElement("BillBoard D");
		dff.addElement("BillBoard E");
		dff.addElement("BillBoard F");
		dff.addElement("BillBoard g");
		dff.addElement("BillBoard h");
		dff.addElement("BillBoard i");
		dff.addElement("BillBoard j");
		dff.addElement("BillBoard k");
		dff.addElement("BillBoard n");
		dff.addElement("BillBoard o");
		dff.addElement("BillBoard p");
		dff.addElement("BillBoard q");
		dff.addElement("BillBoard r");
		dff.addElement("BillBoard s");
		dff.addElement("BillBoard t");
//Scroller for this list
		scrollBar_BillBoard = new JScrollPane();
		scrollBar_BillBoard.setBounds(169, 74, 284, 196);
		ListBillBoard.add(scrollBar_BillBoard);

		BillBoard_List_ListBillBoard = new JList();
		BillBoard_List_ListBillBoard.setBounds(340, 85, -109, 208);
		scrollBar_BillBoard.setViewportView(BillBoard_List_ListBillBoard);
		BillBoard_List_ListBillBoard.setModel(dff);

		DeleteNow_btn_ListBillBoard = new JButton("Delete now?");
		DeleteNow_btn_ListBillBoard.setBounds(241, 298, 126, 23);
		ListBillBoard.add(DeleteNow_btn_ListBillBoard);

		ViewNow_btn_ListBillBoard = new JButton("View now?");
		ViewNow_btn_ListBillBoard.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				tp.setComponentAt(3, ViewBillBoard);
				tp.repaint();

			}
		});
		
		
		ViewNow_btn_ListBillBoard.setBounds(408, 298, 135, 23);
		ListBillBoard.add(ViewNow_btn_ListBillBoard);

		Update_BillBoard = new JPanel();

		Update_BillBoard.setLayout(null);

		
		//Here user will Update a BillBoard
		Name_txt_Ipd = new JTextField();
		Name_txt_Ipd.setBounds(339, 61, 86, 20);
		Update_BillBoard.add(Name_txt_Ipd);
		Name_txt_Ipd.setColumns(10);

		lblNewLabel_6 = new JLabel("Name ");
		lblNewLabel_6.setBounds(170, 64, 86, 14);
		Update_BillBoard.add(lblNewLabel_6);

		lblNewLabel_7 = new JLabel("Bill Board Background");
		lblNewLabel_7.setBounds(170, 118, 158, 14);
		Update_BillBoard.add(lblNewLabel_7);

		lblNewLabel_8 = new JLabel("Message");
		lblNewLabel_8.setBounds(170, 167, 98, 14);
		Update_BillBoard.add(lblNewLabel_8);

		JTextArea Message_TArea = new JTextArea();
		Message_TArea.setBounds(339, 143, 102, 56);
		Update_BillBoard.add(Message_TArea);

		JLabel lblNewLabel_8_11 = new JLabel("Picture");
		lblNewLabel_8_11.setBounds(170, 214, 89, 14);
		Update_BillBoard.add(lblNewLabel_8_11);

		JLabel lblNewLabel_8_1_11 = new JLabel("Infromation");
		lblNewLabel_8_1_11.setBounds(170, 249, 112, 14);
		Update_BillBoard.add(lblNewLabel_8_1_11);
		JTextArea Info_TArea = new JTextArea();
		Info_TArea.setBounds(339, 244, 102, 48);
		Update_BillBoard.add(Info_TArea);

		JButton Color_PickerBg = new JButton("");
		Color_PickerBg.setBackground(Color.GREEN);
		Color_PickerBg.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				final JColorChooser colorChooser = new JColorChooser();
				JDialog dialog = JColorChooser.createDialog(CreateBillBoard, "Chane TextArea color", false,
						colorChooser, new ActionListener() {
							@Override
							public void actionPerformed(ActionEvent event) {

								Color_PickerBg.setBackground(colorChooser.getColor());
							}
						}, new ActionListener() {
							@Override
							public void actionPerformed(ActionEvent event) {

							}
						});
				dialog.setVisible(true);
			}

		});
		Color_PickerBg.setBounds(339, 106, 65, 26);
		Update_BillBoard.add(Color_PickerBg);

		Color_Picker_Messagee = new JButton("");
		Color_Picker_Messagee.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				final JColorChooser colorChooser = new JColorChooser();
				JDialog dialog = JColorChooser.createDialog(CreateBillBoard, "Chane TextArea color", false,
						colorChooser, new ActionListener() {
							@Override
							public void actionPerformed(ActionEvent event) {

								Color_Picker_Messagee.setBackground(colorChooser.getColor());
							}
						}, new ActionListener() {
							@Override
							public void actionPerformed(ActionEvent event) {

							}
						});
				dialog.setVisible(true);
			}

		});
		Color_Picker_Messagee.setBackground(Color.GREEN);
		Color_Picker_Messagee.setBounds(451, 167, 33, 14);
		Update_BillBoard.add(Color_Picker_Messagee);

		Color_Picker_Info = new JButton("");
		Color_Picker_Info.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				final JColorChooser colorChooser = new JColorChooser();
				JDialog dialog = JColorChooser.createDialog(CreateBillBoard, "Chane TextArea color", false,
						colorChooser, new ActionListener() {
							@Override
							public void actionPerformed(ActionEvent event) {

								Color_Picker_Info.setBackground(colorChooser.getColor());
							}
						}, new ActionListener() {
							@Override
							public void actionPerformed(ActionEvent event) {

							}
						});
				dialog.setVisible(true);

			}
		});
		Color_Picker_Info.setBackground(Color.GREEN);
		Color_Picker_Info.setBounds(451, 258, 33, 14);
		Update_BillBoard.add(Color_Picker_Info);

		btnNewButton_6 = new JButton("Update");
		btnNewButton_6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
	//here you will put updated data in db
		btnNewButton_6.setBounds(280, 303, 86, 23);
		Update_BillBoard.add(btnNewButton_6);

		Choose_Img = new JButton("Choose Image");

		Choose_Img.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				JFileChooser fileChooser = new JFileChooser();
				int returnValue = fileChooser.showOpenDialog(null);
				if (returnValue == JFileChooser.APPROVE_OPTION) {
					File selectedFile = fileChooser.getSelectedFile();
					// System.out.println(selectedFile.getName());
					Choose_Img.setText(selectedFile.getName());
				}

			}

		});
		Choose_Img.setBounds(340, 210, 101, 23);
		Update_BillBoard.add(Choose_Img);

		JLabel lblNewLabel_6_1 = new JLabel("Enter the Content you need to update");
		lblNewLabel_6_1.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel_6_1.setBounds(201, 0, 305, 32);
		Update_BillBoard.add(lblNewLabel_6_1);

		btnNewButton_2 = new JButton(" ");
		btnNewButton_2.setIcon(
				new ImageIcon("C:\\Users\\sasit\\Desktop\\Billboard Control Panel\\src\\abc.png"));
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tp.setComponentAt(3, ListBillBoard);
				tp.repaint();
			}
		});
		btnNewButton_2.setBounds(0, 8, 89, 23);
		Update_BillBoard.add(btnNewButton_2);

		ViewBillBoard = new JPanel();

		ViewBillBoard.setBackground(Color.LIGHT_GRAY);

		ViewBillBoard.setLayout(null);

		lblNewLabel_9 = new JLabel("This is Name of Clicked Bill Board");
		lblNewLabel_9.setBackground(Color.BLACK);
		lblNewLabel_9.setBounds(116, 57, 223, 31);
		ViewBillBoard.add(lblNewLabel_9);

		lblNewLabel_10 = new JLabel("This is Message from Clicked Bill Board");
		lblNewLabel_10.setForeground(Color.GREEN);
		lblNewLabel_10.setBackground(Color.CYAN);
		lblNewLabel_10.setBounds(116, 103, 354, 64);
		ViewBillBoard.add(lblNewLabel_10);

		lblNewLabel_11 = new JLabel("This is Information from Clicked Bill Board");
		lblNewLabel_11.setForeground(Color.YELLOW);
		lblNewLabel_11.setBackground(Color.ORANGE);
		lblNewLabel_11.setBounds(116, 169, 354, 64);
		ViewBillBoard.add(lblNewLabel_11);

		ImageIcon image = new ImageIcon("abc.png");
		JLabel j = new JLabel(image);
		j.setBounds(116, 230, 233, 97);
		ViewBillBoard.add(j, BorderLayout.NORTH);

		Backbtn_5 = new JButton(" ");
		Backbtn_5.setIcon(
				new ImageIcon("C:\\Users\\sasit\\Desktop\\Billboard Control Panel\\src\\abc.png"));
		Backbtn_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				tp.setComponentAt(3, ListBillBoard);
				tp.repaint();

			}
		});
		Backbtn_5.setBounds(10, 11, 60, 23);
		ViewBillBoard.add(Backbtn_5);

		ChooseFromFile.setLayout(null);
		lblNewLabel_5 = new JLabel("Choose a File from Computer");
		lblNewLabel_5.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblNewLabel_5.setBounds(164, 11, 378, 24);
		ChooseFromFile.add(lblNewLabel_5);

		JFileChooser j_1 = new JFileChooser();
		j_1.setBounds(20, 53, 633, 237);
		ChooseFromFile.add(j_1);

		JButton btnNewButton_1 = new JButton("Add Bill Board");
		btnNewButton_1.setBounds(235, 301, 159, 24);
		ChooseFromFile.add(btnNewButton_1);

		btn_Back_8 = new JButton(" ");
		btn_Back_8.setIcon(
				new ImageIcon("C:\\Users\\sasit\\Desktop\\Billboard Control Panel\\src\\abc.png"));
		btn_Back_8.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				tp.setComponentAt(2, CreateBillBoard);
				tp.repaint();

			}
		});
		btn_Back_8.setBounds(0, 15, 69, 23);
		ChooseFromFile.add(btn_Back_8);
		UpdateExistingUser = new JPanel();

		UpdateExistingUser.setLayout(null);

		lblNewLabel_1 = new JLabel("Select a User you want to Edit?");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel_1.setBounds(181, 29, 324, 32);
		UpdateExistingUser.add(lblNewLabel_1);

		EditNow_btn = new JButton("Edit now?");
		EditNow_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				tp.setComponentAt(0, UpdateForm);
				tp.repaint();

			}
		});
		EditNow_btn.setBounds(276, 303, 89, 23);
		UpdateExistingUser.add(EditNow_btn);

		JScrollPane scrollBar1 = new JScrollPane();
		scrollBar1.setBounds(232, 85, 176, 208);
		UpdateExistingUser.add(scrollBar1);

		JList User_List1 = new JList();
		User_List1.setBounds(340, 85, -109, 208);
		scrollBar1.setViewportView(User_List1);
		User_List1.setModel(df);

		btn_Back6 = new JButton(" ");
		btn_Back6.setIcon(
				new ImageIcon("C:\\Users\\sasit\\Desktop\\Billboard Control Panel\\src\\abc.png"));
		btn_Back6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				tp.setComponentAt(0, UsersPanel);
				tp.repaint();
			}
		});
		btn_Back6.setBounds(0, 11, 60, 23);
		UpdateExistingUser.add(btn_Back6);

		f.setSize(700, 400);
		f.getContentPane().setLayout(null);
		f.setTitle("Admin View Contoll Panel");
		f.setVisible(true);

	}
}