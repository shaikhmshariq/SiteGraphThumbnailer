import com.trolltech.qt.QThread;
import com.trolltech.qt.core.QObject;
import com.trolltech.qt.core.QRect;
import com.trolltech.qt.core.QSize;
import com.trolltech.qt.core.QUrl;
import com.trolltech.qt.gui.QLineEdit;
import com.trolltech.qt.gui.QMainWindow;
import com.trolltech.qt.gui.QMenuBar;
import com.trolltech.qt.gui.QPushButton;
import com.trolltech.qt.gui.QStatusBar;
import com.trolltech.qt.gui.QWidget;
import com.trolltech.qt.webkit.QWebPage;
import com.trolltech.qt.webkit.QWebView;

public class Ui_Main extends QObject implements com.trolltech.qt.QUiForm<QMainWindow> 
{
    public QWidget centralwidget;
    public QLineEdit lineEdit;
    public QWebView webView;
    public QPushButton back;
    public QPushButton forward;
    public QPushButton thumbButton;
    public QMenuBar menubar;
    public QStatusBar statusbar;
    public Signal1<String> signal = new Signal1<String>();
    
    public Ui_Main() { super(); }
 
    public void setupUi(QMainWindow Main)
    {
        Main.setObjectName("Main");
        Main.resize(new QSize(1024, 800).expandedTo(Main.minimumSizeHint()));
        centralwidget = new QWidget(Main);
        centralwidget.setObjectName("centralwidget");
        lineEdit = new QLineEdit(centralwidget);
        lineEdit.setObjectName("lineEdit");
        lineEdit.setGeometry(new QRect(100, 10, 631, 20));
        webView = new QWebView(centralwidget);
        webView.setObjectName("webView");
        webView.setGeometry(new QRect(10, 40, 800, 600));
        webView.setUrl(new QUrl("http://www.facebook.com/"));
        back = new QPushButton(centralwidget);
        back.setObjectName("back");
        back.setGeometry(new QRect(10, 10, 21, 23));
        forward = new QPushButton(centralwidget);
        forward.setObjectName("forward");
        forward.setGeometry(new QRect(40, 10, 21, 23));
        thumbButton = new QPushButton(centralwidget);
        thumbButton.setObjectName("thumbButton");
        thumbButton.setGeometry(new QRect(750, 10, 41, 23));
        Main.setCentralWidget(centralwidget);
        menubar = new QMenuBar(Main);
        menubar.setObjectName("menubar");
        menubar.setGeometry(new QRect(0, 0, 800, 21));
        Main.setMenuBar(menubar);
        statusbar = new QStatusBar(Main);
        statusbar.setObjectName("statusbar");
        Main.setStatusBar(statusbar);
        retranslateUi(Main);
        back.clicked.connect(webView, "back()");
        forward.clicked.connect(webView, "forward()");
        thumbButton.clicked.connect(this, "thumb()");
        signal.connect(this,"doSnap(String)");
        Main.connectSlotsByName();
    } // setupUi
 
    void retranslateUi(QMainWindow Main)
    {
       
 Main.setWindowTitle(com.trolltech.qt.core.QCoreApplication.translate("Main", 
"MainWindow", null));
        back.setText(com.trolltech.qt.core.QCoreApplication.translate("Main", 
"<-", null));
        forward.setText(com.trolltech.qt.core.QCoreApplication.translate("Main", 
"->", null));
       
 thumbButton.setText(com.trolltech.qt.core.QCoreApplication.translate("Main", 
"Thumb", null));
    } // retranslateUi
 
    
    void thumb() {
          /*ThumbnailerThread t = new ThumbnailerThread (lineEdit.text());
          QThread thread = new QThread (t);
          //t.moveToThread(thread);
          thread.start();*/
          Thumbnailer t = new  Thumbnailer();
          t.load(lineEdit.text());
 
    }
    
    String doSnap(String ptext) {
        /*ThumbnailerThread t = new ThumbnailerThread (lineEdit.text());
        QThread thread = new QThread (t);
        //t.moveToThread(thread);
        thread.start();*/
        Thumbnailer t = new  Thumbnailer();
        t.load(ptext);
        return "Completed "+ptext;

  }
}