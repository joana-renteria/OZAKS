package anbulatorioa;

import java.util.Scanner;

public class Reader {
	
	private static Reader nireReader = null;
	private Scanner sc;

	private Reader() {
		sc = new Scanner(System.in);
	}
	
	public static Reader getReader() {
		if(Reader.nireReader == null) {
			Reader.nireReader = new Reader();
		}
		return nireReader;
	}
	
	public String irakurri(String pMezua) {
		System.out.println(pMezua);
		String emaitza = sc.next();
		if(sc.hasNext()) {
			sc.nextLine();
		}
		return emaitza;
	}
}
