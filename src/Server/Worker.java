package Server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.math.BigInteger;
import java.net.Socket;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.StringTokenizer;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

//import static Server.;
import static Server.Server.arr_rd;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import DAL.User;

import static Server.Server.point_bonus;
import static Server.Server.minute;
import static Server.Server.key;

public class Worker implements Runnable {

	BufferedReader in;
	BufferedWriter out;
	ObjectOutputStream outobj;
	ObjectInputStream inobj;
	private Socket socket;

	private String id;

	private int idroom = -1;
	private Integer[] arr_num = { 1, 2, 3 };
	private Integer[] temp;

	String msg[];
	String arr_result[];
	private String user;

	public Worker(Socket socket, String id, String user) {
		this.socket = socket;
		this.id = id;
		this.user = "";
	}

	public void run() {
		System.out.println("Client " + socket.toString() + "accepted");

		System.out.println("key  :" + key);
		try {
			findAll();
		} catch (ClassNotFoundException | IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			outobj = new ObjectOutputStream(socket.getOutputStream());
			inobj = new ObjectInputStream(socket.getInputStream());

//			msg = (String[]) inobj.readObject();
//			if (msg[0].equals("cl_login")) {
//				System.out.println("dang nhap vao " + msg[0]);
//				Login();
//			}
//			if (msg[0].equals("cl_signup")) {
//				System.out.println("dang ky vao " + msg[0]);
//				SignUp();
//			}
//			if (msg[0].equals("cl_info")) {
//				System.out.println("update  " + msg[0]);
//				UpdateInfo();
//			}
//			if (msg[0].equals("getuser")) {
//				System.out.println("getuser  " + msg[0]);
//				GetUser();
//			}
			int vitri = 0;

			String mahoaKey = encrypt("key#" + key + "\n", "QuynhAnh");
			out.write("" + mahoaKey + "\n");
			out.flush();

			while (true) {

				String input = "";
				input = in.readLine();

				System.out.println("input : " + input);

				String giaima = decrypt(input, key);
				giaima = giaima.trim();

				System.out.print("giai ma :" + giaima);

				StringTokenizer cat1 = new StringTokenizer(giaima, "#");
				String s1 = cat1.nextToken();

				if (s1.equals("dangnhap")) {
					String user = cat1.nextToken();
					String pass = cat1.nextToken();
					Login(user, pass);

				}
				if (s1.equals("dangky")) {
					String username = cat1.nextToken();
					String password = cat1.nextToken();
					String hoten = cat1.nextToken();
					String gender = cat1.nextToken();
					String dob = cat1.nextToken();
					SignUp(username, password, hoten, gender, dob);

				}
				if (s1.equals("getuser")) {
					String username = cat1.nextToken();
					GetUser(username);
				}
				if (s1.equals("updateuser")) {
					String username = cat1.nextToken();
					String pass = cat1.nextToken();
					String hoten = cat1.nextToken();
					String gender = cat1.nextToken();
					String dob = cat1.nextToken();
					UpdateInfo(username, pass, hoten, gender, dob);
				}
				if (s1.equals("rank")) {
					System.out.print("vao day");
					List<Rank> rankList = new ArrayList<Rank>();
					rankList = findAll();
					String str = cat1.nextToken();
					out.write(str);
					out.flush();

					outobj.writeObject(rankList);
					outobj.flush();
				}
				if (giaima.equals("close")) {
					in.close();
					out.close();
					socket.close();
					break;
				}

				if (giaima.equals("sotieptheo")) {

					vitri++;
					for (Worker worker : Server.workers) {
						if (worker.idroom == idroom) {
							if (ktmayman(temp[vitri])) {
								String mahoa = encrypt("number#" + "mayman#" + temp[vitri] + "\n", key);
								worker.out.write("" + mahoa + "\n");
								worker.out.flush();

							}

							else if (ktuutien(temp[vitri])) {
								String mahoa = encrypt("number#" + "uutien#" + temp[vitri] + "\n", key);

								worker.out.write("" + mahoa + "\n");
								worker.out.flush();

							} else {
								String mahoa = encrypt("number#" + temp[vitri] + "\n", key);
								worker.out.write("" + mahoa + "\n");
								worker.out.flush();

							}
						}
					}
				}
				///////////////////////////////////////////////////////////////////////////////////////////////////////

				if (giaima.equals("room")) {
					for (Room room : Server.rooms) {
						if (room.getPlayer1() == null) {
							System.out.println("vao room 1");

							arr_num = arr_rd;
							temp = arr_rd;

							room.setPlayer1(this);
							room.setUser1(user);

							idroom = room.getId();

							break;
						}
						if (room.getPlayer2() == null) {

							arr_num = arr_rd;
							temp = arr_rd;
							System.out.println("vao room 2");

							room.setPlayer2(this);
							room.setUser2(user);

							idroom = room.getId();

							for (Worker worker : Server.workers) {

								if (worker.idroom == idroom) {

									System.out.print("arr la  : " + arr_num);
									worker.outobj.writeObject(arr_num);
									worker.outobj.flush();

									String mahoa = encrypt("minute#" + minute + "\n", key);
									worker.out.write("" + mahoa + "\n");
									worker.out.flush();

									mahoa = encrypt("user1#" + room.getPlayer1().user + "\n", key);
									worker.out.write("" + mahoa + "\n");
									worker.out.flush();

									mahoa = encrypt("user2#" + room.getPlayer2().user + "\n", key);
									worker.out.write("" + mahoa + "\n");
									worker.out.flush();

									if (ktmayman(temp[0])) {
										mahoa = encrypt("number#" + "mayman#" + temp[0] + "\n", key);
										worker.out.write("" + mahoa + "\n");
										worker.out.flush();

									} else if (ktuutien(temp[0])) {
										worker.out.write("number#" + "uutien#" + temp[0] + "\n");
										worker.out.flush();

									} else {
										mahoa = encrypt("number#" + temp[0] + "\n", key);

										worker.out.write("" + mahoa + "\n");
										worker.out.flush();

									}

								}
							}
							break;
						}

					}
				}

				StringTokenizer cat = new StringTokenizer(giaima, "#");

				if (cat.countTokens() > 1) {
					String s = cat.nextToken();
					if (s.equals("huyplayer")) {
						int dem = 0;
						for (Worker worker : Server.workers) {
							System.out.println("work trong for " + worker);
							System.out.println("worker la : " + worker.id);

							if (worker.idroom == idroom && !worker.id.equals(id) && dem == 0) {
								for (Room room : Server.rooms) {
									System.out.println("worker vo la : " + worker.id);

									if (room.getPlayer1().id == worker.id) {

										room.setPlayer1(null);
										break;
									}
									if (room.getPlayer2().id == worker.id) {

										room.setPlayer2(null);
										break;
									}
								}
								dem++;
								continue;
							}
						}
					}

					///////////////////////////// bat su kien click vao
					///////////////////////////// so///////////////////////////////////////////////////
					if (s.equals("click")) {
						s = cat.nextToken();
						String id_num = cat.nextToken();
						int dem = 0;

						for (Worker worker : Server.workers) {
							System.out.println("work trong for " + worker);
							System.out.println("worker la : " + worker.id);

							if (worker.idroom == idroom && !worker.id.equals(id) && dem == 0) {
								for (Room room : Server.rooms) {
									System.out.println("worker vo la : " + worker.id);

									if (room.getPlayer1().id == worker.id) {
										String mahoa = encrypt("s1_di#" + id_num + "#" + s + "\n", key);
										worker.out.write("" + mahoa + "\n");
										worker.out.flush();
										System.out.println("thang 1 di");

										break;
									}
									if (room.getPlayer2().id == worker.id) {
										String mahoa = encrypt("s2_di#" + id_num + "#" + s + "\n", key);
										worker.out.write("" + mahoa + "\n");
										worker.out.flush();
										System.out.println("thang 2 di");

										break;
									}
								}
								dem++;
								continue;
							}
						}

					}

					if (s.equals("play1Win")) {

						PreparedStatement pst = null;
						Connection conn = null;

						conn = ConnectDB.getConnection();
						String sql = "SELECT * FROM user WHERE user=?";
						pst = (PreparedStatement) conn.prepareStatement(sql);
						pst.setString(1, cat.nextToken());

						ResultSet resultSet = pst.executeQuery();
						if (resultSet.next()) {

							int id_user = resultSet.getInt("id");
							System.out.println("user id  :" + id_user);

							String sql2 = "UPDATE history SET point=point+3  WHERE userId=?";
							pst = (PreparedStatement) conn.prepareStatement(sql2);
							pst.setInt(1, id_user);

							int resultSet2 = pst.executeUpdate();
							if (resultSet2 == 1) {
								System.out.println("update");
							}

						}

					}

					if (s.equals("play2Win")) {

						PreparedStatement pst = null;
						Connection conn = null;

						conn = ConnectDB.getConnection();
						String sql = "SELECT * FROM user WHERE user=?";
						pst = (PreparedStatement) conn.prepareStatement(sql);
						pst.setString(1, cat.nextToken());

						ResultSet resultSet = pst.executeQuery();
						if (resultSet.next()) {

							int id_user = resultSet.getInt("id");
							System.out.println("user id  :" + id_user);

							String sql2 = "UPDATE history SET point=point+3  WHERE userId=?";
							pst = (PreparedStatement) conn.prepareStatement(sql2);
							pst.setInt(1, id_user);

							int resultSet2 = pst.executeUpdate();
							if (resultSet2 == 1) {
								System.out.println("update");
							}

						}

					}
					if (s.equals("value")) {
						String player = cat.nextToken();
						s = cat.nextToken();
						int x = Integer.parseInt(s);

						System.out.println("X la  : " + x);
						System.out.println("play la  : " + player);
						System.out.println("vt la  : " + vitri);

						System.out.println("temp la  : " + temp[vitri]);

						if (x == temp[vitri]) {
							for (Worker worker : Server.workers) {
								if (worker.idroom == idroom) {
									if (ktmayman(x)) {
										String mahoa = encrypt("dung#" + player + "#mayman" + "\n", key);
										worker.out.write("" + mahoa + "\n");
										worker.out.flush();

									}

									else if (ktuutien(x)) {
										String mahoa = encrypt("dung#" + player + "#uutien" + "\n", key);
										worker.out.write("" + mahoa + "\n");
										worker.out.flush();

									} else {
										String mahoa = encrypt("dung#" + player + "#sothuong" + "\n", key);
										worker.out.write("" + mahoa + "\n");
//										worker.out.write("dung#" + player + "#sothuong" + "\n");
										worker.out.flush();
									}

									System.out.println("dung");
								}
							}

						} else {
							for (Worker worker : Server.workers) {
								if (worker.idroom == idroom) {
									String mahoa = encrypt("sai" + "\n", key);
									worker.out.write("" + mahoa + "\n");
//									worker.out.write("sai" + "\n");
									worker.out.flush();

									System.out.println("sai");
								}
							}
						}

					}

				}
////////////////////////////////////////////////////////////////////////////////////////////////////////

			}

		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Boolean ktmayman(int sokt) {
		System.out.println("sokt : " + sokt);
		System.out.println(point_bonus[0]);

		for (int i = 0; i < 25; i++) {
			if (point_bonus[i] == sokt)
				return true;
		}
		return false;
	}

	public Boolean ktuutien(int sokt) {
		System.out.println("souutien : " + sokt);
		System.out.println(point_bonus[0]);

		for (int i = 40; i < 60; i++) {
			if (point_bonus[i] == sokt)
				return true;
		}
		return false;
	}

	public void Login(String user, String pass) throws IOException, ClassNotFoundException {

		System.out.println(user);
		System.out.println(pass);
		PreparedStatement pst = null;
		Connection conn = null;
		try {
			conn = ConnectDB.getConnection();
			String sql = "SELECT *FROM user WHERE user=? and password=?";
			pst = (PreparedStatement) conn.prepareStatement(sql);
			pst.setString(1, user);
			pst.setString(2, hashMD5(pass));
			ResultSet resultSet = pst.executeQuery();
			if (resultSet.next()) {
				this.user = user;

				String[] result = new String[3];
				result[0] = "success";
				result[1] = user;
				result[2] = pass;

				outobj.writeObject(result);
				outobj.flush();

//				
			} else {
				String[] result = new String[1];
				result[0] = "fail";
				outobj.writeObject(result);
				outobj.flush();
			}
		}

		catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			if (pst != null) {
				try {
					pst.close();
				} catch (SQLException ex) {

				}
			}
		}

	}

	public String convertByteToHex(byte[] data) {
		BigInteger number = new BigInteger(1, data);
		String hashtext = number.toString(16);
		while (hashtext.length() < 32) {
			hashtext = "0" + hashtext;
		}
		return hashtext;
	}

	public String hashMD5(String pass) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] messageDigest = md.digest(pass.getBytes());
			return convertByteToHex(messageDigest);
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}

	public Boolean CheckTrung(String username) {
		PreparedStatement pst = null;
		Connection conn = null;
		try {
			conn = ConnectDB.getConnection();
			String sql = "SELECT count(*) as count FROM user WHERE user=?";
			pst = (PreparedStatement) conn.prepareStatement(sql);
			pst.setString(1, username);
			ResultSet resultSet = pst.executeQuery();
			if (resultSet.next()) {
				int count = resultSet.getInt("count");
				if (count == 0)
					return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return true;
	}

	public void SignUp(String username, String password, String hoten, String gender, String dob) {
		if (CheckTrung(username)) {
			String[] rs = new String[1];
			rs[0] = "trungusername";
			try {
				outobj.writeObject(rs);
				outobj.flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return;
		}
		password = hashMD5(password);
		PreparedStatement pst = null;
		Connection conn = null;
		try {
			conn = ConnectDB.getConnection();
			String sql = "INSERT INTO user(user, password, hoten, gioitinh, ngaysinh) VALUES(?, ?, ?, ?, ?)";
			pst = (PreparedStatement) conn.prepareStatement(sql);
			pst.setString(1, username);
			pst.setString(2, password);
			pst.setString(3, hoten);
			pst.setString(4, gender);
			pst.setString(5, dob);
			pst.execute();
			pst.close();
			String[] rs = new String[3];
			rs[0] = "success";
			rs[1] = username;
			rs[2] = password;
			outobj.writeObject(rs);
			outobj.flush();
			return;
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String[] rs = new String[1];
		rs[0] = "fail";
		try {
			outobj.writeObject(rs);
			outobj.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/* == Update thông tin người dùng == */
	public void GetUser(String username) {
		PreparedStatement pst = null;
		Connection conn = null;
//		User user = new User();
		String[] user = new String[4];
		try {
			conn = ConnectDB.getConnection();
			String sql = "SELECT * FROM user WHERE user=?";
			pst = (PreparedStatement) conn.prepareStatement(sql);
			pst.setString(1, username);
			ResultSet resultSet = pst.executeQuery();
			if (resultSet.next()) {
				user[0] = resultSet.getString("password");
				user[1] = resultSet.getString("hoten");
				user[2] = resultSet.getString("gioitinh");
				user[3] = resultSet.getString("ngaysinh");
			}
			out.write(user[0] + "#" + user[1] + "#" + user[2] + "#" + user[3] + "\n");
			out.flush();

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void UpdateInfo(String username, String pass, String hoten, String gender, String dob) {
		String password = hashMD5(pass);
		PreparedStatement pst = null;
		Connection conn = null;
		try {
			conn = ConnectDB.getConnection();
			String sql = "UPDATE user SET password = ?, hoten = ?, gioitinh = ?, ngaysinh = ? WHERE user = ?";
			pst = (PreparedStatement) conn.prepareStatement(sql);
			pst.setString(1, password);
			pst.setString(2, hoten);
			pst.setString(3, gender);
			pst.setString(4, dob);
			pst.setString(5, username);
			pst.executeUpdate();
			pst.close();
			out.write("success#"+username+"#"+password+"\n");
			out.flush();
			return;
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			out.write("fail#\n");
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static List<Rank> findAll() throws IOException, ClassNotFoundException {

		List<Rank> rankList = new ArrayList<>();
		PreparedStatement pst = null;
		Connection conn = null;
		try {
			conn = ConnectDB.getConnection();
			String sql = "SELECT *FROM history";
			pst = (PreparedStatement) conn.prepareStatement(sql);

			ResultSet resultSet = pst.executeQuery();
			while (resultSet.next()) {

				int id_user = resultSet.getInt("userId");

				String sql2 = "SELECT * FROM user WHERE id=? ";
				pst = (PreparedStatement) conn.prepareStatement(sql2);
				pst.setInt(1, id_user);

				ResultSet resultSet2 = pst.executeQuery();
				if (resultSet2.next()) {
					String user_name = resultSet2.getString("user");

					Rank rk = new Rank(resultSet.getInt("userId"), user_name, resultSet.getInt("point"),
							resultSet.getInt("N_match"), resultSet.getInt("win"), resultSet.getInt("lose"));
					rankList.add(rk);
				}
			}

		}

		catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			if (pst != null) {
				try {
					pst.close();
				} catch (SQLException ex) {

				}
			}
		}
		return rankList;

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

}
