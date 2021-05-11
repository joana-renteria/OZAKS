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
					this.gaixoaGehitu(pNAN);
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
					n = zenbat.getInt(1);
				} else if (n!=1) {
					this.gaixoaEzDago(gaixNAN);
				} else {
					
				}
				ondoDago=true;
			} catch (TamainaExc e) {
				e.inprimatu("NAN", 8);
			} catch (SQLException s) {
				s.printStackTrace();
			} catch (KonexioarenParamFaltaException k) {
				k.printStackTrace();
			}
		} while (!ondoDago);
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
			this.administratu();//TODO: GEHITU MEDIKUA
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
	
	private void erakutsiOnartuGabekoak(int mNAN) {
		String sql = "SELECT GAIXON,DATA,ORDUA,LEKUA,GELA WHERE ONARTUA=0";
		ResultSet konts = Konexioa.getKonexioa().kontsulta(sql);
		Reader.getReader().kontsultaInprimatu(konts, "GAIXONAN,DATA,ORDUA,LEKUA,GELA");
	}
	
	public boolean zitaEman(int mNAN) {
		//datuak nuluak izan daitezke
		Reader rd = Reader.getReader();
		boolean emaitza=false;
		int gNAN = rd.gaixoaNan();
		if (gNAN!=-1) {
			String pMe1="Sartu aukeratuko dataren urtea";
			String pMe2="Sartu aukeratuko dataren hilabetea";
			String pMe3="Sartu aukeratuko dataren eguna";
			Date zData = rd.irakurriData(pMe1, pMe2, pMe3, rd.itzuliUnekoUrtea(),rd.itzuliUnekoUrtea()+1);
			Time zOrdua = rd.irakurriOrdua("Sartu aukeratutako ordua: ");
			String zLekua = rd.sartuLetraLarriXehe("ohiko zentroaren izena");
			String zGela = rd.irakurri("Sartu gela (String): ");
			//TODO ZITAEMAN EZ LITZATEKE INSERT INTO IZANGO?? BESTELA ZITA GEHITU FALTAKO LITZATEKE
			//ONARTUA FALTA
			String sql = "UPDATE ZITA SET('NULL','NULL'"+",'"+gNAN+"','NULL','"+zData+"','"+zOrdua+"','"+zLekua+"','"+zGela+"')"
					+ "WHERE MEDIKUNAN="+mNAN+"AND DATA='"+zData+"' AND ORDUA='"+zOrdua+"'";
			 emaitza=Konexioa.getKonexioa().aldaketa(sql);
		}
		return emaitza;
	}
	
	public boolean zitaEzabatu(int mNAN) throws SQLException {
		int n=0, saiakerak=3;
		boolean ondo=false, emaitza=false;
		Reader rd = Reader.getReader();
		int gNAN = rd.sartuZenb(8, "pazientearen mota");
		String sql = "SELECT COUNT(GAIXONAN) FROM ZITA WHERE NAN="+mNAN+" AND GAIXONAN="+gNAN;
		ResultSet zenbat=Konexioa.getKonexioa().kontsulta(sql);
		while (!ondo && saiakerak>0) {
			try{
				if (zenbat.next()) {
					n = zenbat.getInt(1);
				}else if (n<1) {	
					throw new DatuaOkerExc();
				} 
				String pMe1="Sartu aukeratuko dataren urtea";
				String pMe2="Sartu aukeratuko dataren hilabetea";			
				String pMe3="Sartu aukeratuko dataren eguna";
				Date zData = rd.irakurriData(pMe1, pMe2, pMe3, rd.itzuliUnekoUrtea(),rd.itzuliUnekoUrtea()+1);
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
		// Hainbat egunetako txandak sortzen ditu
		//8:00etatik 14:00era
		//TODO ordutegi ezberdinak gehitu
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
	
	private void datuakAdministratu(int aukera) throws SQLException, KonexioarenParamFaltaException {
		Reader rd = Reader.getReader();
		int gNAN = rd.gaixoaNan();
		if(aukera == 2) {
			if (gNAN==-1) {
				gaixoaGehitu(rd.sartuZenb(8, "pazientearen NAN"));
			} else {
				System.out.println("NAN hori duen pazientea jada existitzen da");
			}
		} else {
			gaixoaKendu(gNAN);
		}
	}
	
	private void datuakAldatu() throws SQLException, KonexioarenParamFaltaException  {
		Reader rd = Reader.getReader();
		int gNAN = rd.gaixoaNan();
		if (gNAN!=-1) {
			System.out.println ("\n-------PAZIENTEAREN ZEIN DATU----------");
			String[] auk = {"Hospitalean badago","Ohiko zentroa","Telefono",
					"Non bizi","Generoa","Izen-abizenak","Bueltatu lehengo menura"};
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
						udalerriAldatu(gNAN,rd.sartuLetraLarriXehe("udalerriaren izena: "));
						break;
					case 5:
						generoMarkaAldatu(gNAN, rd.sartuSex());
						break;
					case 7:
						iznAbizAldatu(gNAN, rd.sartuLetraLarriXehe("izen berria: "), rd.sartuLetraLarriXehe("abizen berria: "));
						break;
					default:
						System.out.println("Saiatu berriro.");
						break;
				}
			} 	if (zenb == 7) {	
				administratu();
			}
		}else {
			this.gaixoaEzDago(rd.sartuZenb(8, "pazientearen NAN"));
		}
	}
	
	private boolean iznAbizAldatu(int pNAN, String izen, String abizen) {
		String sql = "UPDATE GAIXOA SET IZENA = "+izen+", ABIZENA = "+abizen+" WHERE NAN = "+pNAN;
		return Konexioa.getKonexioa().aldaketa(sql);
	}

	private boolean generoMarkaAldatu(int pNAN, char generoMarka) {
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

  private boolean gaixoaGehitu(int pNAN) throws SQLException, KonexioarenParamFaltaException {
		Reader rd= Reader.getReader();
		int pZenb=rd.sartuZenb(12, "seguritate sozialeko");
		String pIzen=rd.sartuLetraLarriXehe("izena");
		String pAbiz=rd.sartuLetraLarriXehe("abizena");
		char pSex=rd.sartuSex();
		String pMe1="Sartu pazientea jaiotako urtea";
		String pMe2="Sartu pazientea jaiotako hilabetea";
		String pMe3="Sartu pazientea jaiotako eguna";
		Date pData=rd.irakurriData(pMe1, pMe2, pMe3,rd.itzuliUnekoUrtea()-130, rd.itzuliUnekoUrtea());
		String pZentr=rd.sartuLetraLarriXehe("zentroa");
		int pHospDago= rd.sartuBoolean("Pazientea hospitalean dago");
		String pNonBizi =rd.sartuLetraLarriXehe("bizi lekua");
		int pAdina=rd.adinaKalkulatu(pData);
		int pTelf= rd.sartuZenb(9, "telefonoko");
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
	
	private boolean botikaEzabatu(int pBotika) {
		//TODO botika sistematik ezabatu
		//ezabatzerakoan true bestela false
		boolean jadaEzab=false;
		if (botikaDago (pBotika)) {
			String sql = "DELTE FROM BOTIKA WHERE KODEA="+pBotika;
			return Konexioa.getKonexioa().aldaketa(sql);
		}
		return false;
	}
	
	private boolean botikaDago(int pKodea) {
		String sql = "SELECT KODEA FROM BOTIKA WHERE KODEA="+pKodea;
		ResultSet em = Konexioa.getKonexioa().kontsulta(sql);
		try {
			em.next();
			if(em.getInt("KODEA") == (pKodea)) return true;
		} catch (SQLException s) {
			s.printStackTrace();
		}
		return false;
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
	
	
	
}
