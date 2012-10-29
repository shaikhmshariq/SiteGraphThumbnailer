/**
 * 
 */
package com.sitegraph.core.pdf;

import java.rmi.Remote;
import java.rmi.RemoteException;

import com.sitegraph.core.attributes.pdf.PdfAttributes;

/**
 * @author Mohammed
 *
 */
public interface IPdfThumbnailer extends Remote{

	/**
	 * Method to load html content from provided url   
	 */
	public boolean makePdfFromUrl(PdfAttributes pdfAttributes) throws RemoteException;
	/**
	 * Method to load html content from provided url   
	 */
	public boolean makePdfFromHTML(PdfAttributes pdfAttributes,String handler) throws RemoteException;
	/**
	 * Called internally by makeSnap() method to save loaded image(s) based on provided ImageAttribute details.  
	 */
	
}
