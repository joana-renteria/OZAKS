package anbulatorioa;

import java.sql.SQLException;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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

	public void gaixoaEzDago(int pNAN) throws SQLException, KonexioarenParamFaltaException {
		boolean denaOndo=false;
		Reader rd =Reader.getReader();
				System.out.println("Sartutako NAN duen gaixorik ez dago");
				System.out.println("NAN hori duen gaixo berriaren datuak sartu nahi duzu?");
				char karak=rd.irakurriChar("Bai (B) edo ez (E) sakatu");
				while (!denaOndo) {
					switch (karak) {
						case 'B':
							int pZenb=rd.sartuZenb(12, "seguritate sozialeko");
							String pIzen=rd.sartuLetraLarriXehe("izena");
							String pAbiz=rd.sartuLetraLarriXehe("abizena");
							char pSex=rd.sartuSex();
							String pData=rd.sartuJaioData();
							String pZentr=rd.sartuLetraLarriXehe("zentroa");
							int pHosDago= rd.sartuBoolean() ;
							String pNonBizi =rd.sartuLetraLarriXehe("bizi lekua");
							int pAdina=rd.adinaKalkulatu();
							int pTelf= rd.sartuZenb(9, "telefonoko");
							this.gaixoaGehitu(pNAN, pZenb, pIzen, pAbiz, pSex, pData, pZentr, pHosDago, pNonBizi, pAdina, pTelf);
							denaOndo=true;
							break;
						case 'E':
							this.sartuNan();
							denaOndo=true;
							break;
						default:
							System.out.println("Ez duzu ez B, ezta E idatzi");
							break;
				}
			}
		}
	}
	
	public void sartuNan() {
		int n=0;
		boolean ondoDago=false;
		Reader rd= Reader.getReader();
		int gaixNAN=rd.irakurriInt("Gaixoaren NAN sartu");
		String x= Integer.toString(gaixNAN);
		do{
			try{
				if (x.length()!=8) {
					throw new TamainaExc();
				}
				String sql = "SELECT COUNT(Gaixoa.*) FROM Gaixoa WHERE NAN="+gaixNAN;
					//PreparedStatement st= Konexioa.getKonexioa().preparedStatement(sql);
				ResultSet zenbat=Konexioa.getKonexioa().kontsulta(sql);
				if (zenbat.next()) {
					n=zenbat.getInt(1);
				}if (n!=1) {
					this.gaixoaEzDago(gaixNAN);
				}else {
					this.administratu();
				}
				ondoDago=true;
			}catch (TamainaExc e) {
				e.inprimatu("NAN", 8);
			}
		} while (!ondoDago);
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
			String pAbiz, char pSex, String pData, String pZentr,
			int pHospDago, String pNonBizi,int pAdina, int pTelf) throws SQLException, KonexioarenParamFaltaException {
		String sql = "INSERT INTO gaixoa VALUES ("+pNAN+","+pZenb+",'"+pIzen+"','"+pAbiz+"','"+pSex+"','"+pData.toString()+"','"+pZentr+"','"+pHospDago+"','"+pNonBizi+"','"+pAdina+"','"+pTelf+"')";
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
