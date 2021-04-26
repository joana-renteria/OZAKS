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
import java.util.Scanner;

public class Main {
	private BufferedReader br;
	private static Admin ad;
	private static Erabiltzailea er;
	private static String izena = null;
	//private Connection konexioa;
	
	private static void konektatu() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			String url = "jdbc:mysql://localhost:3306/anbulatorioa";
			Connection konexioa = DriverManager.getConnection(url,"admin","db");
			System.out.println("Konektatua!!");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static int login() {
		Reader rd = Reader.getReader();
		String izena = rd.irakurri("Sartu erabiltzaile izena: ");
		System.out.println(izena);
		boolean denaOndo = false;
		int saiakera = 3;
		int kodea = -1;
		if(izena == "admin") {
			try {
				do {				
					if(!ad.pasahitzaEgokia(rd.irakurri("Sartu pasahitza: "))) {
						throw new PasahitzaOkerra();
					}
					denaOndo=true;
					kodea = 1;
				} while (!denaOndo && saiakera>0);
			} catch (PasahitzaOkerra e) {
				e.mezuaImprimatu();
				saiakera--;
			}
		} else {kodea = 0;}
		return kodea;
	}
	
	public static void main(String[] args) throws NumberFormatException, IOException, SQLException {
		//konektatu();
		int lg = login();
		if(lg == 1) {
			ad = Admin.getAdmin();
			ad.administratu();
		} else if(lg == 0) {
			er = Erabiltzailea.getErabil(izena);
			er.erabili();
		} else System.exit(-1);
	}

}
