/**
 *  Main class for loading HTML content of Web page, Generate images and store it on local file system.
 */
package com.sitegraph.core.pdf.impl;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;

import com.sitegraph.core.attributes.pdf.PdfAttributes;
import com.sitegraph.core.pdf.IPdfThumbnailer;
import com.sitegraph.core.util.SiteGraphConstants;
import com.trolltech.qt.QThread;
import com.trolltech.qt.core.QEventLoop;
import com.trolltech.qt.core.QObject;
import com.trolltech.qt.core.QSize;
import com.trolltech.qt.core.QUrl;
import com.trolltech.qt.core.Qt.Orientation;
import com.trolltech.qt.core.Qt.ScrollBarPolicy;
import com.trolltech.qt.gui.QPainter;
import com.trolltech.qt.gui.QPrinter;
import com.trolltech.qt.webkit.QWebPage;

public class PdfThumbnailerImpl implements IPdfThumbnailer {

	private static final Logger logger = Logger.getLogger(PdfThumbnailerImpl.class);
	protected QWebPage page;
	protected QUrl url;
	protected List<PdfAttributes> pdfAttributes=null;
	
	
	/*
	 * Default constructor forcefully added for aop scoped auto proxy 
	 */
	public PdfThumbnailerImpl(){
		super();
	}
	/**
	 * @param url URL of Web Page in String
	 */
	public PdfThumbnailerImpl(String url){
		this.url= new QUrl(url);
	}
	
	/**
	 * @param url URL of Web Page in String
	 * @param imageAttributes object of ImageAttribute Class to provide specific image related information
	 */
	public PdfThumbnailerImpl(String url,PdfAttributes pdfAttributes){
		this.url= new QUrl(url);
		this.pdfAttributes=Arrays.asList(new PdfAttributes []{pdfAttributes});
	}
	
	/**
	 * @param url URL of Web Page in String
	 * @param imageAttributes List of ImageAttribute Class to provide specific image related information
	 */
	public PdfThumbnailerImpl(String url,List<PdfAttributes> pdfAttributes){
		this.url= new QUrl(url);
		this.pdfAttributes=pdfAttributes;
	}
	
	/**
	 * Method to load html content from provided url   
	 */
	public boolean makePdfFromUrl(PdfAttributes pdfAttribute){
		this.url=new QUrl(url);
		MyRunnable runnable = new MyRunnable(pdfAttribute);
	     QThread thread = new QThread(runnable);
	     runnable.moveToThread(thread);
	     thread.start();
	     while(runnable.getFile()==null){
	    	 try {
				Thread.sleep(1000);
				System.out.println("Waiting for image to be generated . . .");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
	     }
	     System.out.println("File Generated "+runnable.getFile().getAbsolutePath());
		return true;
	}
	/**
	 * Method to load html content from provided url   
	 */
	public boolean makePdfFromHTML(PdfAttributes pdfAttribute,String handler){
		return true;
	}
	
	private class MyRunnable extends QObject implements Runnable {

        private QWebPage page;
        private QEventLoop loop;
        private QUrl url;
        private File file;
        private PdfAttributes pdfAttribute;
        
        public MyRunnable(String url) {
            page = new QWebPage(this);
            this.url= new QUrl(url);
        }
        public MyRunnable(PdfAttributes pdfAttribute) {
            page = new QWebPage(this);
            this.pdfAttribute = pdfAttribute;
            this.url= new QUrl(pdfAttribute.getUrl());
        }

        public void run() {
            try {
            	
                page.loadFinished.connect(this, "loadFinished()");
                page.loadProgress.connect(this,  "loadProgress(int)");
                page.mainFrame().load(this.url);
                System.out.println("Loaded ");
               
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
        	QSize size =new QSize(SiteGraphConstants.DEFAULT_PDF_WIDTH,SiteGraphConstants.DEFAULT_PDF_HEIGHT);
        	page.setViewportSize(size);
			page.mainFrame().setScrollBarPolicy(Orientation.Horizontal, ScrollBarPolicy.ScrollBarAlwaysOff);
			page.mainFrame().setScrollBarPolicy(Orientation.Vertical, ScrollBarPolicy.ScrollBarAlwaysOff);
			       
            QPrinter printer = new QPrinter();
            String fileName=pdfAttribute.getPdfPath();
            printer.setOutputFileName(fileName);
            printer.setOutputFormat(QPrinter.OutputFormat.PdfFormat);	            
            QPainter painter = new QPainter();
            painter.begin(printer);
            page.mainFrame().render(painter);
            painter.end();
        	System.out.println("Load Finished");
            setFile(new File(fileName));
            loop.exit();
        
        }

		public File getFile() {
			return file;
		}

		public void setFile(File file) {
			this.file = file;
		}
        
    }
}