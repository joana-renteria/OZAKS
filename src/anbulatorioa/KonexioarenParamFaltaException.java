package anbulatorioa;

public class KonexioarenParamFaltaException extends Exception {
	private String mezua = "ERROR: Zerbitzarira konektatzeko parametroak falta dira";
	
	public KonexioarenParamFaltaException() {
		super();
	}
	
	public void mezuaImprimatu() {
		System.err.println(mezua);
	}
}
