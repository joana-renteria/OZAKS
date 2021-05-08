package anbulatorioa;

import java.sql.SQLException;
import java.sql.Date;
import java.sql.Statement;

public class Admin {
	private String pasahitza = "db";
	private static Admin nireAdmin = null;
	
	private Admin() {
		
	}
	
	public static Admin getAdmin() {
		if(nireAdmin == null) {
			nireAdmin = new Admin();
		}
		return nireAdmin;
	}
	
	public boolean pasahitzaEgokia(String pPass) {
		return (pPass.equals(pasahitza));
	}

	public void gaixoaNAN() {
		Reader rd= Reader.getReader();
		int gaixNAN=rd.irakurriInt("Gaixoaren NAN sartu");
		String sql = "SELECT COUNT(Gaixoa.*) FROM Gaixoa WHERE NAN="+gaixNAN";
		if ()
		System.out.println ("1- Gaixo berria sartu");
		
		System.out.println("");
	}
	
	
	public void administratu() throws SQLException, KonexioarenParamFaltaException {
		Reader rd= Reader.getReader();
		//TODO HONEN AURRETIK GAIXOAREN NAN SARTU
		System.out.println ("-----------------");
		System.out.println ("1- Zita eman");
		System.out.println ("2- Botika eman");
		System.out.println ("3- Gaixoaren datuak aldatu");
		System.out.println ("4- Exit");
		System.out.println ("-----------------");
		int zenb=rd.irakurriInt("Sartu zenbaki bat");
		while (zenb!=4) {
			switch (zenb) {
				case 1: 
					zitaEman();
					break;
				case 2:
					botikaEman(rd.irakurriInt("Botikaren kodea"),
							rd.irakurriInt("Pazientearen NANa:"),
							rd.irakurri("Botikaren marka:"),
							rd.irakurri("Botikaren izena:"),
							rd.irakurriInt("Dosi kopurua:"));
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
	
	public void zitaEman() {
		
	}
	public void datuakAldatu() throws SQLException, KonexioarenParamFaltaException  {
		Reader rd = Reader.getReader();
		System.out.println ("-------GAIXOAREN ZEIN DATU----------");
		System.out.println ("1- Hospitalean badago ");
		System.out.println ("2- Ohiko zentroa");
		System.out.println ("3- Telefono");
		System.out.println ("4- Non bizi");
		System.out.println ("5- Bueltatu lehengo menura");
		System.out.println ("-----------------");
		int zenb=rd.irakurriInt("Sartu zenbaki bat");
		while (zenb != 5) {
			switch (zenb) {
				case 1: 
					
					break;
				case 2:
					
					break;
				case 3: 
				
					break;
				case 4:
					
					break;
				default:
					System.out.println("Saiatu berriro.");
					break;
			}
		} 	if (zenb == 5) {
			administratu();
		}
	}
	
	private boolean gaixoaGehitu(int pNAN, int pZenb, String pIzen,
			String pAbiz, String pSex, Date pData, String pZentr,
			int pHospDago, String pNonBizi, String pOdol) throws SQLException, KonexioarenParamFaltaException {
		String sql = "INSERT INTO gaixoa("+pNAN+","+pZenb+",'"+pIzen+"','"+pAbiz+"','"+pSex+"','"+pData.toString()+"','"+pZentr+"','"+pHospDago+"','"+pNonBizi+"','"+pOdol+"')";
		return Konexioa.getKonexioa().aldaketa(sql);
	}
	
	private boolean medikuaGehitu(int pNAN, int pZenb, String pIzen,
			String pAbiz, String pZentr, Date pData, int pFamili,
			String pEspezial, int pTelf) throws SQLException, KonexioarenParamFaltaException {
		String sql = "INSERT INTO medikua("+pNAN+","+pZenb+",'"+pIzen+"','"+pAbiz+"','"+pZentr+"','"+pData.toString()+"','"+pFamili+"','"+pEspezial+"','"+pTelf+"')";
		return Konexioa.getKonexioa().aldaketa(sql);
	}
	
	private boolean botikaGehitu(String pBotika) {
		//TODO botika gehitu, true gauxatu baden
		//false botika jada existitzen baden
		return true;
	}
	
	private boolean botikaEzabatu(String pBotika) {
		//TODO botika sistematik ezabatu
		//ezabatzerakoan true bestela false
		return true;
	}
	
	private boolean botikaDago(String pBotika) {
		//TODO botika dagoen konprobatu
		return true;
	}
	
	private boolean botikaEman(int pKodea, int pNan, String pMarka, String pBIzena,
			int pDosiKop) throws SQLException, KonexioarenParamFaltaException {
		String sql = "INSERT INTO botika(NULL,NULL,'"+pKodea+"','"+pBIzena+"','"+pMarka+"','"+pDosiKop+"')";
		return Konexioa.getKonexioa().aldaketa(sql);
	}
	
	private boolean botikaKendu(int pKodea, int pNan, String pBIzena) throws SQLException, KonexioarenParamFaltaException {
		String sql = "DELETE FROM botika WHERE KODEA="+pKodea+",GAIXONAN="+pNan+",IZENA="+pBIzena;
		return Konexioa.getKonexioa().aldaketa(sql);
	}
	
}
