/**
 * 
 */
package com.sitegraph.core.store;

import com.sitegraph.core.attributes.ImageAttributes;
import com.sitegraph.core.attributes.pdf.PdfAttributes;
import com.trolltech.qt.core.QUrl;
import com.trolltech.qt.webkit.QWebPage;

/**
 * @author Mohammed
 *
 */
public interface IStore {

	
	/**
	 * Saves image file in the server file system.
	 * @param url Web page URL.
	 * @param page QWebPage object which holds HTML content from specified url.
	 * @param image attributes
	 * @return Path to the resource to be accessed by controller. 
	 */
	public String saveImage(QUrl url,QWebPage page,ImageAttributes imageAttributes);
	
	/*
	 * Save pdf file in the server file system.
	 * @param url Web page URL.
	 * @param page QWebPage object which holds HTML content from specified url.
	 * @param pdf attributes
	 * @return Path to the resource to be accessed by controller. 
	 */
	public String savePdf(QUrl url,QWebPage page,PdfAttributes pdfAttributes);
}
