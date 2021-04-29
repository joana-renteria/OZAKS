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

	public void administratu() {
		Reader rd= Reader.getReader();
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
					botikaBerria();
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
	public void datuakAldatu() {
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
	
	private void gaixoaGehitu(int pNAN, int pZenb, String pIzen,
			String pAbiz, String pSex, Date pData, String pZentr,
			int pHospDago, String pNonBizi, String pOdol) throws SQLException, KonexioarenParamFaltaException {
		Statement s = Konexioa.getKonexioa().createStatement();
		String sql = "INSERT INTO gaixoa("+pNAN+","+pZenb+",'"+pIzen+"','"+pAbiz+"','"+pSex+"','"+pData.toString()+"','"+pZentr+"','"+pHospDago+"','"+pNonBizi+"','"+pOdol+"')";
		s.executeUpdate(sql);
	}
	
	private void medikuaGehitu(int pNAN, int pZenb, String pIzen,
			/*String pAbiz, String pSex, Date pData, String pZentr,
			int pHospDago, String pNonBizi, String pOdol) throws SQLException, KonexioarenParamFaltaException {
		Statement s = Konexioa.getKonexioa().createStatement();
		String sql = "INSERT INTO gaixoa("+pNAN+","+pZenb+",'"+pIzen+"','"+pAbiz+"','"+pSex+"','"+pData.toString()+"','"+pZentr+"','"+pHospDago+"','"+pNonBizi+"','"+pOdol+"')";
		s.executeUpdate(sql);*/ 
			//TODO
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
	
	private boolean botikaEman() {
		//TODO gaixoari botika eman
		return true;
	}
	
	private boolean botikaKendu() {
		//TODO gaixoari botika kendu
		return true;
	}
	
}
