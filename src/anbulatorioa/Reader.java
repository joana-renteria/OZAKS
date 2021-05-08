package anbulatorioa;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
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
	//	if(sc.hasNext()) { //scanner garbitzeko
	//		sc.nextLine();
	//	}
		return emaitza;
	}
	
	public int irakurriInt(String pMezua) {
		//TODO try and catch y eso
		String ema = this.irakurri(pMezua);
		int ema2 = Integer.parseInt(ema);
		return ema2;
	}
	
	public Date irakurriData(String pMezua) {
		//TODO
		String ema = this.irakurri(pMezua);
	}
	
	public int aukerak(String[] lista) {
		System.out.println ("-----------------");
		Iterator<String> itr = Arrays.asList(lista).iterator();
		int i = 1;
		while(itr.hasNext()) {
			System.out.println(i+"- "+itr.next());
			i++;
		}
		System.out.println ("-----------------");
		return irakurriInt("Sartu zenbaki bat: ");
	}
}
