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
	private Konexioa kx;
	private String helb = "localhost/anbulatorioa";
	private String erab = "admin";
	private String pasa = "db";

	
	public static int login() {
		Reader rd = Reader.getReader();
		String izena = rd.irakurri("Sartu erabiltzaile izena: ");
		boolean denaOndo = false;
		int saiakera = 3;
		int kodea = 0;
		if(izena.equals("admin")) {
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
				kodea=-1;
			}
		}
		return kodea;
	}
	
	public static void main(String[] args) throws NumberFormatException, IOException, SQLException, KonexioarenParamFaltaException {
		System.out.println("a");
		kx = Konexioa.getKonexioa(helb,erab,pasa);
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
