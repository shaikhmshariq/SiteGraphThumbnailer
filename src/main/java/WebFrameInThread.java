
import com.trolltech.qt.*;
import com.trolltech.qt.core.*;
import com.trolltech.qt.gui.*;
import com.trolltech.qt.webkit.*;

public class WebFrameInThread extends QLabel {

    public WebFrameInThread() {
        MyRunnable runnable = new MyRunnable("http://www.facebook.com");
        MyRunnable runnable2 = new MyRunnable("http://www.twitter.com");
        QThread thread = new QThread(runnable);
        runnable.moveToThread(thread);
        QThread th = new QThread(runnable2);
        runnable2.moveToThread(th);
        thread.start();
        //th.start();
        
    }

    private class MyRunnable extends QObject implements Runnable {

        private QWebPage page;
        private QEventLoop loop;
        private String url;
        public MyRunnable(String url) {
            page = new QWebPage(this);
            this.url = url; 
        }

        public void run() {
            try {
                page.loadFinished.connect(this, "loadFinished()");
                page.loadProgress.connect(this,  "loadProgress(int)");
                page.mainFrame().load(new QUrl(url));
            } catch (Throwable e) {
                e.printStackTrace();
            }

            loop = new QEventLoop();
            loop.exec();
        }

        public void loadProgress(final int progress) {
            QApplication.invokeLater(new Runnable() {
            	
                public void run() {
                	System.out.println("load progress");
                    setText(progress + "%");
                }
            });
        }

        public void loadFinished() {
            page.setViewportSize(page.mainFrame().contentsSize());

            QPrinter printer = new QPrinter();
            printer.setOutputFileName("output.pdf");
            printer.setOutputFormat(QPrinter.OutputFormat.PdfFormat);

            QPainter painter = new QPainter();
            painter.begin(printer);
            page.mainFrame().render(painter);
            painter.end();

            loop.exit();

            QApplication.invokeLater(new Runnable() {
                public void run() {
                    close();
                }
            });            
        }
    }


    public static void main(String args[]) {
        QApplication.initialize(args);
        
        QWidget widget = new WebFrameInThread();
        widget.show();

        QApplication.exec();
    }

}
