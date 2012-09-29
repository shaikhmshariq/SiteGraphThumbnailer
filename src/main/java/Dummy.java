import com.sitegraph.core.pdf.PdfThumbnailer;
import com.sitegraph.core.pdf.impl.PdfThumbnailerImpl;
import com.trolltech.qt.gui.QApplication;


public class Dummy extends Thread{
	
	Main testMain;
	private static I i;
	
	/*public void doItAgain(){
		System.out.println("Initilizing Instance dummy");
		thread = new Thread(){
			public void run(){
				QApplication.initialize(new String[]{});
				QApplication.instance().exec();
		}};
		thread.start();
	}*/
	/*public void run(){
		System.setProperty("com.trolltech.qt.thread-check", "no");
		QApplication.initialize(new String[]{});
		Screenshot screenshot = new Screenshot(null);
        screenshot.show();
		System.out.println("Initilizing Instance dummy");
		//QApplication.pr
		QApplication.exec();
	}*/
	
	public void run(){
		QApplication.initialize(new String[]{});
		 
        testMain = new Main();
        testMain.show();
 
        QApplication.exec();
		
	}
	private void doSomething(){
		System.out.println("Initilizing Instance");
		//if(QApplication.instance()==null)
		//	QApplication.initialize(new String[]{});
		System.out.println("Initilizing Instance done");
		//QApplication.exec();
	}
	public static QApplication doIt(){
		System.setProperty("com.trolltech.qt.thread-check", "no");
		QApplication.initialize(new String[]{});
		return QApplication.instance();
		
	}
	public PdfThumbnailer getPdfThumbnailer(){
		
		System.out.println(Thread.currentThread());
		return new PdfThumbnailerImpl(Thread.currentThread());
		//return Dummy.i.getPdfThumbnailer();
		
	}
	public static void main(String s[]) throws InterruptedException{
		
		Dummy d = new Dummy();
		d.start();
		Thread.sleep(10000);
		System.out.println("Started . . .");
		//d.testMain.ui.thumbButton.click();
		d.testMain.doSnap("http://www.google.com");
		Thread.sleep(5000);
		d.testMain.doSnap("http://www.twitter.com");
		d.testMain.doSnap("http://www.facebook.com");
	}
	
} 
