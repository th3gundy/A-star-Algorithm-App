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

	private final JPanel contentPane; // componentleri yerleþtireceðimiz Panel
	private final JButton [][] kare; // Satranç tahtasýndaki elemanlar
	private final JLabel [] labelsX,labelsY ; // kenarlarda koordinat belirtmeye yarayan labellar
	int basX,basY,bitX,bitY; // Ayildiz sýnýfýna göndereceðimiz deðiþkenler için baþlangýç ve bitiþleri belirten deðiþkenler
	int clickCounter=0; // Yol bulma butonuna basmadan önce týklanma sayýsýný kýsýtlandýrmak için kullanýlan kontrol deðiþkeni
	
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
		setLocationRelativeTo(null); // Frame açýldýgýnda ekraný ortalamasý için
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		// Satranç tahtasýný oluþturuyoruz.
		kare = new JButton[12][12];
		int satir = 67;
		int sutun = 25;
		for(int i=0;i<12;i++) {
			sutun = 40;
			for(int j=0;j<12;j++) {
				kare[i][j] = new JButton("");
				kare[i][j].addMouseListener(this); // Mouse click event'ý için Listener ekleniyor.
				if((i+j)%2 == 0) 
					kare[i][j].setBackground(Color.black); // Bir siyah bir beyaz görünümünde olmasý için renklendirme yapýlýyor
                else 
                	kare[i][j].setBackground(Color.white); // Bir siyah bir beyaz görünümünde olmasý için renklendirme yapýlýyor			
            	
				kare[i][j].setEnabled(false);
				kare[i][j].setBounds(sutun, satir, 48, 48); // Butonlar belli bir düzende yerleþtiriliyor.
				kare[i][j].setFont(new Font("Dialog", Font.PLAIN, 12));
                contentPane.add(kare[i][j]); // oluþturulan butonlar panele ekleniyor.
                sutun = sutun+48;
			}
			satir = satir+48;
		}
		
		JLabel lblHarcananSure = new JLabel("Harcanan Süre:");
		lblHarcananSure.setBounds(80, 650, 122, 15);
		contentPane.add(lblHarcananSure);
		
		final JLabel txtSure = new JLabel(""); // Harcanan süreyi yazdýracagýmýz label
		txtSure.setBounds(196, 650, 70, 15);
		contentPane.add(txtSure);
		
		JLabel lblEnFazlaEleman = new JLabel("En Fazla Eleman Sayýsý:");
		lblEnFazlaEleman.setBounds(350, 650, 174, 15);
		contentPane.add(lblEnFazlaEleman);
		
		final JLabel txtEnfEl = new JLabel(""); // Algoritma iþlenirken kuyrukta kullanýlan toplam elemanlarý göstermek için
		txtEnfEl.setBounds(506, 650, 70, 15); 
		contentPane.add(txtEnfEl);
		
		JOptionPane.showMessageDialog(null, "Uygulamayý Kullanmak Ýçin Baþlangýç ve Bitiþ Noktalarýna Mouse ile Týklayýnýz.!\n" +
				"Programý Kapatmadan Birçok Defa Yol Bulma Denemeleri Yapabilirsiniz.\n" +
				"Yeniden Yol Belirlemek için Mouse ile Tekrar Týklayýnýz.", "Kullaným", JOptionPane.INFORMATION_MESSAGE);
		
		JButton btnBasla = new JButton("Yollarý Ara"); // Algoritmayý baþlatacak buton.
		btnBasla.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
			if (clickCounter != 0 ){ // Baþlangýc ve bitiþ noktalarýný belirtmeden butona basýlmasýna karþýn kontrol
				for(int i=0;i<12;i++)
					for(int j=0;j<12;j++){
						if((i+j)%2 == 0) 
							kare[i][j].setBackground(Color.black); // program kapatýlmadan tekrar arama ypýlmasý durumunda tahtayý düzelten satýr
			            else 
			              	kare[i][j].setBackground(Color.white); // program kapatýlmadan tekrar arama ypýlmasý durumunda tahtayý düzelten satýr
	
	                	kare[i][j].setText("");
			         }
					String sonuc[] = new String[1000]; // bulunan yolun -return olarak dönen deðerin- atanacagý string
					Ayildiz ay = new Ayildiz(); // Algoritmayý yazdýðýmýz sýnýftan olusturulan nesne
					sonuc = ay.aYildiz(basX, basY, bitX, bitY); // Yollarý bulmaya yarayan method ve gönderilen parametreler
					kare[basX][basY].setBackground(Color.lightGray);
					kare[basX][basY].setText("1"); // Ýlk týklanan noktayý baþlangýç duruman getiren satýrlar
					kare[bitX][bitY].setBackground(Color.blue);
					int k = 2; // geçilen noktalarý sayýlandýrmak için
					String gcc[]; // geçilen noktalarý parse ederken kullnaýlan ara deðiþken
					for(int i=1;i<sonuc.length-1;i++) {
						// gidilen yollarý teker teker numaralandýrmak ve renklendirmek için yazýlan satýrlar.
						gcc = sonuc[i].split("[ ]");
						kare[Integer.parseInt(gcc[0])][Integer.parseInt(gcc[1])].setBackground(Color.cyan);
						kare[Integer.parseInt(gcc[0])][Integer.parseInt(gcc[1])].setText(""+k);
						k++;
					}
					// varýþ noktasýný numaralandýrmak ve renklendirmek için kullanýlan satýrlar.
					gcc = sonuc[sonuc.length-1].split("[ ]");
					kare[Integer.parseInt(gcc[0])][Integer.parseInt(gcc[1])].setBackground(Color.blue);
					kare[Integer.parseInt(gcc[0])][Integer.parseInt(gcc[1])].setText(""+k);
					int ks;
					long sure;
					ks = ay.getKuyrukSize(); // Ayildiz sýnýfýnda yolu bulmak için kullanýlan kuyrugun toplam eleman sayýsý
					sure = ay.getSure(); // Algoritmanýn iþleyiþi sýrasýnda geçen sürenin nanosaniye cinsinden deðeri
					txtEnfEl.setText(""+ks);
					txtSure.setText(sure+" ns");
					clickCounter = 0; // Yol bulma iþlemi yapýldýktan sonra týklamalar için ara deðiþken sýfýrlanýyor.
			} else if (clickCounter == 0)
				JOptionPane.showMessageDialog(null, "Lütfen Baþlangýç ve Bitiþ Noktalarýný Seçin.", 
						"Hata", JOptionPane.INFORMATION_MESSAGE);
			}
		});
		btnBasla.setBounds(199, 12, 250, 25);
		contentPane.add(btnBasla);
		
		//  Tahtanýn sol tarafýnda yer alan labellar
		labelsX = new JLabel[12];
		int lenght = 74;
		for (int i=0; i<12; i++){
			labelsX[i] = new JLabel("x" + i);
			labelsX[i].setBounds(12, lenght, 25, 15);
			contentPane.add(labelsX[i]);
			lenght = lenght + 49;
		}
		
		//  Tahtanýn üst tarafýnda yer alan labellar
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
		clickCounter++; // yol aramadan önce max 2 týklamaya izin vermek için deðer arttýrýlýyor
		for( int i=0;i<12;i++ ) 
			for( int j=0;j<12;j++ )
				if ( kare[i][j] == evt.getComponent() &&  clickCounter == 1){
					// Baþlangýç için hangi butona basýldýðýný bulmak için yapýlan kontrol
					// Butona basýldýktan sonra basýlan buton , baþlangýç noktasý ve deðerler atanýyor
					basX = i;
					basY = j;
					kare[basX][basY].setBackground(Color.lightGray);
					kare[basX][basY].setText("1");
					
				} else if( kare[i][j] == evt.getComponent() &&  clickCounter == 2){
						// Varýþ noktasý için hangi butona basýldýðýný bulmak için yapýlan kontrol
						// Butona basýldýktan sonra basýlan buton , bitiþ noktasý ve deðerler atanýyor
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
