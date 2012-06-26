/**
 *  Generic class for loading HTML content of Web page and Generate images.
 */
package com.sitegraph.core.pdf;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.sitegraph.core.attributes.pdf.PdfAttributes;
import com.sitegraph.core.store.IStore;
import com.sitegraph.core.util.Constants;
import com.trolltech.qt.core.QObject;
import com.trolltech.qt.core.QUrl;
import com.trolltech.qt.webkit.QWebPage;


public abstract class PdfThumbnailer extends QObject{

	/**
	 * @param args
	 */
	protected QWebPage page;
	protected QUrl url;
	protected String html;
	protected List<PdfAttributes> pdfAttributes=null;
	public Signal0 finished = new Signal0();
	
	/*
	 * Default constructor forcefully added for aop scoped auto proxy 
	 */
	public PdfThumbnailer(){
		this(null,Constants.DEFAULT_URL,Arrays.asList(new PdfAttributes[]{ new PdfAttributes()}));
	}
	/**
	 * @param url URL of Web Page in String
	 */
	public PdfThumbnailer(String url){
		this(null,url,Arrays.asList(new PdfAttributes[]{ new PdfAttributes()}));
	}
	
	/**
	 * @param url URL of Web Page in String
	 * @param imageAttributes object of ImageAttribute Class to provide specific image related information
	 */
	public PdfThumbnailer(String url,PdfAttributes pdfAttributes){
		this(null,url,Arrays.asList(pdfAttributes));
	}
	
	/**
	 * @param url URL of Web Page in String
	 * @param imageAttributes List of ImageAttribute Class to provide specific image related information
	 */
	public PdfThumbnailer(String url,List<PdfAttributes> pdfAttributes){
		this(null,url,pdfAttributes);
	}
	
	/**
	 * @param object object of QObject class 
	 * @param url URL of Web Page in String
	 * @param imageAttributes object of ImageAttribute Class to provide specific image related information
	 */
	public PdfThumbnailer(QObject obj,String url,List<PdfAttributes> pdfAttributes){
		super(obj);
		this.pdfAttributes=pdfAttributes;
		this.url=new QUrl(url);
	}
	
	/**
	 * @return Returns QUrl Object of Url associated with PdfThumbnailer Class  
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
	
	
	public List<PdfAttributes> getPdfAttributes() {
		return pdfAttributes;
	}
	public void setPdfAttributes(List<PdfAttributes> pdfAttributes) {
		this.pdfAttributes = pdfAttributes;
	}
	/**
	 * Method to load html content from provided url   
	 */
	public abstract boolean makePdfFromUrl();
	/**
	 * Method to load html content from provided url   
	 */
	//public abstract boolean makePdfFromHTML(String html,String handler);
	/**
	 * Called internally by makeSnap() method to save loaded image(s) based on provided ImageAttribute details.  
	 */
	public String getHtml() {
		return html;
	}
	public void setHtml(String html) {
		this.html = html;
	}

	
}
