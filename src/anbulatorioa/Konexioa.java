package anbulatorioa;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Konexioa {
	private static Connection konexioa;
	private String helb;
	private String erab;
	private String pasa;
	

	private Konexioa() {
		System.out.println(helb);
		this.konektatu();
	}
	
	public Konexioa getKonexioa() throws KonexioarenParamFaltaException {
		try {
			if(helb == null ||
					erab == null ||
					pasa == null) {
				throw KonexioarenParamFaltaException;
					}
			if(konexioa == null) {
				konexioa = new Konexioa();
			}
		} catch (KonexioarenParamFaltaException e) {
			e.printStackTrace();
		}
		return konexioa;
	}
	
	public Konexioa getKonexioa(String pHelb, String pErab, String pPasa) throws KonexioarenParamFaltaException {
		try {
			this.setDatuak(pHelb,pErab,pPasa);
			if(konexioa == null) {
				konexioa = new Konexioa();
			}
		} catch (KonexioarenParamFaltaException e) {
			e.printStackTrace();
		}
		return konexioa;
	}

	public void setDatuak(String pHelb, String pErab, String pPasa) {
		this.helb = pHelb;
		this.erab = pErab;
		this.pasa = pPasa;
	}

	private static void konektatu() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			konexioa = DriverManager.getConnection("jdbc:mysql://"+this.helb,this.erab,this.pasa);
			System.out.println("Konektatua!!");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
