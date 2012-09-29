/**
 *  Main class for loading HTML content of Web page, Generate images and store it on local file system.
 */
package com.sitegraph.core.pdf.impl;

import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;

import com.sitegraph.core.attributes.pdf.PdfAttributes;
import com.sitegraph.core.pdf.PdfThumbnailer;
import com.sitegraph.core.store.IStore;
import com.trolltech.qt.core.QObject;
import com.trolltech.qt.core.QUrl;
import com.trolltech.qt.gui.QApplication;
import com.trolltech.qt.network.QNetworkRequest;
import com.trolltech.qt.webkit.QWebPage;

public class PdfThumbnailerImpl extends PdfThumbnailer {

	private static final Logger logger = Logger.getLogger(PdfThumbnailerImpl.class);
	
	public IStore store;
	private Thread mainThread;
	public PdfThumbnailerImpl(Thread thread){
		this.mainThread=thread;
	}
	/*
	 * Default constructor forcefully added for aop scoped auto proxy 
	 */
	public PdfThumbnailerImpl(){
		super();
		signal.connect(this,"makePdfFromUrl()");
	}
	/**
	 * @param url URL of Web Page in String
	 */
	public PdfThumbnailerImpl(String url){
		super(null,url,Arrays.asList(new PdfAttributes[]{ new PdfAttributes()}));
	}
	
	/**
	 * @param url URL of Web Page in String
	 * @param imageAttributes object of ImageAttribute Class to provide specific image related information
	 */
	public PdfThumbnailerImpl(String url,PdfAttributes pdfAttributes){
		super(null,url,Arrays.asList(pdfAttributes));
	}
	
	/**
	 * @param url URL of Web Page in String
	 * @param imageAttributes List of ImageAttribute Class to provide specific image related information
	 */
	public PdfThumbnailerImpl(String url,List<PdfAttributes> pdfAttributes){
		super(null,url,pdfAttributes);
	}
	
	/**
	 * @param object object of QObject class 
	 * @param url URL of Web Page in String
	 * @param imageAttributes object of ImageAttribute Class to provide specific image related information
	 */
	public PdfThumbnailerImpl(QObject obj,String url,List<PdfAttributes> pdfAttributes){
		super(obj,url,pdfAttributes);
	}

	/**
	 * Method to load html content from provided url   
	 */
	public boolean makePdfFromUrl(){
		try{
		if(logger.isDebugEnabled())
			logger.debug("Connecting to url : "+this.url);
		//if(QApplication.instance() == null)
		//	QApplication.initialize(new String[] { });
		//System.out.println("QApplication : "+qApp);
		page = new QWebPage();
		
		System.out.println("Initialized qwebpage");
//		this.parent().moveToThread(QApplication.instance().thread());
//		page.mainFrame().load(new QNetworkRequest(this.url));
		page.loadStarted.connect(this, "loadStarted()");
		page.loadProgress.connect(this, "loadProgress()");
		page.loadFinished.connect(this, "loadDone()");
		//finished.connect(qApp, "quit()");
		finished.emit();
		//QApplication.exec();
    	}catch(Exception exp){
			logger.error(exp.getMessage()+ " Error While making a pdf");
			return false;
		}
		return true;
	}
	/**
	 * Method to load html content from provided url   
	 */
	public boolean makePdfFromHTML(String handler){
		try{
		if(QApplication.instance() == null)
			QApplication.initialize(new String[] { });
		System.out.println("QApplication : "+QApplication.instance());
		page = new QWebPage(null);
		page.mainFrame().load(new QNetworkRequest((QUrl)null));
		page.mainFrame().setHtml(getHtml());
		page.loadStarted.connect(this, "loadStarted()");
		page.loadProgress.connect(this, "loadProgress()");
		page.loadFinished.connect(this, "loadDone()");
		finished.connect(QApplication.instance(), "quit()");
		QApplication.exec();
    	}catch(Exception exp){
			logger.error(exp.getMessage()+ " Error While making a pdf");
			return false;
		}
		return true;
	}
	@SuppressWarnings("unused")
	private void loadStarted(){
		logger.debug("Part in Started");
	}
	@SuppressWarnings("unused")
	private void loadProgress(){
		logger.debug("Part in Progress");
	}
	/**
	 * Called internally by makeSnap() method to save loaded image(s) based on provided ImageAttribute details.  
	 */
	@SuppressWarnings("unused")
	private boolean loadDone() {
		for(PdfAttributes pdfAttribute: this.pdfAttributes){
			logger.debug("Loading for page url completed : "+ this.url);
			//String pdfPath = store.savePdf(this.url, page, pdfAttribute);
		}
	    finished.emit();
	    return true;
    }
	public IStore getStore() {
		return store;
	}
	public void setStore(IStore store) {
		this.store = store;
	}
	
}