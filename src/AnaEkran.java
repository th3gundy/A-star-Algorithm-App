import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class AnaEkran extends JFrame implements MouseListener{

	private final JPanel contentPane; // componentleri yerle�tirece�imiz Panel
	private final JButton [][] kare; // Satran� tahtas�ndaki elemanlar
	private final JLabel [] labelsX,labelsY ; // kenarlarda koordinat belirtmeye yarayan labellar
	int basX,basY,bitX,bitY; // Ayildiz s�n�f�na g�nderece�imiz de�i�kenler i�in ba�lang�� ve biti�leri belirten de�i�kenler
	int clickCounter=0; // Yol bulma butonuna basmadan �nce t�klanma say�s�n� k�s�tland�rmak i�in kullan�lan kontrol de�i�keni
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					AnaEkran frame = new AnaEkran();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public AnaEkran() {
		setTitle("Yapay Zeka Odev 1");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 639, 705);
		setLocationRelativeTo(null); // Frame a��ld�g�nda ekran� ortalamas� i�in
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		// Satran� tahtas�n� olu�turuyoruz.
		kare = new JButton[12][12];
		int satir = 67;
		int sutun = 25;
		for(int i=0;i<12;i++) {
			sutun = 40;
			for(int j=0;j<12;j++) {
				kare[i][j] = new JButton("");
				kare[i][j].addMouseListener(this); // Mouse click event'� i�in Listener ekleniyor.
				if((i+j)%2 == 0) 
					kare[i][j].setBackground(Color.black); // Bir siyah bir beyaz g�r�n�m�nde olmas� i�in renklendirme yap�l�yor
                else 
                	kare[i][j].setBackground(Color.white); // Bir siyah bir beyaz g�r�n�m�nde olmas� i�in renklendirme yap�l�yor			
            	
				kare[i][j].setEnabled(false);
				kare[i][j].setBounds(sutun, satir, 48, 48); // Butonlar belli bir d�zende yerle�tiriliyor.
				kare[i][j].setFont(new Font("Dialog", Font.PLAIN, 12));
                contentPane.add(kare[i][j]); // olu�turulan butonlar panele ekleniyor.
                sutun = sutun+48;
			}
			satir = satir+48;
		}
		
		JLabel lblHarcananSure = new JLabel("Harcanan S�re:");
		lblHarcananSure.setBounds(80, 650, 122, 15);
		contentPane.add(lblHarcananSure);
		
		final JLabel txtSure = new JLabel(""); // Harcanan s�reyi yazd�racag�m�z label
		txtSure.setBounds(196, 650, 70, 15);
		contentPane.add(txtSure);
		
		JLabel lblEnFazlaEleman = new JLabel("En Fazla Eleman Say�s�:");
		lblEnFazlaEleman.setBounds(350, 650, 174, 15);
		contentPane.add(lblEnFazlaEleman);
		
		final JLabel txtEnfEl = new JLabel(""); // Algoritma i�lenirken kuyrukta kullan�lan toplam elemanlar� g�stermek i�in
		txtEnfEl.setBounds(506, 650, 70, 15); 
		contentPane.add(txtEnfEl);
		
		JOptionPane.showMessageDialog(null, "Uygulamay� Kullanmak ��in Ba�lang�� ve Biti� Noktalar�na Mouse ile T�klay�n�z.!\n" +
				"Program� Kapatmadan Bir�ok Defa Yol Bulma Denemeleri Yapabilirsiniz.\n" +
				"Yeniden Yol Belirlemek i�in Mouse ile Tekrar T�klay�n�z.", "Kullan�m", JOptionPane.INFORMATION_MESSAGE);
		
		JButton btnBasla = new JButton("Yollar� Ara"); // Algoritmay� ba�latacak buton.
		btnBasla.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
			if (clickCounter != 0 ){ // Ba�lang�c ve biti� noktalar�n� belirtmeden butona bas�lmas�na kar��n kontrol
				for(int i=0;i<12;i++)
					for(int j=0;j<12;j++){
						if((i+j)%2 == 0) 
							kare[i][j].setBackground(Color.black); // program kapat�lmadan tekrar arama yp�lmas� durumunda tahtay� d�zelten sat�r
			            else 
			              	kare[i][j].setBackground(Color.white); // program kapat�lmadan tekrar arama yp�lmas� durumunda tahtay� d�zelten sat�r
	
	                	kare[i][j].setText("");
			         }
					String sonuc[] = new String[1000]; // bulunan yolun -return olarak d�nen de�erin- atanacag� string
					Ayildiz ay = new Ayildiz(); // Algoritmay� yazd���m�z s�n�ftan olusturulan nesne
					sonuc = ay.aYildiz(basX, basY, bitX, bitY); // Yollar� bulmaya yarayan method ve g�nderilen parametreler
					kare[basX][basY].setBackground(Color.lightGray);
					kare[basX][basY].setText("1"); // �lk t�klanan noktay� ba�lang�� duruman getiren sat�rlar
					kare[bitX][bitY].setBackground(Color.blue);
					int k = 2; // ge�ilen noktalar� say�land�rmak i�in
					String gcc[]; // ge�ilen noktalar� parse ederken kullna�lan ara de�i�ken
					for(int i=1;i<sonuc.length-1;i++) {
						// gidilen yollar� teker teker numaraland�rmak ve renklendirmek i�in yaz�lan sat�rlar.
						gcc = sonuc[i].split("[ ]");
						kare[Integer.parseInt(gcc[0])][Integer.parseInt(gcc[1])].setBackground(Color.cyan);
						kare[Integer.parseInt(gcc[0])][Integer.parseInt(gcc[1])].setText(""+k);
						k++;
					}
					// var�� noktas�n� numaraland�rmak ve renklendirmek i�in kullan�lan sat�rlar.
					gcc = sonuc[sonuc.length-1].split("[ ]");
					kare[Integer.parseInt(gcc[0])][Integer.parseInt(gcc[1])].setBackground(Color.blue);
					kare[Integer.parseInt(gcc[0])][Integer.parseInt(gcc[1])].setText(""+k);
					int ks;
					long sure;
					ks = ay.getKuyrukSize(); // Ayildiz s�n�f�nda yolu bulmak i�in kullan�lan kuyrugun toplam eleman say�s�
					sure = ay.getSure(); // Algoritman�n i�leyi�i s�ras�nda ge�en s�renin nanosaniye cinsinden de�eri
					txtEnfEl.setText(""+ks);
					txtSure.setText(sure+" ns");
					clickCounter = 0; // Yol bulma i�lemi yap�ld�ktan sonra t�klamalar i�in ara de�i�ken s�f�rlan�yor.
			} else if (clickCounter == 0)
				JOptionPane.showMessageDialog(null, "L�tfen Ba�lang�� ve Biti� Noktalar�n� Se�in.", 
						"Hata", JOptionPane.INFORMATION_MESSAGE);
			}
		});
		btnBasla.setBounds(199, 12, 250, 25);
		contentPane.add(btnBasla);
		
		//  Tahtan�n sol taraf�nda yer alan labellar
		labelsX = new JLabel[12];
		int lenght = 74;
		for (int i=0; i<12; i++){
			labelsX[i] = new JLabel("x" + i);
			labelsX[i].setBounds(12, lenght, 25, 15);
			contentPane.add(labelsX[i]);
			lenght = lenght + 49;
		}
		
		//  Tahtan�n �st taraf�nda yer alan labellar
		labelsY = new JLabel[12];
		lenght = 55;
		for (int i=0; i<12; i++){
			labelsY[i] = new JLabel("y" + i);
			labelsY[i].setBounds(lenght, 44, 23, 15);
			contentPane.add(labelsY[i]);
			lenght = lenght + 48;
		}
	}

	@Override
	public void mouseClicked(MouseEvent evt) {
		clickCounter++; // yol aramadan �nce max 2 t�klamaya izin vermek i�in de�er artt�r�l�yor
		for( int i=0;i<12;i++ ) 
			for( int j=0;j<12;j++ )
				if ( kare[i][j] == evt.getComponent() &&  clickCounter == 1){
					// Ba�lang�� i�in hangi butona bas�ld���n� bulmak i�in yap�lan kontrol
					// Butona bas�ld�ktan sonra bas�lan buton , ba�lang�� noktas� ve de�erler atan�yor
					basX = i;
					basY = j;
					kare[basX][basY].setBackground(Color.lightGray);
					kare[basX][basY].setText("1");
					
				} else if( kare[i][j] == evt.getComponent() &&  clickCounter == 2){
						// Var�� noktas� i�in hangi butona bas�ld���n� bulmak i�in yap�lan kontrol
						// Butona bas�ld�ktan sonra bas�lan buton , biti� noktas� ve de�erler atan�yor
						bitX = i;
						bitY = j;
						kare[bitX][bitY].setBackground(Color.blue);
						kare[bitX][bitY].setText("Son"); 
				  }
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {	}

	@Override
	public void mouseExited(MouseEvent arg0) { }

	@Override
	public void mousePressed(MouseEvent arg0) {	}

	@Override
	public void mouseReleased(MouseEvent arg0) { }
}
