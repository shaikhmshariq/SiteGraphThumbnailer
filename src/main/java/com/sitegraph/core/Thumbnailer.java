/**
 * 
 */
package com.sitegraph.core;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import com.sitegraph.core.image.IImageThumbnailer;
import com.sitegraph.core.image.impl.ImageThumbnailerImpl;
import com.trolltech.qt.gui.QApplication;


/**
 * @author SHAIKH
 *
 */
public class Thumbnailer {

	private static String IMAGE_SERVICE_END_POINT="ImageService";
	
	public static void main(String args[]){
	
		QApplication.initialize(args);
    	System.setProperty("java.security.policy","file:///D://t.policy");
    	if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }
        try {
            IImageThumbnailer engine = new ImageThumbnailerImpl();
            IImageThumbnailer stub = (IImageThumbnailer) UnicastRemoteObject.exportObject(engine, 0);
            Registry registry = LocateRegistry.getRegistry();
            registry.bind(IMAGE_SERVICE_END_POINT, stub);
            System.out.println("Image Service bound");
        } catch (Exception e) {
            System.err.println("Image Service exception:");
            e.printStackTrace();
        }
        System.out.println("Initializing Qapplication engine ");
        QApplication.exec();
	}

}
