/**
 *  Generic class for loading HTML content of Web page and Generate images.
 */
package com.sitegraph.core;

import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;

import com.sitegraph.core.attributes.ImageAttributes;
import com.sitegraph.core.attributes.PNGImageAttributes;
import com.trolltech.qt.core.QObject;
import com.trolltech.qt.core.QUrl;
import com.trolltech.qt.webkit.QWebPage;


public abstract class SiteGraphThumbnailer extends QObject{

	private static final Logger logger = Logger.getLogger(SiteGraphThumbnailer.class);
	/**
	 * @param args
	 */
	protected QWebPage page;
	protected QUrl url;
	protected List<ImageAttributes> imageAttributes=null;
	public Signal0 finished = new Signal0();
	
	/**
	 * @param url URL of Web Page in String
	 */
	public SiteGraphThumbnailer(String url){
		this(null,url,Arrays.asList(new ImageAttributes[]{ new PNGImageAttributes()}));
	}
	
	/**
	 * @param url URL of Web Page in String
	 * @param imageAttributes object of ImageAttribute Class to provide specific image related information
	 */
	public SiteGraphThumbnailer(String url,ImageAttributes imageAttribute){
		this(null,url,Arrays.asList(imageAttribute));
	}
	
	/**
	 * @param url URL of Web Page in String
	 * @param imageAttributes List of ImageAttribute Class to provide specific image related information
	 */
	public SiteGraphThumbnailer(String url,List<ImageAttributes> imageAttributes){
		this(null,url,imageAttributes);
	}
	
	/**
	 * @param object object of QObject class 
	 * @param url URL of Web Page in String
	 * @param imageAttributes object of ImageAttribute Class to provide specific image related information
	 */
	public SiteGraphThumbnailer(QObject obj,String url,List<ImageAttributes> imageAttributes){
		super(obj);
		this.imageAttributes=imageAttributes;
		this.url=new QUrl(url);
	}
	
	/**
	 * @return Returns QUrl Object of Url associated with SiteGraphThumbnailer Class  
	 */
	public QUrl getUrl() {
		return url;
	}
	
	/**
	 * @param QUrl object
	 */
	public void setUrl(QUrl url) {
		this.url = url;
	}
	
	/**
	 * @return Returns QUrl Object of Url associated with SiteGraphThumbnailer Class  
	 */
	public List<ImageAttributes> getImageAttributes() {
		return imageAttributes;
	}
	
	/**
	 * @return Returns QUrl Object of Url associated with SiteGraphThumbnailer Class  
	 */
	public void setImageAttributes(List<ImageAttributes> imageAttributes) {
		this.imageAttributes = imageAttributes;
	}
	
	/**
	 * Method to load html content from provided url   
	 */
	public abstract boolean makeSnap();
	/**
	 * Called internally by makeSnap() method to save loaded image(s) based on provided ImageAttribute details.  
	 */
	public abstract void loadDone();
	/*
	 * Signal for finished QApplication
	 */
	public void quit(){
		if(logger.isDebugEnabled())
			logger.debug("Snap Created for URL : "+this.url);
	}
}
