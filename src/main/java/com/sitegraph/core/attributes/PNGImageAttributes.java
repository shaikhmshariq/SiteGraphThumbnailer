/**
 *  Class holds image attributes for PNG images.
 */
package com.sitegraph.core.attributes;

import com.sitegraph.core.util.Constants;
import com.trolltech.qt.core.QSize;

public class PNGImageAttributes extends ImageAttributes {

	private String mimeType="image/png";
	
	/**
	 *  Class Default constructor
	 */
	public PNGImageAttributes(){
		this.imageSuffix= Constants.PNG_IMAGE_SUFFIX;
	}
	
	/**
	 * @param absoluteImageFilePath Absolute Image path without extension details
	 */
	public PNGImageAttributes(String absoluteImageFilePath){
		this.imageSuffix= Constants.PNG_IMAGE_SUFFIX;
		this.absoluteImageFilePath = absoluteImageFilePath;
	}
	/**
	 * @param imageSize Image size wrapped in QSize object.
	 */
	public PNGImageAttributes(QSize imageSize) {
		super(Constants.PNG_IMAGE_SUFFIX, imageSize);
	}
	
	/**
	 * @param imageSize Image size wrapped in QSize object.
	 * @param absoluteImageFilePath Absolute Image path without extension details
	 */
	public PNGImageAttributes(QSize imageSize,String absoluteImageFilePath) {
		super(Constants.PNG_IMAGE_SUFFIX, imageSize, absoluteImageFilePath);
	}
}
