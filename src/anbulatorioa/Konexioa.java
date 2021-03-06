package anbulatorioa;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Konexioa {
	private static Konexioa nireKonexioa;
	private static Connection konexioa;
	private static String helb;
	private static String erab;
	private static String pasa;
	

	private Konexioa() {
		System.out.println(helb);
		this.konektatu();
	}
	
	public static Konexioa getKonexioa() {
		try {
			if(helb == null ||
					erab == null ||
					pasa == null) {
				throw new KonexioarenParamFaltaException();
					}
			if(nireKonexioa == null) {
				nireKonexioa = new Konexioa();
			}
		} catch (KonexioarenParamFaltaException e) {
			e.printStackTrace();
		}
		return nireKonexioa;
	}
	
	public static Konexioa getKonexioa(String pHelb, String pErab, String pPasa) throws KonexioarenParamFaltaException {
		try {
			setDatuak(pHelb,pErab,pPasa);
			if(helb == null ||
					erab == null ||
					pasa == null) {
				throw new KonexioarenParamFaltaException();
					}
			if(nireKonexioa == null) {
				nireKonexioa = new Konexioa();
			}
		} catch (KonexioarenParamFaltaException e) {
			e.printStackTrace();
		}
		return nireKonexioa;
	}

	public static void setDatuak(String pHelb, String pErab, String pPasa) {
		helb = pHelb;
		erab = pErab;
		pasa = pPasa;
	}

	private void konektatu() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			konexioa = DriverManager.getConnection("jdbc:mysql://"+this.helb,this.erab,this.pasa);
			System.out.println("Konektatua!!");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Statement createStatement() throws SQLException {
		return konexioa.createStatement();
	}
	
	public PreparedStatement preparedStatement (String lortu) throws SQLException {
		return konexioa.prepareStatement(lortu);
	}
	
	public ResultSet kontsulta(String sql) {
		ResultSet em;
		try {
			Statement s = this.konexioa.createStatement();
			em = s.executeQuery(sql);
		} catch (SQLException e) {
			System.out.println("Txarto exekutatu da, konexioak hutsa egin du");
			e.printStackTrace();
			return null;
		}
		return em;
	}
	
	public boolean aldaketa(String sql) {
		try {
			Statement s = this.konexioa.createStatement();
			s.executeUpdate(sql);
		} catch (SQLException e) {
			System.out.println("Txarto exekutatu da, konexioak hutsa egin du");
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
}
