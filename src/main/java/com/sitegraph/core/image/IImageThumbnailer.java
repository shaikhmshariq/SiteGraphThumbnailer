/**
 * 
 */
package com.sitegraph.core.image;

import java.rmi.Remote;
import java.rmi.RemoteException;

import com.sitegraph.core.attributes.ImageAttributes;

/**
 * @author Mohammed
 *
 */
public interface IImageThumbnailer extends Remote{

	/**
	 * Method to load html content from provided url   
	 */
	public boolean makeSnap(ImageAttributes imageAttribute) throws RemoteException;
}
