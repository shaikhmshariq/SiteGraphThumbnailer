/**
 * 
 */
package com.sitegraph.core.test;

import java.io.File;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * @author Mohammed
 *
 */
public class ThumbnailerTest {

	
	
	    public static void main(String args[]) {
	        if (System.getSecurityManager() == null) {
	            System.setSecurityManager(new SecurityManager());
	        }
	        try {
	            String name = "Compute";
	            System.setProperty("java.security.policy","file:///D://t.policy");
	            Registry registry = LocateRegistry.getRegistry(args[0]);
	            Thumbnailer comp = (Thumbnailer) registry.lookup(name);
	            File f =comp.doPrint(args[1]);
	            
	            System.out.println(f.getAbsolutePath());
	        } catch (Exception e) {
	            System.err.println("ComputePi exception:");
	            e.printStackTrace();
	        }
	    }    
	
}
