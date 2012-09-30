/**
 * 
 */
package com.sitegraph.core.test;


import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.trolltech.qt.QThread;
import com.trolltech.qt.core.QEventLoop;
import com.trolltech.qt.core.QObject;
import com.trolltech.qt.core.QUrl;
import com.trolltech.qt.gui.QApplication;
import com.trolltech.qt.gui.QLabel;
import com.trolltech.qt.gui.QPainter;
import com.trolltech.qt.gui.QPrinter;
import com.trolltech.qt.webkit.QWebPage;

public class WebFrameInThread2 extends QLabel implements Thumbnailer{
	private static String url;
	private static List<String> urls = new ArrayList<String>(); 
	private static int counter=0;
	private ServerSocket socket;
	public static String getNextUrl(){
		/*if(counter < urls.size()){
			return urls.get(counter++);
		}else
			return null;*/
		return url;
	}
	/*public class Main extends QObject implements Runnable{
		
		public Main(){
			
		}
		@Override
		public void run() {
				String url =getNextUrl();
				if(url !=null){
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
		}
		
	} */
    public WebFrameInThread2() {
    	super();
    	/*Thread t = new Thread(){
    		public void run(){
    	
    			try {
    				socket = new ServerSocket(10000);
    				System.out.println("Listening on port "+socket.getLocalPort());
    				while(true){
						Socket skt =socket.accept();
						DataInputStream input = new DataInputStream(skt.getInputStream());
						String url = input.readLine();
						input.close();
						 MyRunnable runnable = new MyRunnable(url);
					     QThread thread = new QThread(runnable);
					     runnable.moveToThread(thread);
					     //thread.setDaemon(true);
					     thread.start();
					     //while(runnable.getFile()==null){
					    //	 Thread.sleep(2000);
					     //}
					     //System.out.println(runnable.getFile().getAbsolutePath());
    				}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    		}		
    		
    	};
    	t.start();
    	*/
    }
    public File doPrint(String url) throws RemoteException{
    	 MyRunnable runnable = new MyRunnable(url);
	     QThread thread = new QThread(runnable);
	     runnable.moveToThread(thread);
	     thread.start();
	     while(runnable.getFile()==null){
	    	 try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	     }
	     System.out.println("File Generated "+runnable.getFile().getAbsolutePath());
	     return runnable.getFile();
    	
    }

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
                /*while(!flag){
                	QApplication.processEvents();
                	Thread.sleep(1000);
                }*/
            } catch (Throwable e) {
                e.printStackTrace();
            }

            loop = new QEventLoop();
            loop.exec();
        }

        public void loadProgress(final int progress) {
        	System.out.println("In progress of "+url.toString()+" % Completed "+progress);
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

 
    public static void main(String args[]) throws InterruptedException {
       /* QApplication.initialize(args);
        QApplication.aboutQtJambi();
        WebFrameInThread2 widget = new WebFrameInThread2();
        widget.show();
        widget.doPrint("http://www.jroller.com");
        Thread.sleep(5000);
        widget.doPrint("http://www.namecheap.com");
        
        //Thread t =new Thread(){public void run(){
        	//QApplication.exec();
        QApplication.processEvents();	
        //}};
        //t.start();
        //QApplication.exec();
        System.out.println("J");*/
    	QApplication.initialize(args);
    	//System.setProperty("java.security.policy","file:///D://t.policy");
    	if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }
        try {
            String name = "Compute";
            Thumbnailer engine = new WebFrameInThread2();
            Thumbnailer stub = (Thumbnailer) UnicastRemoteObject.exportObject(engine, 0);
            Registry registry = LocateRegistry.getRegistry();
            registry.bind(name, stub);
            System.out.println("ComputeEngine bound");
        } catch (Exception e) {
            System.err.println("ComputeEngine exception:");
            e.printStackTrace();
        }
        System.out.println("Initializing Qapplication engine ");
        QApplication.exec();
    }
    

}
