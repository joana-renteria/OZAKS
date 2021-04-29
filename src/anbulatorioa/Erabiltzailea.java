package anbulatorioa;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
	
	public void erabili() {
		Reader rd= Reader.getReader();
		System.out.println ("-----------------");
		System.out.println ("1- Zita eskatu");
		System.out.println ("2- Botiken informazioa eman");
		System.out.println ("3- Eskatu zure daturen bat aldatzeko");
		System.out.println ("4- Irten");
		System.out.println ("-----------------");
		int zenb=rd.irakurriInt("Sartu zenbaki bat");
		while (zenb!=4) {
			switch (zenb) {
				case 1: 
					zitaEskatu();
					break;
				case 2:
					botikaInprimatu();
					break;
				case 3: 
					datuakAldatu();
					break;
				default:
					System.out.println("Saiatu berriro.");
					break;
			}
		} 
		System.out.println("Saioa itxi da.");
		
	}
	
	private void zitaEskatu() throws SQLException, KonexioarenParamFaltaException {
		//Statement s = Konexioa.getKonexioa().createStatement();
	}
	
	private void botikaInprimatu() throws SQLException, KonexioarenParamFaltaException {
		String sql = "SELECT IZENA, MARKA, DOSIKOP, IRAUNGIDATA FROM BOTIKA WHERE GAIXONAN="+nan;
		ResultSet konts = Konexioa.getKonexioa().kontsulta(sql);
		while (konts.next()) {
			System.out.println("Izena: "+konts.getString("IZENA"));
			System.out.println("Marka: "+konts.getString("MARKA"));
			System.out.println("DosiKop: "+konts.getString("DOSIKOP"));
			System.out.println("IraungiData: "+konts.getString("IRAUNGIDATA"));
		}
		
	}
	
	private boolean datuakAldatu() {
		
		return true;
	}
}
