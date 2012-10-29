/**
 * 
 */
package com.sitegraph.core.util;

import java.util.HashMap;
import java.util.Map;

import com.trolltech.qt.gui.QPrinter;

/**
 * @author Mohammed
 *
 */
public class SiteGraphMappingHelper {

	private static final Map<String,QPrinter.Orientation> orientationMap= new HashMap<String,QPrinter.Orientation>();
	private static final Map<String,QPrinter.PageSize> pageSizeMap= new HashMap<String,QPrinter.PageSize>();
	
	static{
		
		orientationMap.put(SiteGraphConstants.Orientation.PORTRAIT.toString(), QPrinter.Orientation.Portrait);
		orientationMap.put(SiteGraphConstants.Orientation.LANDSCAPE.toString(), QPrinter.Orientation.Landscape);
		
		pageSizeMap.put(SiteGraphConstants.PageSize.A4.toString(), QPrinter.PageSize.A4);
	}
	
	public static QPrinter.Orientation getOrientation(String orientation){
			return orientationMap.get(orientation);
	}
	public static QPrinter.PageSize getPageSize(String pageSize){
			return pageSizeMap.get(pageSize);
	}
}