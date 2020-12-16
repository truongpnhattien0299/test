package GUI;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Properties;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.UIManager;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import DAL.DateLabelFormatter;
import DAL.User;

public class SignUp extends JFrame {

	public JPanel start;
	private JTextField jTextFieldUsername, txthoten;
	private JRadioButton radioNam, radioNu;
	private JButton btnButtonSignUp;
	private JPasswordField passwordField, oldpassword;
	private JDatePickerImpl datePicker;
	public static DAL.Cl_Connect cl;
	private Boolean flag = false;
	private String username;
	private String[] user;

	public SignUp() {
		initcomponents();
		actionListener();

		LocalDate date = LocalDate.now();
		datepicker(date.getYear(), date.getMonthValue(), date.getDayOfMonth());
	}

	public SignUp(String username) {
		this.username = username;
		flag = true;
		initcomponents();
		actionListener();
		try {
			cl = new DAL.Cl_Connect();
			cl.CreateSocket("127.0.0.1", 1234);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		user = cl.GetUser(username).split("#");
		System.out.println(user.toString());
		txthoten.setText(user[1]);
		if (user[2].equals("nu"))
			radioNu.setSelected(true);
		String[] dob = user[3].split("/");
		datepicker(Integer.parseInt(dob[2]), Integer.parseInt(dob[1]), Integer.parseInt(dob[0]));
	}

	public void initcomponents() {
		start = new JPanel();

		ImagePanel background = new ImagePanel("main.png", 0, 0, 800, 600);

		JLabel lblNewLabel = new JLabel("Sign Up");
		if (flag)
			lblNewLabel = new JLabel("Update Infomation");
		lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD, 16));
		lblNewLabel.setBounds(300, 24, 145, 30);
		start.add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("Please enter your infomation");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNewLabel_1.setBounds(300, 65, 259, 14);
		start.add(lblNewLabel_1);
		JLabel lblNewLabel_2 = null, lblNewLabel_3 = null;
		if (flag) {
			lblNewLabel_2 = new JLabel("Old Password");
			lblNewLabel_3 = new JLabel("New Password");
		} else {
			lblNewLabel_2 = new JLabel("Username");
			lblNewLabel_3 = new JLabel("Password");
		}
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNewLabel_2.setBounds(200, 106, 70, 14);
		start.add(lblNewLabel_2);

		lblNewLabel_3.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNewLabel_3.setBounds(200, 142, 63, 14);
		start.add(lblNewLabel_3);

		JLabel lblNewLabel_4 = new JLabel("Họ tên");
		lblNewLabel_4.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNewLabel_4.setBounds(200, 175, 63, 14);
		start.add(lblNewLabel_4);

		JLabel lblNewLabel_5 = new JLabel("Giới tính");
		lblNewLabel_5.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNewLabel_5.setBounds(200, 208, 63, 14);
		start.add(lblNewLabel_5);

		JLabel lblNewLabel_6 = new JLabel("Ngày sinh");
		lblNewLabel_6.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNewLabel_6.setBounds(200, 240, 63, 20);
		start.add(lblNewLabel_6);

		if (flag) {
			oldpassword = new JPasswordField();
			oldpassword.setBounds(303, 104, 163, 30);
			start.add(oldpassword);
		} else {
			jTextFieldUsername = new JTextField();
			jTextFieldUsername.setBounds(303, 104, 163, 30);
			start.add(jTextFieldUsername);
			jTextFieldUsername.setColumns(10);
		}

		passwordField = new JPasswordField();
		passwordField.setBounds(300, 141, 163, 30);
		start.add(passwordField);

		txthoten = new JTextField();
		txthoten.setBounds(300, 178, 163, 30);
		start.add(txthoten);

		radioNam = new JRadioButton();
		radioNam.setBounds(300, 215, 60, 15);
		radioNam.setText("Nam");
		start.add(radioNam);

		radioNu = new JRadioButton();
		radioNu.setBounds(370, 215, 50, 15);
		radioNu.setText("Nữ");
		start.add(radioNu);

		radioNam.setSelected(true);
		ButtonGroup G = new ButtonGroup();
		G.add(radioNam);
		G.add(radioNu);

		if (flag)
			btnButtonSignUp = new JButton("Update");
		else
			btnButtonSignUp = new JButton("Sign Up");
		btnButtonSignUp.setBounds(270, 350, 230, 60);
		start.add(btnButtonSignUp);

		JButton exitButton = new JButton("Exit");

		exitButton.setBounds(270, 350, 230, 60);

		start.add(exitButton);
		start.add(background);

		start.setLayout(null);
		start.setBounds(0, 0, 800, 600);
		this.add(start);

