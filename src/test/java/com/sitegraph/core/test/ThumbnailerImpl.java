/**
 * 
 */
package com.sitegraph.core.test;

import java.io.File;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Date;

import com.trolltech.qt.core.QEventLoop;
import com.trolltech.qt.core.QObject;
import com.trolltech.qt.core.QUrl;
import com.trolltech.qt.gui.QApplication;
import com.trolltech.qt.gui.QPainter;
import com.trolltech.qt.gui.QPrinter;
import com.trolltech.qt.webkit.QWebPage;

/**
 * @author Mohammed
 *
 */
public class ThumbnailerImpl implements Thumbnailer {

	/* (non-Javadoc)
	 * @see com.sitegraph.core.test.Thumbnailer#createImage(java.lang.String)
	 */
	
    private class MyRunnable extends QObject implements Runnable {

        private QWebPage page;
        private QEventLoop loop;
        private QUrl url;
        private File file;
        private boolean flag;
        public MyRunnable(String url) {
            page = new QWebPage(this);
            this.url= new QUrl(url);
        }

        public void run() {
            try {
            	
                page.loadFinished.connect(this, "loadFinished()");
                page.loadProgress.connect(this,  "loadProgress(int)");
                page.mainFrame().load(this.url);
                System.out.println("Loaded ");
                while(!flag){
                	QApplication.processEvents();
                	Thread.sleep(1000);
                }
            } catch (Throwable e) {
                e.printStackTrace();
            }

            loop = new QEventLoop();
            loop.exec();
        }

        public void loadProgress(final int progress) {
        	System.out.println("In progress of "+url.toString());
            
        }

        public void loadFinished() {
            page.setViewportSize(page.mainFrame().contentsSize());
            
	            QPrinter printer = new QPrinter();
	            String fileName=url.toString().replaceAll("//","-").replaceAll(":","-" )+new Date().getTime()+".pdf";
	            printer.setOutputFileName(fileName);
	            printer.setOutputFormat(QPrinter.OutputFormat.PdfFormat);
	            
	            QPainter painter = new QPainter();
	            painter.begin(printer);
	            page.mainFrame().render(painter);
	            painter.end();
            	System.out.println("Load Finished");
	            setFile(new File(fileName));
	            loop.exit();
	            flag=true;
	            QApplication.invokeLater(new Runnable() {
	                public void run() {
	                    //close();
	                }
	            });            
	        
        }

		public File getFile() {
			return file;
		}

		public void setFile(File file) {
			this.file = file;
		}
        
    }


    public static void main(String[] args) {
        /*if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }
        try {
            String name = "Compute";
            Compute engine = new ComputeEngine();
            Compute stub = (Compute) UnicastRemoteObject.exportObject(engine, 0);
            Registry registry = LocateRegistry.getRegistry();
            registry.rebind(name, stub);
            System.out.println("ComputeEngine bound");
        } catch (Exception e) {
            System.err.println("ComputeEngine exception:");
            e.printStackTrace();
        }*/
    }


	@Override
	public File doPrint(String url) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

}
