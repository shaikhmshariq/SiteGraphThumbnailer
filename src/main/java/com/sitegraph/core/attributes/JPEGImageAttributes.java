/**
 *  Class holds image attributes for JPEG images.
 */
package com.sitegraph.core.attributes;

import com.sitegraph.core.util.Constants;
import com.trolltech.qt.core.QSize;

public class JPEGImageAttributes extends ImageAttributes {

	private String mimeType="image/jpeg";
	/**
	 *  Class Default constructor
	 */
	public JPEGImageAttributes(){
		this.imageSuffix= Constants.JPEG_IMAGE_SUFFIX;
	}
	
	/**
	 * @param absoluteImageFilePath Absolute Image path without extension details
	 */
	public JPEGImageAttributes(String absoluteImageFilePath){
		this.imageSuffix= Constants.JPEG_IMAGE_SUFFIX;
		this.absoluteImageFilePath = absoluteImageFilePath;
	}

	/**
	 * @param imageSize Image size wrapped in QSize object.
	 */
	public JPEGImageAttributes(QSize imageSize) {
		super(Constants.JPEG_IMAGE_SUFFIX, imageSize);
	}
	
	/**
	 * @param imageSize Image size wrapped in QSize object.
	 * @param absoluteImageFilePath Absolute Image path without extension details
	 */
	public JPEGImageAttributes(QSize imageSize,String absoluteImageFilePath) {
		super(Constants.JPEG_IMAGE_SUFFIX, imageSize, absoluteImageFilePath);
	}
}
