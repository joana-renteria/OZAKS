package anbulatorioa;

public class LetraLarriXeheExc extends Exception{
	String pMezua="-ren lehen hizkia letra larriz idazten da eta gainerakoa minuskulaz";
	
	public LetraLarriXeheExc() {
		super();
	}
	
	public void inprimatu (String pMota) {
		System.out.println("pMota"+pMezua);
	}
}
