package Server;

import java.sql.DriverManager;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;

public class ConnectDB {
	public static Connection getConnection() throws SQLException {
		final String url = "jdbc:mysql://remotemysql.com/N9sAqushb3?autoReconnect=true&useSSL=false";
		final String user = "N9sAqushb3";
		final String pass = "5HZZmDRyKL";
		try {
			Class.forName("com.mysql.jdbc.Driver");
			return (Connection) DriverManager.getConnection(url, user, pass);
		} catch (ClassNotFoundException ex) {
			ex.printStackTrace();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return null;

	}

	public static void main(String[] args) throws SQLException {
		Connection connection = getConnection();

		if (connection != null) {
			System.out.println("Data connection successful");
		} else {
			System.out.println("Data connection failed");
		}

	}
}
