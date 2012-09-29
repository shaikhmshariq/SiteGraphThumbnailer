package com.sitegraph.core.test;

import java.io.File;
import java.rmi.Remote;
import java.rmi.RemoteException;


public interface Thumbnailer extends Remote{
	
	public File doPrint(String url) throws RemoteException;
}