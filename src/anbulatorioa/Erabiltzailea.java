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
	
	public void erabili() throws SQLException, KonexioarenParamFaltaException {
		Reader rd= Reader.getReader();
		System.out.println ("-----------------");
		System.out.println ("0- Zita eskatu");
		System.out.println ("1- Dauzkazun zitak erakutsi");
		System.out.println ("2- Botiken informazioa eman");
		System.out.println ("3- Eskatu zure daturen bat aldatzeko");
		System.out.println ("4- Irten");
		System.out.println ("-----------------");
		int zenb=rd.irakurriInt("Sartu zenbaki bat");
		while (zenb!=4) {
			switch (zenb) {
				case 0: 
					zitaEskatu();
					break;
				case 1:
					int gNAN=rd.sartuZenb(8, "zure NAN (pazientea)");
					erakutsiZitak (gNAN);
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
		
		String sql="SELECT DATA, ORDUA FROM MEDIKUA, ZITA WHERE MEDIKUNAN=NAN AND GAIXONAN IS NULL AND (DATA BETWEEN CURRENT_DATE AND ADDD_DATE(CURRENT_DATE+3))";
		
	}
	
	private void erakutsiZitak(int gNAN) throws KonexioarenParamFaltaException, SQLException {
		if (Reader.getReader().gaixoaNanBadago(gNAN)) {
			String sql = "SELECT MEDIKU.IZENA, MEDIKUA.ABIZENA, GAIXOA.IZENA, GAIXOA.ABIZENA,DATA,"
					+ "ORDUA,LEKUA,GELA FROM ZITA, MEDIKUA, GAIXOA WHERE MEDIKUANAN=NAN AND DATA>=CURRENT_DATE "
					+ "AND LEKUA=ZENTROA AND GAIXOA.NAN=GAIXONAN AND GAIXONAN="+gNAN;
			ResultSet em = Konexioa.getKonexioa().kontsulta(sql);
			try {
				System.out.println();
				System.out.println("| Mediku izena \t| Mediku abizena  \t| Pazientearen izena \t| Pazientearen abizena"
						+ " \t|  Ordua \\t| Lekua \\t| Gela");
				System.out.println("-------------------------------------------------------------------------------");
				while(em.next()) {
					System.out.println(em.getString("MEDIKU.IZENA")+"\t"+
							em.getString("MEDIKUA.ABIZENA")+"\t"+em.getString("GAIXOA.IZENA")+"\t"+
							em.getString("GAIXOA.ABIZENA")+"\t"+
							em.getString("DATA")+"\t"+em.getString("ORDUA")+"\t"+
							em.getString("LEKUA")+"\t"+em.getString("GELA"));
				}
			} catch (SQLException e) {
				System.out.println("Emaitzak jasotzen errorea");
				e.printStackTrace();
			}
		} else {
			this.erabili();
		}
	}
	
	private void botikaInprimatu() throws SQLException, KonexioarenParamFaltaException {
		Reader rd= Reader.getReader();
		int gNan=rd.sartuZenb(8, "gaixoaren NAN");
		String sql = "SELECT IZENA,MARKA,DOSIKOP,IRAUNGIDATA FROM BOTIKA WHERE GAIXONAN="+gNan+ "AND IRAUNGIDATA>=CURRENT_DATE" ;
		ResultSet konts = Konexioa.getKonexioa().kontsulta(sql);
		try {
			System.out.println();
			System.out.println("| Izena \t| Marka  \t| DosiKop \t| IraungiData ");
			System.out.println("-------------------------------------------------------------------------------");
			while (konts.next()) {
				System.out.println(konts.getString("IZENA")+"\t"+
						konts.getString("MARKA")+"\t"+konts.getString("DOSIKOP")+"\t"+
						konts.getString("IRAUNGIDATA"));
			}
		} catch (SQLException e) {
			System.out.println("Emaitzak jasotzen errorea");
			e.printStackTrace();
		}
	}
	
	private void datuakAldatu() {
		
		
	}
	
	
}
