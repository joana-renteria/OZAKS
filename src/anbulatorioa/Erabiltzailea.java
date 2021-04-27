package anbulatorioa;

public class Erabiltzailea {
	private static Erabiltzailea nireErabil = null;
	private String izena;
	
	private Erabiltzailea(String pIzena) {
		this.izena = pIzena;
	}
	
	public static Erabiltzailea getErabil(String pIzena) {
		if(nireErabil == null) {
			nireErabil = new Erabiltzailea(pIzena);
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
	
	private void zitaEskatu() {
		
	}
	private void botikaInprimatu() {
		
	}
	private datuakAldatu() {
		
	}
}
