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
	
	private void erakutsiZitak(int gNAN) throws KonexioarenParamFaltaException, SQLException {
		if (Reader.getReader().gaixoaNan()!=-1) {
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
	
	private void zitaEskatu() {
		Reader rd = Reader.getReader();
		int i = 0;
		while(i < 3) {
			i++;
			String sql = "SELECT DATA, ORDUA FROM MEDIKUA, ZITA WHERE MEDIKUNAN=NAN, GAIXONAN IS NULL, (DATA BETWEEN CURRENT_DATE AND ADD_DATE(CURRENT_DATE+"+i+"))";
			ResultSet konts = Konexioa.getKonexioa().kontsulta(sql);
			rd.kontsultaInprimatu(konts,"DATA,ORDUA");
		}
		String[] auk = {"Jarraitu","Irten"};
		int zenb = rd.aukerak(auk);
		if (zenb == 1) {
			Date zData = rd.irakurriData("Data: ");
			Time zOrdua = rd.irakurriOrdua("Ordua: ");
			int mNAN = rd.irakurriInt("Medikua (NAN): ");
			String sql = "UPDATE ZITA SET GAIXONAN="+nan+", ONARTUA=0 WHERE DATA="+zData
					+" AND ORDUA="+zOrdua+" AND MEDIKUNAN="+mNAN;
			Konexioa.getKonexioa().aldaketa(sql);
		}
	}
	
	private void botikaInprimatu() {
		String sql = "SELECT IZENA, MARKA, DOSIKOP, IRAUNGIDATA FROM BOTIKA WHERE GAIXONAN="+nan;
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
			while (zenb != 7) {
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
					case 4:
						erabili();
						break;
					default:
						System.out.println("Saiatu berriro.");
						break;
				}
			} 			
		}
	}
	
	private boolean zentroAldatu(int pNAN, String zentroberria) {
		String sql = "UPDATE GAIXOA SET OHIKOZENTROA = "+zentroberria+" WHERE NAN = "+pNAN;
		return Konexioa.getKonexioa().aldaketa(sql);
	}

	private boolean udalerriAldatu(int pNAN, String udalerri) {
		String sql = "UPDATE GAIXOA SET NONBIZI = "+udalerri+" WHERE NAN = "+pNAN;
		return Konexioa.getKonexioa().aldaketa(sql);
	}

	private boolean tlfAldatu(int pNAN, int tlf) {
		String sql = "UPDATE GAIXOA SET TELEFONO = "+tlf+" WHERE NAN = "+pNAN;
		return Konexioa.getKonexioa().aldaketa(sql);
	}
	
}
