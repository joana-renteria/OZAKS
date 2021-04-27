package anbulatorioa;

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
	
	private boolean botikaGehitu(String pBotika) {
		//TODO botika gehitu, true gauxatu baden
		//false botika jada existitzen baden
	}
	
	private boolean botikaDago(String pBotika) {
		//TODO botika dagoen konprobatu
	}
	
	private void botikaEman() {
		//TODO gaixoari botika eman
	}
	
	private void botikaKendu() {
		//TODO gaixoari botika kendu
	}
	
}
