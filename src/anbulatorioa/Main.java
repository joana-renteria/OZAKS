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
	private static Admin ad;
	private static Erabiltzailea er;
	private static String izena;
	private static int nan;
	private static Konexioa kx;
	private static String helb = "remotemysql.com:3306/bvcA2AmJT6";
	private static String erab = "bvcA2AmJT6";
	private static String pasa = "a645cRXIen";
	
	public static int login() {
		Reader rd = Reader.getReader();
		String izena = rd.irakurri("Sartu erabiltzaile izena: ");
		boolean denaOndo = false;
		int saiakera = 3;
		int kodea = -1;
		if(izena.equals("admin")||izena.equals("Admin")) {
			ad=Admin.getAdmin();
			String pasa=rd.irakurri("Sartu pasahitza: ");
			while (!denaOndo && saiakera > 1) {
				try {			
					if(!ad.pasahitzaEgokia(pasa)) {
						throw new PasahitzaOkerra();
					}
					denaOndo=true;
					kodea = 1;
				} catch (PasahitzaOkerra e) {
					e.mezuaImprimatu();
					pasa=rd.irakurri("Sartu pasahitza: ");
					saiakera--;
				}
			}
			if (!denaOndo) {
				System.out.println("Saiakera guztiak bukatu zaizkizu.");
			}
		} else {
			try {
				int nan = rd.irakurriInt("Sartu NANa: ");
				ResultSet bIzen = Konexioa.getKonexioa().kontsulta("SELECT IZENA FROM GAIXOA WHERE NAN="+nan);
				if(bIzen.next() && bIzen.getString(0).equals(izena)) {
					kodea = 0;
				}
				//TODO SSZENB konprobatu
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return kodea;
	}
	
	public static void main(String[] args) throws NumberFormatException, IOException, SQLException, KonexioarenParamFaltaException {
		System.out.println("a");
		kx = Konexioa.getKonexioa(helb,erab,pasa);
				
//		String sql = "SELECT IZENA FROM medikua where NAN=12345678;";
//		ResultSet a = kx.kontsulta(sql);
//		System.out.println(a.getString("IZENA"));
//		Reader.getReader().kontsultaInprimatu(a, "IZENA");
		
		int lg = login();
		if(lg == 1) {
			ad = Admin.getAdmin();
			ad.administratu();
		} else if(lg == 0) {
			er = Erabiltzailea.getErabil(izena, nan);
			er.erabili();
		} else System.exit(-1);
		
		System.exit(0);
		
	}

}