		this.setLayout(null);
		this.setSize(800, 600);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public void datepicker(int year, int month, int day) {
		UtilDateModel model = new UtilDateModel();
		model.setDate(year, month - 1, day);
		model.setSelected(true);
		Properties p = new Properties();
		p.put("text.today", "Today");
		p.put("text.month", "Month");
		p.put("text.year", "Year");
		JDatePanelImpl datePanel = new JDatePanelImpl(model, p);

		datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
		datePicker.setBounds(300, 240, 163, 28);
		start.add(datePicker);
	}

	public void actionListener() {
		if (flag) {
			ActionListener ButtonUpdate = new ButtonUpdate();
			btnButtonSignUp.addActionListener(ButtonUpdate);
		} else {
			ActionListener ButtonSignup = new ButtonSignUp();
			btnButtonSignUp.addActionListener(ButtonSignup);
		}
	}

	class ButtonSignUp implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {

			String username = jTextFieldUsername.getText();
			String password = String.valueOf(passwordField.getPassword());
			String hoten = txthoten.getText();
			String gender = "nam";
			if (radioNu.isSelected())
				gender = "nu";
			String dob = datePicker.getModel().getDay() + "/" + datePicker.getModel().getMonth() + "/"
					+ datePicker.getModel().getYear();

			if (username.equals("") || password.equals("") || hoten.equals("")) {
				JOptionPane.showConfirmDialog(rootPane, "Some Fields Are is Empty", "Error", 1);
			} else {
				if (isValid(username)) {
					int check = CheckSignUp(username, password, hoten, gender, dob);
					if (check == 1) {
						dispose();
						System.out.println("Sign up success!!");
						new StartPanel2();
					} else if (check == 3) {
						JOptionPane.showConfirmDialog(rootPane, "Username Đã tồn tại", "Error", 1);
					} else
						JOptionPane.showConfirmDialog(rootPane, "Username hoặc Password không đúng", "Error", 1);
				} else {
					JOptionPane.showConfirmDialog(rootPane, "Username Không đúng định dạng 'Example@mail.com'", "Error",
							1);
				}
			}

		}

	}

	class ButtonUpdate implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			String password = String.valueOf(oldpassword.getPassword());
			String newpassword = String.valueOf(passwordField.getPassword());
			String hoten = txthoten.getText();
			String gender = "nam";
			if (radioNu.isSelected())
				gender = "nu";
			String dob = datePicker.getModel().getDay() + "/" + datePicker.getModel().getMonth() + "/"
					+ datePicker.getModel().getYear();

			if (password.equals("") || hoten.equals("") || newpassword.equals("")) {
				JOptionPane.showConfirmDialog(rootPane, "Some Fields Are is Empty", "Error", 1);
			} else {
				if(cl.hashMD5(password).equals(user[0]))
				{
					int check = cl.UpdateInfo(username, newpassword, hoten, gender, dob);
					if(check == 1)
					{
						JOptionPane.showConfirmDialog(rootPane, "Không thể cập nhật lại thông tin", "Error", 1);
					}
					else
					{
						JOptionPane.showConfirmDialog(rootPane, "Đã cập nhật thành công", "Error", 1);
						dispose();
						try {
							new StartGUI();
						} catch (UnknownHostException e) {
							e.printStackTrace();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
				else
					JOptionPane.showConfirmDialog(rootPane, "Mật khẩu không đúng", "Error", 1);
			}

		}

	}

	public Boolean isValid(String email) {
		String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
		return email.matches(regex);
	}

	public int CheckSignUp(String user, String pass, String hoten, String gender, String dob) {
		int check = 2;
		try {
			cl = new DAL.Cl_Connect();
			cl.CreateSocket("127.0.0.1", 1234);
			check = cl.Signup(user, pass, hoten, gender, dob);

		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return check;
	}

	public String encrypt(String strToEncrypt, String myKey) {
		try {
			MessageDigest sha = MessageDigest.getInstance("SHA-1");
			byte[] key = myKey.getBytes("UTF-8");
			key = sha.digest(key);
			key = Arrays.copyOf(key, 16);
			SecretKeySpec secretKey = new SecretKeySpec(key, "AES");
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, secretKey);
			return java.util.Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes("UTF-8")));
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return null;
	}

	public String decrypt(String strToDecrypt, String myKey) {
		try {
			MessageDigest sha = MessageDigest.getInstance("SHA-1");
			byte[] key = myKey.getBytes("UTF-8");
			key = sha.digest(key);
			key = Arrays.copyOf(key, 16);
			SecretKeySpec secretKey = new SecretKeySpec(key, "AES");
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
			cipher.init(Cipher.DECRYPT_MODE, secretKey);
			return new String(cipher.doFinal(java.util.Base64.getDecoder().decode(strToDecrypt)));
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return null;
	}

	public static void main(String[] args) {

		try {
			UIManager.setLookAndFeel("com.jtattoo.plaf.smart.SmartLookAndFeel");
		} catch (Exception e) {
		}
		SignUp sign = new SignUp("123");
	}

}
