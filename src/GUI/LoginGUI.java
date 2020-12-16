package GUI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class LoginGUI extends JFrame {

	private JPanel start;
	private JTextField jTextFieldUsername;
	private JButton btnButtonLogin, btnButtonSignup;
	private JPasswordField passwordField;
	public static DAL.Cl_Connect cl;

	Image img;

	public LoginGUI() {
		initcomponents();
		actionListener();
	}

	public void initcomponents() {
		start = new JPanel();

		start = new JPanel();

		ImagePanel background = new ImagePanel("main.png", 0, 0, 800, 600);

		JLabel lblNewLabel = new JLabel("LOGIN");
		lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD, 16));
		lblNewLabel.setBounds(300, 24, 145, 30);
		start.add(lblNewLabel);

		JLabel lblNewLabel_2 = new JLabel("User");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNewLabel_2.setBounds(200, 106, 46, 14);
		start.add(lblNewLabel_2);

		JLabel lblNewLabel_3 = new JLabel("Password");
		lblNewLabel_3.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNewLabel_3.setBounds(200, 142, 63, 14);
		start.add(lblNewLabel_3);

		jTextFieldUsername = new JTextField();
		jTextFieldUsername.setBounds(303, 104, 163, 30);
		start.add(jTextFieldUsername);
		jTextFieldUsername.setColumns(10);

		passwordField = new JPasswordField();
		passwordField.setBounds(300, 141, 163, 30);
		start.add(passwordField);

		btnButtonLogin = new JButton("Login");
		btnButtonLogin.setBounds(270, 270, 230, 60);
		start.add(btnButtonLogin);

		btnButtonSignup = new JButton("Sign Up");
		btnButtonSignup.setBounds(270, 350, 230, 60);
		start.add(btnButtonSignup);

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

	public void actionListener() {
		ActionListener ButtonLogin = new ButtonLogin();
		btnButtonLogin.addActionListener(ButtonLogin);
		ActionListener ButtonSignup = new ButtonSignUp();
		btnButtonSignup.addActionListener(ButtonSignup);
	}

	class ButtonLogin implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {

			String username = jTextFieldUsername.getText();
			String password = String.valueOf(passwordField.getPassword());
			if (username.equals("") || password.equals("")) {
				JOptionPane.showConfirmDialog(rootPane, "Some Fields Are is Empty", "Error", 1);
			} else if (Login(username, password) == true) {
				dispose();
				System.out.println("login success");
				new StartPanel2();
			} else {
				JOptionPane.showConfirmDialog(rootPane, "User or password wrong", "Error", 1);

			}

		}
	}

	class ButtonSignUp implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {

			dispose();
			new SignUp();

		}
	}

	public Boolean Login(String user, String pass) {

		try {
			cl = new DAL.Cl_Connect();
			cl.CreateSocket("127.0.0.1", 1234);
//		    cl.CreateSocket("192.168.137.247", 1234);

			if (cl.Login(user, pass) == true) {

//				cl.StartGUI();
				return true;
			}

		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;

	}

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginGUI frame = new LoginGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
