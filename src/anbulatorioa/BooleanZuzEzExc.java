package anbulatorioa;

public class BooleanZuzEzExc extends Exception{
	String pMezua="true edo false sartu behar duzu";
	
	public BooleanZuzEzExc() {
		super();
	}
	
	public void inprimatu() {
		System.out.println(pMezua);
	}
}
