package anbulatorioa;

public class DatuaOkerExc extends Exception{
	public DatuaOkerExc() {
		super();
	}
	public void inprimatu (String pMota) {
		System.out.println("Sartu duzun "+pMota+" ez da zuzena");
	}
}
