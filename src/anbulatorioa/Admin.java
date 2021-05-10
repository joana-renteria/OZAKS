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
		Reader rd = Reader.getReader();
		String[] auk = {"Zitak erakutsi","Zita eman","Botika eman",
				"Gaixoen datuak administratu","Exit"};
		int zenb = rd.aukerak(auk);
		while (zenb != 4) {
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
					String[] auk2 = {"Paziente baten datuak aldatu","Pazientea gehitu",
							"Pazientea ezabatu","Atzera itzuli"};
					int zenb2 = rd.aukerak(auk2);
					if(zenb2 == 1) {
						datuakAldatu();
					} else if(zenb2 == 2 || zenb2 == 3) {
						datuakAdministratu(zenb2);
					}
					break;
				default:
					System.out.println("Saiatu berriro.");
					break;
			}
		} 
		System.out.println("Saioa itxi da.");
		
	}
	
	public void zitaEman() {
		String[] auk = {};
	}
	
	public void datuakAdministratu(int aukera) {
		Reader rd = Reader.getReader();
		if(aukera == 2) {
			int pNAN = rd.irakurriInt("Gaixoaren NAN: ");
			int pZenb = rd.irakurriInt("Sekuritate Soziala: ");
			String pIzen = rd.irakurri("Izena: ");
			String pAbiz = rd.irakurri("Abizena: ");
			String pSex = rd.irakurri("Generoa: ");
			Date pData = rd.irakurriData("Jaiotze data (UUUU-HH-EE): "); //TODO
			String pZentr = rd.irakurri("Zentroa: ");
			int pHospDago = rd.irakurriInt("Hospitalean dago (1 bai) (0 ez): ");
			String pNonBizi = rd.irakurri("Udalerria: ");
			String pOdol = rd.irakurri("Odol mota: ");
			gaixoaGehitu(pNAN,pZenb,pIzen,pAbiz,pSex,pData,pZentr,pHospDago,pNonBizi,pOdol);
		} if (aukera == 3) {
			gaixoaKendu(rd.irakurriInt("Gaixoaren NAN: "));
		} else break;
	}
	
	public void datuakAldatu() throws SQLException, KonexioarenParamFaltaException  {
		Reader rd = Reader.getReader();
		int gNAN = rd.irakurriInt("Pazientearen NANa: ");
		System.out.println ("\n-------GAIXOAREN ZEIN DATU----------");
		String[] auk = {"Hospitalean badago","Ohiko zentroa","Telefono",
				"Non bizi","Bueltatu lehengo menura"};
		int zenb = rd.aukerak(auk);
		while (zenb != 7) {
			switch (zenb) {
				case 1:
					toggleHospDago(gNAN);
					break;
				case 2:
					zentroAldatu(gNAN, rd.irakurri("Zentro berriaren izena: "));
					break;
				case 3:
					tlfAldatu(gNAN, rd.irakurriInt("Telefono zenbaki berria: "));
					break;
				case 4:
					udalerriAldatu(gNAN, rd.irakurri("Udalerriaren izena: "));
					break;
				case 5:
					generoMarkaAldatu(gNAN, rd.irakurri("Genero marka berria [E/G/X]"));
					break;
				case 7:
					iznAbizAldatu(gNAN, rd.irakurri("Izen berria: "), rd.irakurri("Abizen berria: "));
					break;
				default:
					System.out.println("Saiatu berriro.");
					break;
			}
		} 	if (zenb == 5) {
			administratu();
		}
	}
	
	private boolean iznAbizAldatu(int pNAN, String izen, String abizen) {
		String sql = "UPDATE GAIXOA SET IZENA = "+izen+", ABIZENA = "+abizen+" WHERE NAN = "+pNAN;
		return Konexioa.getKonexioa().aldaketa(sql);
	}

	private boolean generoMarkaAldatu(int pNAN, String generoMarka) {
		String sql = "UPDATE GAIXOA SET SEXUA = "+generoMarka+" WHERE NAN = "+pNAN;
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

	private boolean zentroAldatu(int pNAN, String zentroberria) {
		String sql = "UPDATE GAIXOA SET OHIKOZENTROA = "+zentroberria+" WHERE NAN = "+pNAN;
		return Konexioa.getKonexioa().aldaketa(sql);
	}

	private boolean toggleHospDago(int pNAN)  throws SQLException {
		String sql = "SELECT HOSPITALEANDAGO FROM GAIXOA WHERE NAN="+pNAN;
		ResultSet ospdago = Konexioa.getKonexioa().kontsulta(sql);
		ospdago.next();
		if(ospdago.getInt(0) == 1) {
			System.out.println("Gaixoari alta eman");
			sql = "UPDATE GAIXOA SET HOSPITALEANDAGO = 0 WHERE NAN="+pNAN;
		} else {
			System.out.println("Gaixoa ingresatu");
			sql = "UPDATE GAIXOA SET HOSPITALEANDAGO = 1 WHERE NAN="+pNAN;
		}
		return Konexioa.getKonexioa().aldaketa(sql);
	}

	private boolean gaixoaGehitu(int pNAN, int pZenb, String pIzen,
			String pAbiz, char pSex, String pData, String pZentr,
			int pHospDago, String pNonBizi,int pAdina, int pTelf) throws SQLException, KonexioarenParamFaltaException {
		String sql = "INSERT INTO gaixoa VALUES ("+pNAN+","+pZenb+",'"+pIzen+"','"+pAbiz+"','"+pSex+"','"+pData.toString()+"','"+pZentr+"','"+pHospDago+"','"+pNonBizi+"','"+pAdina+"','"+pTelf+"')";
		return Konexioa.getKonexioa().aldaketa(sql);
	}
	
	private boolean gaixoaKendu(int pNAN) {
		String sql = "DELETE FROM gaixoa WHERE NAN="+pNAN;
		return Konexioa.getKonexioa().aldaketa(sql);
	}
	
	private boolean medikuaGehitu(int pNAN, int pZenb, String pIzen,
			String pAbiz, String pZentr, Date pData, int pFamili,
			String pEspezial, int pTelf) {
		String sql = "INSERT INTO medikua("+pNAN+","+pZenb+",'"+pIzen+"','"+pAbiz+"','"+pZentr+"','"+pData.toString()+"','"+pFamili+"','"+pEspezial+"','"+pTelf+"')";
		return Konexioa.getKonexioa().aldaketa(sql);
	}
	
	private boolean botikaGehitu(int gaixoNAN, int sszenb, int kodea,
			String pIzena, String marka, float dosiKop, Date iraungiData) {
		//TODO botika gehitu, true gauxatu baden
		//false botika jada existitzen baden
		String sql = "INSERT INTO botika("+gaixoNAN+","+sszenb+","+kodea+","+pIzena+",'"+marka+"',"+dosiKop+","+iraungiData.toString()+"";
		return Konexioa.getKonexioa().aldaketa(sql);
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
	
	private boolean botikaEman(int pKodea, int pNan, String pMarka,
			String pBIzena, int pDosiKop) {
		String sql = "INSERT INTO botika(NULL,NULL,'"+pKodea+"','"+pBIzena+"','"+pMarka+"','"+pDosiKop+"')";
		return Konexioa.getKonexioa().aldaketa(sql);
	}
	
	private boolean botikaKendu(int pKodea, int pNan, String pBIzena) {
		String sql = "DELETE FROM botika WHERE KODEA="+pKodea+",GAIXONAN="+pNan+",IZENA="+pBIzena;
		return Konexioa.getKonexioa().aldaketa(sql);
	}
	
	
	
	
}
