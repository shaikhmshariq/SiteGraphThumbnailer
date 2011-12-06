/**
 * This class will hold utility methods to be used in web layer.
 */
package com.sitegraph.core.util;

import com.sitegraph.core.attributes.ImageAttributes;

public class WebAppUtils {

	/*
	 * This method is used to resolve image name based on its attributes.
	 */
	public static String resolveImageName(ImageAttributes imageAttributes,String inputUrl){
		return imageAttributes.getAbsoluteImageFilePath() + imageAttributes.getImageSuffix();
	}
}
