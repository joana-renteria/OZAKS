package anbulatorioa;

import java.sql.SQLException;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Time;

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
					Date pData=rd.irakurriData();
					String pZentr=rd.sartuLetraLarriXehe("zentroa");
					int pHosDago= rd.sartuBoolean("Pazientea hospitalean dago");
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

	private boolean medikuaNanBadago(int mNAN) {
		int n=0, saiakerak=3;
		boolean ondoDago=false;
		do{
			try{
				String sql = "SELECT COUNT(MEDIKUA.*) FROM MEDIKUA WHERE NAN="+mNAN;
				ResultSet zenbat=Konexioa.getKonexioa().kontsulta(sql);
				if (zenbat.next()) {
					n = zenbat.getInt(1);
				} else if (n!=1) {
					throw new DatuaOkerExc();
				} 
				ondoDago=true;
			} catch (DatuaOkerExc e) {
				e.inprimatu("NAN");
				saiakerak--;
			} catch (SQLException s) {
				s.printStackTrace();
			}
		} while (!ondoDago &&saiakerak>0);
		if (!ondoDago) {
			System.out.println("NAN sartzeko aukerak bukatu zaizkizu");
		}
		return ondoDago;
	}
	
	
	public void administratu() throws SQLException, KonexioarenParamFaltaException {
		Reader rd = Reader.getReader();
		String[] auk = {"Zitak administratu","Botika eman",
				"Gaixoen datuak administratu","Exit"};
		int zenb = rd.aukerak(auk);
		while (zenb != 4) {
			switch (zenb) {
				case 1: 
					zitak();
					break;
				case 2:
					botikaEman(rd.sartuZenb(3, "Botikaren kodea"),
							rd.sartuZenb(8,"Pazientearen NANa:"),
							rd.irakurriInt("Sartu botikaren kodea :"),
							rd.irakurri("Sartu botikaren marka:"),
							rd.irakurri("Sartu botikaren izena:"),
							rd.irakurriFloat("Sartu botikaren dosi kopurua:"));
					//TODO IRAUNGIDATA FALTA
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
	
	public void zitak() throws SQLException, KonexioarenParamFaltaException {
		Reader rd = Reader.getReader();
		int mNAN=rd.sartuZenb(8, "medikuaren NAN");
		boolean badago=this.medikuaNanBadago(mNAN);
		if (badago) {
			String[] auk = {"Zitak erakutsi","Zita eman",
				"Zita bat ezabatu","Atzera"};
			int zenb = rd.aukerak(auk);
			while (zenb != 4) {
				switch (zenb) {
					case 1:
						zitakErakutsi(mNAN);
						break;
					case 2:
						zitaEman(mNAN);
						break;
					case 3:
						zitaEzabatu(mNAN);
						break;
					default:
						System.out.println("Saiatu berriro");
				}
			}
		} else {
			this.administratu();
		}
	}
	
	private void zitakErakutsi(int mNAN) {
		String sql = "SELECT GAIXONAN,DATA,ORDUA,LEKUA,GELA FROM ZITA WHERE MEDIKUANAN="+mNAN+" AND DATA>=CURRENT_DATE";
		ResultSet em = Konexioa.getKonexioa().kontsulta(sql);
		try {
			System.out.println();
			System.out.println("| Gaixoa \t| Data  \t| Ordua \t| Lekua \t| Gela");
			System.out.println("-------------------------------------------------------------------------------");
			while(em.next()) {
				System.out.println(em.getString("GAIXONAN")+"\t"+
						em.getString("DATA")+"\t"+em.getString("ORDUA")+"\t"+
						em.getString("LEKUA")+"\t"+em.getString("GELA"));
			}
		} catch (SQLException e) {
			System.out.println("Emaitzak jasotzen errorea");
			e.printStackTrace();
		}
	}
	
	private void zitaHistorial(int mNAN) {
		String sql = "SELECT GAIXONAN,DATA,ORDUA,LEKUA,GELA FROM ZITA WHERE MEDIKUANAN=\"+mNAN+\" AND DATA<CURRENT_DATE";
		ResultSet em = Konexioa.getKonexioa().kontsulta(sql);
		try {
			System.out.println();
			System.out.println("| Gaixoa \t| Data  \t| Ordua \t| Lekua \t| Gela");
			System.out.println("-------------------------------------------------------------------------------");
			while(em.next()) {
				System.out.println(em.getString("GAIXONAN")+"\t"+
						em.getString("DATA")+"\t"+em.getString("ORDUA")+"\t"+
						em.getString("LEKUA")+"\t"+em.getString("GELA"));
			}
		} catch (SQLException e) {
			System.out.println("Emaitzak jasotzen errorea");
			e.printStackTrace();
		}
	}
	
	public boolean zitaEman(int mNAN) {
		//TODO zita nuluak sortzeko aukera eman aka txandak
		Reader rd = Reader.getReader();
		int gNAN = rd.sartuZenb(8, " gaixoaren NAN");
		Date zData = rd.irakurriData("Sartu aukeratutako data: ");
		Time zOrdua = rd.irakurriOrdua("Sartu aukeratutako ordua: ");
		String zLekua = rd.sartuLetraLarriXehe("ohiko zentroaren izena");
		String zGela = rd.irakurri("Sartu gela (String): ");
		//TODO ZITAEMAN EZ LITZATEKE INSERT INTO IZANGO?? BESTELA ZITA GEHITU FALTAKO LITZATEKE
		//ONARTUA FALTA
		String sql = "UPDATE ZITA SET('NULL','NULL'"+",'"+gNAN+"','NULL','"+zData+"','"+zOrdua+"','"+zLekua+"','"+zGela+"')"
				+ "WHERE MEDIKUNAN="+mNAN+"AND DATA='"+zData+"' AND ORDUA='"+zOrdua+"'";
		return Konexioa.getKonexioa().aldaketa(sql);
	}
	
	public boolean zitaEzabatu(int mNAN) throws SQLException {
		int n=0, saiakerak=3;
		boolean ondo=false, emaitza=false;
		Reader rd = Reader.getReader();
		int gNAN = rd.sartuZenb(8, "gaixoaren NAN");
		String sql = "SELECT COUNT(GAIXONAN) FROM ZITA WHERE NAN="+mNAN+" AND GAIXONAN="+gNAN;
		ResultSet zenbat=Konexioa.getKonexioa().kontsulta(sql);
		while (!ondo && saiakerak>0) {
			try{
				if (zenbat.next()) {
					n = zenbat.getInt(1);
				}else if (n<1) {
					throw new DatuaOkerExc();
				} 
				Date zData = rd.irakurriData("Data: ");
				Time zOrdua = rd.irakurriOrdua("Ordua: ");
				sql = "DELETE FROM ZITA WHERE GAIXONAN = "+gNAN+" AND DATA = "+zData.toString()+" AND zOrdua = "+zOrdua.toString();
				emaitza=Konexioa.getKonexioa().aldaketa(sql);
				ondo=true;
			}catch (DatuaOkerExc e) {
				e.inprimatu("pazientearen NAN");
			}
		}
		if (!ondo) {
			System.out.println("NAN-a ondo sartzeko saiakerak bukatu zaizkizu.");
		}
		return  emaitza;
	}
	
	private boolean txandaGeneratu(int mNAN, int egunak) {
		boolean em = true;
		String sql;
		int i = 0;
		int zOrdua = 8;
		while(i != egunak || em) {
			i++;
			while(zOrdua < 14 || em) {
				sql = "INSERT INTO ZITA (MEDIKUNAN,DATA,ORDUA) VALUES('"+mNAN+"',DATEADD(day,"+i+",CURRENT_DATE,'"+zOrdua+"')";
				em = Konexioa.getKonexioa().aldaketa(sql);
				zOrdua++;
			}
		}
		return em;
	}
	
	private void datuakAdministratu(int aukera) {
		
		char pSex=rd.sartuSex();
		String pData=rd.sartuJaioData();
		String pZentr=rd.sartuLetraLarriXehe("zentroa");
		int pHosDago= rd.sartuBoolean() ;
		String pNonBizi =rd.sartuLetraLarriXehe("bizi lekua");
		int pAdina=rd.adinaKalkulatu();
		int pTelf= rd.sartuZenb(9, "telefonoko");
		Reader rd = Reader.getReader();
		if(aukera == 2) {
			int pNAN = rd.sartuZenb(8, "pazientearen NAN");
			int pZenb = rd.sartuZenb(12, "pazientearen Seguritate Sozialeko");
			String pIzen=rd.sartuLetraLarriXehe("pazientearen izena");
			String pAbiz=rd.sartuLetraLarriXehe("pazientearen abizena");
			Char pSex = rd.irakurriChar("Generoa")rd.irakurri("Generoa: ");
			String pData = rd.irakurri("Jaiotze data (UUUU-HH-EE): "); //TODO
			String pZentr = rd.irakurri("Zentroa: ");
			int pHospDago = rd.irakurriInt("Hospitalean dago (1 bai) (0 ez): ");
			String pNonBizi = rd.irakurri("Udalerria: ");
			String pOdol = rd.irakurri("Odol mota: ");
			gaixoaGehitu(pNAN,pZenb,pIzen,pAbiz,pSex,pData,pZentr,pHospDago,pNonBizi,pOdol);
		} if (aukera == 3) {
			gaixoaKendu(rd.irakurriInt("Gaixoaren NAN: "));
		} else {
			
		}
	}
	
	private void datuakAldatu() throws SQLException, KonexioarenParamFaltaException  {
		Reader rd = Reader.getReader();
		int gNAN = rd.sartuZenb(8, "pazientearen NAN");
		int gSSzenb=rd.sartuZenb(12, "pazientearen Seguritate Sozialeko zenbakia");
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
					zentroAldatu(gNAN, rd.sartuLetraLarriXehe("zentro berriaren izena"));
					break;
				case 3:
					tlfAldatu(gNAN, rd.sartuZenb(9, "telefono berriaren "));
					break;
				case 4:
					udalerriAldatu(gNAN, rd.sartuLetraLarriXehe("udalerriaren izena: "));
					break;
				case 5:
					generoMarkaAldatu(gNAN, rd.sartuSex());
					break;
				case 7:
					iznAbizAldatu(gNAN, rd.irakurri("izen berria: "), rd.irakurri("abizen berria: "));
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

	private boolean generoMarkaAldatu(int pNAN, Char generoMarka) {
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
			String pAbiz, char pSex, Date pData, String pZentr,
			int pHospDago, String pNonBizi,int pAdina, int pTelf) throws SQLException, KonexioarenParamFaltaException {
		String sql = "INSERT INTO GAIXOA VALUES ("+pNAN+","+pZenb+",'"+pIzen+"','"+pAbiz+"','"+pSex+"','"+pData.toString()+"','"+pZentr+"','"+pHospDago+"','"+pNonBizi+"','"+pAdina+"','"+pTelf+"')";
		return Konexioa.getKonexioa().aldaketa(sql);
	}
	
	private boolean gaixoaKendu(int pNAN) {
		String sql = "DELETE FROM GAIXOA WHERE NAN="+pNAN;
		return Konexioa.getKonexioa().aldaketa(sql);
	}
	
	private boolean medikuaGehitu(int pNAN, int pZenb, String pIzen,
			String pAbiz, String pZentr, Date pData, int pFamili,
			String pEspezial, int pTelf) {
		String sql = "INSERT INTO MEDIKUA VALUES("+pNAN+","+pZenb+",'"+pIzen+"','"+pAbiz+"','"+pZentr+"','"+pData.toString()+"','"+pFamili+"','"+pEspezial+"','"+pTelf+"')";
		return Konexioa.getKonexioa().aldaketa(sql);
	}
	
	private boolean botikaGehitu(int gaixoNAN, int sszenb, int kodea,
			String pIzena, String marka, float dosiKop, Date iraungiData) {
		//TODO botika gehitu, true gauxatu baden
		//false botika jada existitzen baden
		String sql = "INSERT INTO BOTIKA VALUES("+gaixoNAN+","+sszenb+","+kodea+","+pIzena+",'"+marka+"',"+dosiKop+","+iraungiData.toString()+"";
		return Konexioa.getKonexioa().aldaketa(sql);
	}
	
	private boolean botikaEzabatu(String pBotika) {
		//TODO botika sistematik ezabatu
		//ezabatzerakoan true bestela false
		boolean jadaEzab=false;
		if (botikaDago (pBotika))
		return true;
	}
	
	private boolean botikaDago(String pBotika) {
		//TODO botika dagoen konprobatu
		return true;
	}
	
	private boolean botikaEman(int pKodea, int pSSzenb, int pNan, String pMarka,
			String pBIzena, float pDosiKop) {
		//TODO: IRAUNGI DATA FALTA
		String sql = "INSERT INTO BOTIKA VALUES ("+pNan+","+pSSzenb+",'"+pKodea+"','"+pBIzena+"','"+pMarka+"','"+pDosiKop+"')";
		return Konexioa.getKonexioa().aldaketa(sql);
	}
	
	private boolean botikaKendu(int pKodea) {
		String sql = "DELETE FROM BOTIKA WHERE KODEA="+pKodea;
		return Konexioa.getKonexioa().aldaketa(sql);
	}
	
	
	
	
}
