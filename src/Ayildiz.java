import java.util.Comparator;
import java.util.PriorityQueue;


public class Ayildiz {
	private int ks;	//kuyrukta bulunan eleman sayisi
	private long sure;	//hesaplama suresi
	public String[] aYildiz(int basX, int basY, int bitX, int bitY) {
		int f;
		boolean durum = false;
		String gec = "";	//onceden gecilen yerlerin tutuldugu degisken
		int tahta = 12;		//tahta boyutu
		String sonuc[] = new String[1000];
		int uls[] = null;
		int enk;	//ulasamama durumu icin hesap yapilirken kullanilan gecici degiskenler
		int enkYer;	//ulasamama durumu icin hesap yapilirken kullanilan gecici degiskenler
		String yol[];	//hedefe ulasilamamasi durumunda bulunan en yakin yol
		Comparator comp = new DurumComp();
		PriorityQueue kuyruk = new PriorityQueue(1000,comp);	//noktalar kucukten buyuge oncelikli bir kuyruga atilacak
		long startTime = System.nanoTime(); 
		f = heu(bitX,bitY,basX,basY)+geu(basX,basY,basX,basY);
		SimdiDurum s = new SimdiDurum(basX, basY, f);		//su an bulunan noktanin ozelliklerini tasiyan sinif
		s.h += heu(bitX,bitY,basX,basY)+"|";
		s.yollar += s.xEksen+" "+s.yEksen+"|";
		kuyruk.add(s);
		while(kuyruk.size()!=0 && durum!=true){
			SimdiDurum sd = (SimdiDurum) kuyruk.remove();
			while(gec.contains(sd.xEksen+""+sd.yEksen+" ")==true)
			//kuyruktan cekilen noktadan onceden gecildiyese while devam eder
				if(kuyruk.size()==0) {
				//kuyrukta eleman kalmamasina ragmen hedefe varilmamissa hedefe ulasilamiyor demektir
					System.out.println("Ulasilamadi!: "+sd.yollar);
					System.out.println("Heu: "+sd.h);
					sonuc = (sd.h).split("[|]");
					uls = new int[sonuc.length];
					for(int i=0;i<sonuc.length;i++)
						uls[i] = Integer.parseInt(sonuc[i]);
					enk = uls[0];
					enkYer = 0;
					//hedefe en cok yaklasilan yer araniyor
					for(int i=1;i<uls.length;i++)
						if(uls[i]<enk) {
							enk = uls[i];
							enkYer = i;
						}
					sonuc = (sd.yollar).split("[|]");
					yol = new String[enkYer+1];
					for(int i=0;i<enkYer+1;i++)
						yol[i] = sonuc[i];
					return yol;
				} else
					sd = (SimdiDurum) kuyruk.remove();
			gec += sd.xEksen+""+sd.yEksen+" ";
			ks = kuyruk.size();
			if(sd.xEksen==bitX && sd.yEksen==bitY) {
				//hedefe ulasildi mi kontrolu yapiliyor
				durum=true;
				System.out.println("Ulasildi: "+sd.yollar);
				sonuc = (sd.yollar).split("[|]");
				break;
			}
			SimdiDurum sd2,sd3,sd4,sd5,sd6,sd7,sd8,sd9;
			//tahta sinirlarinin disina cikmayan noktalar kuyruga ekleniyor
			if(sd.xEksen+1<tahta && sd.yEksen+4<tahta) {
				f = heu(bitX,bitY,sd.xEksen+1,sd.yEksen+4)+5+sd.yol;	//fonksiyonun yeni degeri hesaplaniyor
				sd2 = new SimdiDurum(sd.xEksen+1, sd.yEksen+4, f);
				sd2.yol = sd.yol+5;
				sd2.h = sd.h+(f-5-sd.yol)+"|";
				sd2.yollar = sd.yollar+sd2.xEksen+" "+sd2.yEksen+"|";	//gecilen yollara ekleniyor
				kuyruk.add(sd2);
			}
			if(sd.xEksen-1>-1 && sd.yEksen+4<tahta) {
				f = heu(bitX,bitY,sd.xEksen-1,sd.yEksen+4)+5+sd.yol;
				sd3 = new SimdiDurum(sd.xEksen-1, sd.yEksen+4, f);
				sd3.yol = sd.yol+5;
				sd3.h = sd.h+(f-5-sd.yol)+"|";
				sd3.yollar = sd.yollar+sd3.xEksen+" "+sd3.yEksen+"|";
				kuyruk.add(sd3);
			}
			if(sd.xEksen+1<tahta && sd.yEksen-4>-1) {
				f = heu(bitX,bitY,sd.xEksen+1,sd.yEksen-4)+5+sd.yol;
				sd4 = new SimdiDurum(sd.xEksen+1, sd.yEksen-4, f);
				sd4.yol = sd.yol+5;
				sd4.h = sd.h+(f-5-sd.yol)+"|";
				sd4.yollar = sd.yollar+sd4.xEksen+" "+sd4.yEksen+"|";
				kuyruk.add(sd4);
			}
			if(sd.xEksen-1>-1 && sd.yEksen-4>-1) {
				f = heu(bitX,bitY,sd.xEksen-1,sd.yEksen-4)+5+sd.yol;
				sd5 = new SimdiDurum(sd.xEksen-1, sd.yEksen-4, f);
				sd5.yol = sd.yol+5;
				sd5.h = sd.h+(f-5-sd.yol)+"|";
				sd5.yollar = sd.yollar+sd5.xEksen+" "+sd5.yEksen+"|";
				kuyruk.add(sd5);
			}
			if(sd.xEksen+4<tahta && sd.yEksen+1<tahta) {
				f = heu(bitX,bitY,sd.xEksen+4,sd.yEksen+1)+5+sd.yol;
				sd6 = new SimdiDurum(sd.xEksen+4, sd.yEksen+1, f);
				sd6.yol = sd.yol+5;
				sd6.h = sd.h+(f-5-sd.yol)+"|";
				sd6.yollar = sd.yollar+sd6.xEksen+" "+sd6.yEksen+"|";
				kuyruk.add(sd6);
			}
			if(sd.xEksen+4<tahta && sd.yEksen-1>-1) {
				f = heu(bitX,bitY,sd.xEksen+4,sd.yEksen-1)+5+sd.yol;
				sd7 = new SimdiDurum(sd.xEksen+4, sd.yEksen-1, f);
				sd7.yol = sd.yol+5;
				sd7.h = sd.h+(f-5-sd.yol)+"|";
				sd7.yollar = sd.yollar+sd7.xEksen+" "+sd7.yEksen+"|";
				kuyruk.add(sd7);
			}
			if(sd.xEksen-4>-1 && sd.yEksen-1>-1) {
				f = heu(bitX,bitY,sd.xEksen-4,sd.yEksen-1)+5+sd.yol;
				sd8 = new SimdiDurum(sd.xEksen-4, sd.yEksen-1, f);
				sd8.yol = sd.yol+5;
				sd8.h = sd.h+(f-5-sd.yol)+"|";
				sd8.yollar = sd.yollar+sd8.xEksen+" "+sd8.yEksen+"|";
				kuyruk.add(sd8);
			}
			if(sd.xEksen-4>-1 && sd.yEksen+1<tahta) {
				f = heu(bitX,bitY,sd.xEksen-4,sd.yEksen+1)+5+sd.yol;
				sd9 = new SimdiDurum(sd.xEksen-4, sd.yEksen+1, f);
				sd9.yol = sd.yol+5;
				sd9.h = sd.h+(f-5-sd.yol)+"|";
				sd9.yollar = sd.yollar+sd9.xEksen+" "+sd9.yEksen+"|";
				kuyruk.add(sd9);
			}
		}
		long endTime = System.nanoTime();
		sure = endTime - startTime;
		return sonuc;
	}
	
	public int heu(int bitX, int bitY, int simX, int simY) {
		//heuristic hesabi yapan fonksiyon
		int h = 0;
		h = Math.abs(bitX-simX)+Math.abs(bitY-simY);
		return h;
	}
	
	public int geu(int onX, int onY, int simX, int simY) {
		//simdiki duruma kadar gelinen yol uzunlugunu bulan fonksiyon
		int g = 0;
		g = Math.abs(simX-onX)+Math.abs(simY-onY);
		return g;
	}
	
	public int getKuyrukSize() {
		return ks;
	}
	
	public long getSure() {
		return sure/1000000;
	}

}
