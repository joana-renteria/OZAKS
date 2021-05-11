package anbulatorioa;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.sql.Date;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.ArrayList;
import java.util.Arrays;
import java.sql.Time;
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
		return emaitza;
	}
	
	public char irakurriChar(String pMezua) {
		System.out.println(pMezua);
		char karakterea=sc.next().charAt(0);
		return karakterea;
	}
	
	public int irakurriInt(String pMezua) {
		//TODO try and catch y eso
		String ema = this.irakurri(pMezua);
		int ema2 = Integer.parseInt(ema);
		return ema2;
	}
	
	public float irakurriFloat(String pMezua) {
		String ema = this.irakurri(pMezua);
		float ema2 = Float.parseFloat(ema);
		return ema2;
	}
	
	public int irakurriIntTarte (String pMezua, int min, int max) {
		boolean denaOndo=false;
		int pZenb=0;
		do {
			try {
				pZenb=this.irakurriInt(pMezua);
				if (pZenb<min ||pZenb>min) {
					throw new TarteaEzExc();
				}
				denaOndo=true;
			}catch (TarteaEzExc e) {
				e.inprimatu(min, max);
			}
		}while (!denaOndo);
		return pZenb;
	}
	
	public String sartuLetraLarriXehe(String pMota) {
		boolean denaOndo=false;
		int saiakerak=2;
		String pIzen="NULL";
		do {
			try {
				pIzen=this.irakurri("Sartu gaixoaren"+pMota+":");
				char [] zuzena=pIzen.toCharArray();
				if (Character.isLowerCase(zuzena[0])) {
					throw new LetraLarriXeheExc();
				}
				for (int i=1;i<zuzena.length;i++) {
					if (Character.isUpperCase(zuzena[i])) {
						throw new LetraLarriXeheExc();
					}
				}
				denaOndo=true;
			}catch (LetraLarriXeheExc e) {
				saiakerak--;
				e.inprimatu(pMota);
			}
		}while(!denaOndo && saiakerak>0);
		if (!denaOndo) {
			pIzen="NULL";
		}
		return pIzen;
	}
	
	public int sartuZenb(int pTamaina, String pMota) {
		boolean denaOndo=false;
		int pZenb=-1;
		do{
			try {
				pZenb=this.irakurriInt("Sartu "+pMota+" zenbakia:");
				String x=Integer.toString(pZenb);
				if (x.length()!=pTamaina) {
					throw new TamainaExc();
			}
			denaOndo=true;
			}catch(TamainaExc e) {
				e.inprimatu(pMota,pTamaina);
			}
		}while (!denaOndo);
		denaOndo=false;
		return pZenb;
	}
	
	public char sartuSex() {
		boolean denaOndo=false;
		char pSex='A';
		while (!denaOndo) {
			try {
				pSex=this.irakurriChar("Zein da gaixoaren sexua? E(emakumea), G(gizona) edo X(ez bitarra)");
				if (pSex!='E'|| pSex!='G'|| pSex!='X') {
					throw new SexuaGaizExc();
				}
				denaOndo=true;
			}catch(SexuaGaizExc e) {
				e.inprimatu();
			}
		}
		return pSex;
	}
	
	public String sartuJaioData() {
		int urtea=this.itzuliUrtea();
		int hilab=this.itzuliHilabetea();
		int eguna=this.itzuliEguna(hilab, urtea);
		return urtea+"-"+hilab+"-"+eguna;
	}
	
	public int adinaKalkulatu() {
		int urtea=this.itzuliUrtea();
		int hilab=this.itzuliHilabetea();
		int eguna=this.itzuliEguna(hilab, urtea);
		DateTimeFormatter fmt= DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate jaioData=LocalDate.parse(eguna+"/"+hilab+"/"+urtea, fmt);
		LocalDate unekoa=LocalDate.now();
		Period periodo=Period.between (jaioData, unekoa);
		return periodo.getYears();
	}
	
	private int itzuliUrtea() {
		Date data = new Date(0, 0, 0);
		Calendar egutegia=Calendar.getInstance();
		egutegia.setTime(data);
		int unekoData= egutegia.get(Calendar.YEAR);
		String pMezua="Sartu gaixoa jaiotako urtea";
		int urtea=this.irakurriIntTarte(pMezua, unekoData-130, unekoData);
		return urtea;
	}
	
	private int itzuliHilabetea() {
		String pMezua="Sartu gaixoa jaiotako hilabetea";
		int hilab=this.irakurriIntTarte(pMezua, 01, 12);
		return hilab;
	}
	
	private int itzuliEguna(int hilab, int urtea) {
		boolean denaOndo=false;
		int[] egunaHil= {31,28,31,30,31,30,31,31,30,31,30,31};
		int pMax=0, eguna=0;
		while (!denaOndo) {
			try{
				eguna=this.irakurriInt("Sartu gaixoa jaiotako eguna");
				int i=1;
				while (i<hilab) {
					i++;
				}
				if (egunaHil[i]<eguna ||eguna<1) {
					pMax =egunaHil[i];
					throw new TarteaEzExc();
				} else if (hilab==2 && urtea%4==0 && (eguna>29 ||eguna<1)) {
					pMax=29;
					throw new TarteaEzExc();
				}
				denaOndo=true;
			}catch (TarteaEzExc e) {
				e.inprimatu(1, pMax);
			}
		}
		return eguna;
	}
	
	public int sartuBoolean () {
		String pEmaitza =this.irakurri("Gaixoa hospitalean dago? true edo false sartu");
		int i=0;
		boolean denaOndo=false;
		while (!denaOndo) {
			try {
				if (!pEmaitza.equals("true") ||!pEmaitza.equals("false")) {
					throw new BooleanZuzEzExc();
				}
				if (pEmaitza.equals("true")){
					i=1;		
				}
				denaOndo=true;
			}catch (BooleanZuzEzExc e) {
				e.inprimatu();
			}
		}
		return i;
	}

	public Date irakurriData(String pMezua) {
		String irak = this.irakurri(pMezua);
		Date ema = Date.valueOf(irak);
		return ema;
	}
	
	public Time irakurriOrdua(String pMezua) {
		String irak = this.irakurri(pMezua);
		Time ema = Time.valueOf(irak);
		return ema;
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
