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
		
	}
}
