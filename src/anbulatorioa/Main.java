package anbulatorioa;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;

public class Main {
	private BufferedReader br;
	//private Connection konexioa;
	
	private static void konektatu() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			String url = "jdbc:mysql://localhost:3306/mysql?";
			Connection konexioa = DriverManager.getConnection(url,"admin","db");
			System.out.println("Konektatua!!");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws NumberFormatException, IOException, SQLException {
		konektatu();
	}

}
