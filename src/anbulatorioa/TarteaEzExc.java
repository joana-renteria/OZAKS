package anbulatorioa;

public class TarteaEzExc extends Exception{
	
	public TarteaEzExc() {
		super();
	}
	
	public void inprimatu (int pMin, int pMax) {
		System.out.println("Sartu behar duzun zenbakia "+pMin+" eta "+pMax+" artean egon behar da.");
	}
}
