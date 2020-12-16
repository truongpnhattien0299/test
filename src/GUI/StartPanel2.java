package GUI;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.security.MessageDigest;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;

import static DAL.Cl_Connect.socket;

import static DAL.Cl_Connect.in;
import static DAL.Cl_Connect.out;
import static DAL.Cl_Connect.outobj;
import static DAL.Cl_Connect.inobj;

import GUI.LoginGUI.ButtonLogin;
import static DAL.Cl_Connect.arr_result;

public class StartPanel2 extends JFrame {

	public JPanel start;
	JButton oneButton, btninfo;
	JButton twoButton;
	JButton rankButton;
	JButton exitButton;
	public JLabel lb_user;
	String key = "3";

//    SoundPlayer mySound = new SoundPlayer();

	public StartPanel2() {

		initcomponents();
		actionListener();

	}

	public void initcomponents() {
		start = new JPanel();

		ImagePanel background = new ImagePanel("background1.png", 0, 0, 800, 600);

		oneButton = new JButton("Play");
		btninfo = new JButton("Update Infomation");
		rankButton = new JButton("Rank");
		exitButton = new JButton("Exit");

		lb_user = new JLabel();

		lb_user.setBounds(520, 30, 260, 100);
		lb_user.setText("USERNAME : " + arr_result[1]);
		lb_user.setForeground(Color.RED);
		oneButton.setBounds(350, 350, 100, 30);
		btninfo.setBounds(350, 400, 100, 30);
		rankButton.setBounds(350, 450, 100, 30);
		exitButton.setBounds(350, 500, 100, 30);

		start.add(lb_user);
		start.add(oneButton);
		start.add(btninfo);
		start.add(rankButton);
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

	public void actionListener() {
		ActionListener twoPlay = new twoPlay();
		oneButton.addActionListener(twoPlay);

		ActionListener info = new Info();
		btninfo.addActionListener(info);

		ActionListener rank = new rank();
		rankButton.addActionListener(rank);
	}

	public class twoPlay implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {

			dispose();
			System.out.println("vao ttwo");
			try {
				new StartGUI();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	public class Info implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {

			dispose();
			new SignUp(arr_result[1]);

		}
	}

	public class rank implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {

			try {
				String mahoa = encrypt("rank#" + arr_result[1], key);
				out.write("" + mahoa + "\n");
//				    out.write("rank\n");
				out.flush();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			dispose();
			System.out.println("vao rank");
			try {
				new rankGUI().setVisible(true);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
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
		;
		StartPanel2 start = new StartPanel2();
	}

}
