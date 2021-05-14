package anbulatorioa;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;

public class Erabiltzailea {
	private static Erabiltzailea nireErabil = null;
	private String izena;
	private int nan;
	
	private Erabiltzailea(String pIzena, int pNAN) {
		this.izena = pIzena;
		this.nan = pNAN;
	}
	
	public static Erabiltzailea getErabil(String pIzena, int pNAN) {
		if(nireErabil == null) {
			nireErabil = new Erabiltzailea(pIzena, pNAN);
		}
		return nireErabil;
	}
	
	public void erabili() throws SQLException, KonexioarenParamFaltaException {
		Reader rd= Reader.getReader();
		String[] auk = {"Zita eskatu", "Dauzkazun zitak erakutsi",
				"Botiken informazioa eman", "Eskatu zure daturen bat aldatzeko","Irten"};
		int zenb = rd.aukerak(auk);
		while (zenb != 5) {
			switch (zenb) {
				case 1: 
					zitaEskatu();
					break;
				case 2:
					erakutsiZitak (nan);
					break;
				case 3:
					botikaInprimatu();
					break;
				case 4: 
					datuakAldatu();
					break;
				default:
					System.out.println("Saiatu berriro.");
					break;
			}
			zenb = rd.aukerak(auk);
		} 
		System.out.println("Saioa itxi da.");
		
	}
	
	private void erakutsiZitak(int gNAN) throws KonexioarenParamFaltaException, SQLException {
		String sql = "SELECT medikua.IZENA, medikua.ABIZENA, gaixoa.IZENA, gaixoa.ABIZENA,DATA,"
				+ "ORDUA,LEKUA,GELA FROM zita, medikua, gaixoa WHERE DATA>=CURRENT_DATE "
				+ "AND LEKUA=ZENTROA AND gaixoa.NAN="+gNAN;
		ResultSet em = Konexioa.getKonexioa().kontsulta(sql);
		Reader.getReader().kontsultaInprimatu(em, "medikua.IZENA,medikua.ABIZENA,"
				+ "gaixoa.IZENA,gaixoa.ABIZENA,DATA,ORDUA,LEKUA,GELA");
	}
	
	private void zitaEskatu() {
		Reader rd = Reader.getReader();
		int i = 0;
		while(i < 3) {
			i++;
			String sql = "SELECT DATA, ORDUA FROM medikua, zita WHERE MEDIKUNAN=NAN, GAIXONAN IS NULL, (DATA BETWEEN CURRENT_DATE AND ADD_DATE(CURRENT_DATE+"+i+"))";
			ResultSet konts = Konexioa.getKonexioa().kontsulta(sql);
			rd.kontsultaInprimatu(konts,"DATA,ORDUA");
		}
		String[] auk = {"Jarraitu","Irten"};
		int zenb = rd.aukerak(auk);
		if (zenb == 1) {
			Date zData = rd.irakurriData("Urtea: ", "Hilabetea: ", "Eguna: ", rd.itzuliUnekoUrtea(), rd.itzuliUnekoUrtea()+1);
			Time zOrdua = rd.irakurriOrdua("Ordua: ");
			int mNAN = rd.irakurriInt("Medikua (NAN): ");
			String sql = "UPDATE zita SET GAIXONAN="+nan+", ONARTUA=0 WHERE DATA="+zData
					+" AND ORDUA="+zOrdua+" AND MEDIKUNAN="+mNAN;
			Konexioa.getKonexioa().aldaketa(sql);
		}
	}
	
	private void botikaInprimatu() {
		String sql = "SELECT IZENA, MARKA, DOSIKOP, IRAUNGIDATA FROM dosia WHERE GAIXONAN="+nan;
		ResultSet konts = Konexioa.getKonexioa().kontsulta(sql);
		Reader.getReader().kontsultaInprimatu(konts, "IZENA,MARKA,DOSIKOP,IRAUNGIDATA");
	}
	
	private void datuakAldatu() {
		Reader rd = Reader.getReader();
		int gNAN = rd.gaixoaNan();
		if (gNAN!=-1) {
			System.out.println ("\n-------ZEIN DATU ALDATU NAHI DUZU----------");
			String[] auk = {"Ohiko zentroa","Telefono",
					"Non bizi","Bueltatu lehengo menura"};
			int zenb = rd.aukerak(auk);
			while (zenb != 4) {
				switch (zenb) {
					case 1:
						zentroAldatu(gNAN, rd.sartuLetraLarriXehe("zentro berriaren izena"));
						break;
					case 2:
						tlfAldatu(gNAN, rd.sartuZenb(9, "telefono berriaren "));
						break;
					case 3:
						udalerriAldatu(gNAN,rd.sartuLetraLarriXehe("udalerriaren izena: "));
						break;
					default:
						System.out.println("Saiatu berriro.");
						break;
				}
				zenb = rd.aukerak(auk);
			} 			
		}
	}
	
	private boolean zentroAldatu(int pNAN, String zentroberria) {
		String sql = "UPDATE gaixoa SET OHIKOZENTROA = "+zentroberria+" WHERE NAN = "+pNAN;
		return Konexioa.getKonexioa().aldaketa(sql);
	}

	private boolean udalerriAldatu(int pNAN, String udalerri) {
		String sql = "UPDATE gaixoa SET NONBIZI = "+udalerri+" WHERE NAN = "+pNAN;
		return Konexioa.getKonexioa().aldaketa(sql);
	}

	private boolean tlfAldatu(int pNAN, int tlf) {
		String sql = "UPDATE gaixoa SET TELEFONO = "+tlf+" WHERE NAN = "+pNAN;
		return Konexioa.getKonexioa().aldaketa(sql);
	}
	
}
