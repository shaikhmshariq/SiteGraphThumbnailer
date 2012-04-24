/**
 *  Main class for loading HTML content of Web page, Generate images and store it on local file system.
 */
package com.sitegraph.core.fs;

import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;

import com.sitegraph.core.SiteGraphThumbnailer;
import com.sitegraph.core.attributes.ImageAttributes;
import com.sitegraph.core.attributes.PNGImageAttributes;
import com.sitegraph.core.util.Constants;
import com.sitegraph.core.util.WebAppUtils;
import com.trolltech.qt.core.QObject;
import com.trolltech.qt.core.QSize;
import com.trolltech.qt.core.Qt.Orientation;
import com.trolltech.qt.core.Qt.ScrollBarPolicy;
import com.trolltech.qt.gui.QApplication;
import com.trolltech.qt.gui.QPainter;
import com.trolltech.qt.gui.QPrinter;
import com.trolltech.qt.network.QNetworkRequest;
import com.trolltech.qt.webkit.QWebPage;

public class PdfMaker extends SiteGraphThumbnailer {

	private static final Logger logger = Logger.getLogger(PdfMaker.class);
	
	/*
	 * Default constructor forcefully added for aop scoped auto proxy 
	 */
	public PdfMaker(){
		super();
	}
	/**
	 * @param url URL of Web Page in String
	 */
	public PdfMaker(String url){
		super(null,url,Arrays.asList(new ImageAttributes[]{ new PNGImageAttributes()}));
	}
	
	/**
	 * @param url URL of Web Page in String
	 * @param imageAttributes object of ImageAttribute Class to provide specific image related information
	 */
	public PdfMaker(String url,ImageAttributes imageAttribute){
		super(null,url,Arrays.asList(imageAttribute));
	}
	
	/**
	 * @param url URL of Web Page in String
	 * @param imageAttributes List of ImageAttribute Class to provide specific image related information
	 */
	public PdfMaker(String url,List<ImageAttributes> imageAttributes){
		super(null,url,imageAttributes);
	}
	
	/**
	 * @param object object of QObject class 
	 * @param url URL of Web Page in String
	 * @param imageAttributes object of ImageAttribute Class to provide specific image related information
	 */
	public PdfMaker(QObject obj,String url,List<ImageAttributes> imageAttributes){
		super(obj,url,imageAttributes);
	}

	/**
	 * Method to load html content from provided url   
	 */
	public boolean makeSnap(){
		try{
		if(logger.isDebugEnabled())
			logger.debug("Connecting to url : "+this.url);
		if(QApplication.instance() == null)
			QApplication.initialize(new String[] { });
		page = new QWebPage(null);
		page.mainFrame().load(new QNetworkRequest(this.url));
		logger.debug("Page Loaded");
		page.loadStarted.connect(this, "loadStarted()");
		page.loadProgress.connect(this, "loadProgress()");
		page.loadFinished.connect(this, "loadDone()");
		logger.debug("Load Finished");
		finished.connect(QApplication.instance(), "quit()");
		logger.debug("pdf created");
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
		logger.debug("Loading for page url : "+ this.url);
		if(QApplication.instance() == null)
			QApplication.initialize(new String[] { });
		for(ImageAttributes imageAttribute: this.imageAttributes){
			logger.debug("Loading for page url : "+ this.url);
			//page.setViewportSize(imageAttribute.getImageSize());
			page.mainFrame().setScrollBarPolicy(Orientation.Horizontal, ScrollBarPolicy.ScrollBarAlwaysOff);
			page.mainFrame().setScrollBarPolicy(Orientation.Vertical, ScrollBarPolicy.ScrollBarAlwaysOff);
			//page.setViewportSize(new QSize(Constants.DEFAULT_IMAGE_WIDTH, Constants.DEFAULT_IMAGE_HEIGHT));
		    //QImage image = new QImage(page.viewportSize(), QImage.Format.Format_ARGB32);
		    //image.fill(QColor.white.rgb());
			QPrinter printer = new QPrinter();
			printer.setPageSize(QPrinter.PageSize.A4);
			printer.setResolution(200);
			printer.setOutputFormat(QPrinter.OutputFormat.PdfFormat);
			String pdfFileName= WebAppUtils.resolveImageStoragePath(imageAttribute, this.url.toString());
			printer.setOutputFileName(pdfFileName);
		    QPainter painter = new QPainter(printer);
		    page.mainFrame().render(painter);
		    painter.end();
		    
		}
	    finished.emit();
	    return true;
    }
	/*
	public static void main(String[] args) {
		  new ImageMaker("http://www.google.com").makeSnap();
		  new ImageMaker("http://www.google.com",new PNGImageAttributes()).makeSnap();
		  new ImageMaker("http://www.google.com",new PNGImageAttributes("C:\\temp\\new")).makeSnap();
		  List<ImageAttributes> imageAttributes = new ArrayList<ImageAttributes>();
		  imageAttributes.add(new JPEGImageAttributes("C:\\temp\\JPEGImage"));
		  imageAttributes.add(new JPEGImageAttributes(new QSize(800,600),"C:\\temp\\JPEGImage_800_600"));
		  imageAttributes.add(new PNGImageAttributes(new QSize(800,600),"C:\\temp\\PNGImage_800_600"));
		  new ImageMaker("http://www.facebook.com",imageAttributes).makeSnap();
	}
	*/
}