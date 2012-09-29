import com.trolltech.qt.core.QUrl;
import com.trolltech.qt.core.Qt;
import com.trolltech.qt.gui.QApplication;
import com.trolltech.qt.gui.QImage;
import com.trolltech.qt.gui.QPainter;
import com.trolltech.qt.webkit.QWebPage;

/**
 * 
 */

/**
 * @author Mohammed
 *
 */
public class Thumbnailer extends QWebPage {
      Thumbnailer()
      {
    	  System.out.println("Constructor ");
            loadStarted.connect(this, "loadStarted()");
            loadProgress.connect(this,"loadProgress()");
            loadFinished.connect(this, "loadFinished(Boolean)");
      }
      
      String load(String pText)
      {
            mainFrame().load(new QUrl(pText));
            return pText + " Completed";
      }
      
        void loadStarted()
        {
            System.out.println("START");
        }
        void loadProgress(){
        	System.out.println("In Progress "+this.toString());
        }
        void loadFinished(Boolean pState)
        {
        	System.out.println("finished loading . . .");
         setViewportSize(mainFrame().contentsSize());
            
           QImage image = new QImage(viewportSize(),QImage.Format.Format_ARGB32);
           QPainter painter = new QPainter(image);
           mainFrame().render(painter);
           painter.end();
 
           // we assume that the page width is smaller than the page height
           QImage thumbnail = image.scaledToWidth(218,Qt.TransformationMode.SmoothTransformation);
           thumbnail.save("C:\\temp\\thumbnail"+this.toString()+".png");
            
           System.out.println ("rendering finished");
        }
    
        public static void main(String s[]){
        	QApplication.initialize(new String[]{});
        	/*Thread t = new Thread(){
        	public void run(){
        		try {
        			System.out.println("Waiting for next 5 seconds . . .");
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        		Initiator i = new Initiator();
        		i.initiate("http://www.google.com");
        		
        	}};
        	t.start();*/
        	Thumbnailer t = new Thumbnailer();
        	t.load("http://www.google.com");
        	System.out.println("Again");
        	QApplication.exec();
        	
        	
        	//th.load("http://www.google.com");
        	/*t.start();
        	t.start();*/
        	
        	
        }
    }
class Initiator{
	 public Initiator(){}
	 public void initiate(String url){
		 Thumbnailer t = new Thumbnailer();
		 t.load(url);
	 }
}

