import com.trolltech.qt.gui.QApplication;
import com.trolltech.qt.gui.QMainWindow;
import com.trolltech.qt.gui.QWidget;

public class Main extends QMainWindow {
 
    Ui_Main ui = new Ui_Main();
 
    public static void main(String[] args) {
          //QNetworkProxy proxy = new QNetworkProxy(ProxyType.HttpProxy, hostname, 8080);
          //QNetworkProxy.setApplicationProxy(proxy);
          
        QApplication.initialize(args);
 
        Main testMain = new Main();
        testMain.show();
 
        QApplication.exec();
        System.out.println("Running thread");
    }
 
    public Main() {
        ui.setupUi(this);
    }
    public String doSnap(String ptext){
    	ui.signal.emit(ptext);
    	return "Completed";
    	}
    public Main(QWidget parent) {
        super(parent);
        ui.setupUi(this);
    }
}