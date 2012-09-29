/**
 * 
 */
package com.sitegraph.core.test;


import java.util.ArrayList;
import java.util.List;

import com.trolltech.qt.QThread;
import com.trolltech.qt.core.QEventLoop;
import com.trolltech.qt.core.QObject;
import com.trolltech.qt.core.QUrl;
import com.trolltech.qt.gui.QApplication;
import com.trolltech.qt.gui.QLabel;
import com.trolltech.qt.gui.QPainter;
import com.trolltech.qt.gui.QPrinter;
import com.trolltech.qt.gui.QWidget;
import com.trolltech.qt.webkit.QWebPage;

public class WebFrameInThread extends QLabel {
	private static String url;
	private static List<String> urls = new ArrayList<String>(); 
	private static int counter=0;
	public static String getNextUrl(){
		/*if(counter < urls.size()){
			return urls.get(counter++);
		}else
			return null;*/
		return url;
	}
	public class Main extends QObject implements Runnable{
		
		public Main(){
			
		}
		@Override
		public void run() {
			
			//while(true){
				String url =getNextUrl();
				if(url !=null){
					// TODO Auto-generated method stub
					 MyRunnable runnable = new MyRunnable(url);
				     QThread thread = new QThread(runnable);
				     runnable.moveToThread(thread);
				     thread.setDaemon(true);
				     thread.start();
				    	 
				     try {
						Thread.sleep(10000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			//}
		}
		
	} 
    public WebFrameInThread() {
    	urls.add("http://www.google.com");
		urls.add("http://www.facebook.com");
		urls.add("http://www.yahoo.com");
        /*MyRunnable runnable = new MyRunnable("http://www.google.com");
        QThread thread = new QThread(runnable);
        runnable.moveToThread(thread);
        
        MyRunnable runnable2 = new MyRunnable("http://www.yahoo.com");
        QThread thread2 = new QThread(runnable2);
        runnable2.moveToThread(thread2);
        
        thread.start();
        thread2.start();*/
    	
    	
    }
    public void doPrint(String url){
    	this.url=url;
    	Main m = new Main();
    	QThread thread = new QThread(m);
    	m.moveToThread(thread);
    	thread.start();
    	
    }

    private class MyRunnable extends QObject implements Runnable {

        private QWebPage page;
        private QEventLoop loop;
        private QUrl url;
        
        public MyRunnable(String url) {
            page = new QWebPage(this);
            this.url= new QUrl(url);
        }

        public void run() {
            try {
            	
                page.loadFinished.connect(this, "loadFinished()");
                page.loadProgress.connect(this,  "loadProgress(int)");
                page.mainFrame().load(this.url);
            } catch (Throwable e) {
                e.printStackTrace();
            }

            loop = new QEventLoop();
            loop.exec();
        }

        public void loadProgress(final int progress) {
        	System.out.println("In progress of "+url.toString());
            QApplication.invokeLater(new Runnable() {
                public void run() {
                    setText(progress + "%");
                }
            });
        }

        public void loadFinished() {
            page.setViewportSize(page.mainFrame().contentsSize());
            
	            QPrinter printer = new QPrinter();
	            printer.setOutputFileName(url.toString().replaceAll("//","-").replaceAll(":","-" )+"output.pdf");
	            printer.setOutputFormat(QPrinter.OutputFormat.PdfFormat);
	            
		            QPainter painter = new QPainter();
		            painter.begin(printer);
		            page.mainFrame().render(painter);
		            painter.end();
	            	
	            
	            loop.exit();
	
	            QApplication.invokeLater(new Runnable() {
	                public void run() {
	                    //close();
	                }
	            });            
	        
        }
    }

 
    public static void main(String args[]) throws InterruptedException {
        QApplication.initialize(args);

        WebFrameInThread widget = new WebFrameInThread();
        //widget.show();
        widget.doPrint("http://www.jroller.com");
        Thread.sleep(5000);
        widget.doPrint("http://www.namecheap.com");
        
        //Thread t =new Thread(){public void run(){
        	QApplication.exec();
        	
        //}};
        //t.start();
        //QApplication.exec();
        System.out.println("J");
    }
    

}
