package anbulatorioa;

public class PasahitzaOkerra extends Exception {
	private String mezua = "Pasahitza okerra";
	
	public PasahitzaOkerra() {
		super();
	}
	
	public void mezuaImprimatu() {
		System.out.println(mezua);
	}
}
