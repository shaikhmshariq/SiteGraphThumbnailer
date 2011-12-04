/**
 *   Generic Class holds image attributes.

 */
package com.sitegraph.core.attributes;

import com.sitegraph.core.util.Constants;
import com.trolltech.qt.core.QSize;

public class ImageAttributes {

	protected String imageSuffix=null;
	protected QSize imageSize=null;
	protected String absoluteImageFilePath = Constants.DEFAULT_IMAGE_ABSOLUTH_PATH;
	
	/**
	 *  Class Default constructor
	 */
	public ImageAttributes(){
		this.imageSize = new QSize(Constants.DEFAULT_IMAGE_WIDTH,Constants.DEFAULT_IMAGE_HEIGHT);
	}
	
	/**
	 * @param imageSuffix Image suffix .png.jpeg etc 
	 * @param imageSize Image size wrapped in QSize Object
	 * @param absoluteImageFilePath Absolute Image path without extension details
	 */
	public ImageAttributes(String imageSuffix, QSize imageSize,String absoluteImageFilePath) {

		super();
		this.imageSuffix = imageSuffix;
		this.imageSize = imageSize;
		this.absoluteImageFilePath = absoluteImageFilePath;
	}
	
	/**
	 * @param imageSuffix Image suffix .png.jpeg etc 
	 * @param imageSize Image size wrapped in QSize Object
	 */
	public ImageAttributes(String imageSuffix, QSize imageSize) {
		super();
		this.imageSuffix = imageSuffix;
		this.imageSize = imageSize;
	}
	
	/**
	 * @param imageSuffix Image suffix .png.jpeg etc
	 */
	public ImageAttributes(String imageSuffix) {
		super();
		this.imageSuffix = imageSuffix;
	}
	
	/**
	 * @return Returns Image suffix attached with this ImageAttribute object.  
	 */

	public String getImageSuffix() {
		return imageSuffix;
	}
	
	/**
	 * @param Sets Image suffix attached with this ImageAttribute object.  
	 */
	public void setImageSuffix(String imageSuffix) {
		this.imageSuffix = imageSuffix;
	}
	
	/**
	 * @return Returns Image suffix attached with this ImageAttribute object.  
	 */
	public QSize getImageSize() {
		return imageSize;
	}
	
	/**
	 * @param Sets Image size attached with this ImageAttribute object wrapped in QSize Object.  
	 */
	public void setImageSize(QSize imageSize) {
		this.imageSize = imageSize;
	}
	
	/**
	 * @return Returns Image suffix attached with this ImageAttribute object.  
	 */
	public String getAbsoluteImageFilePath() {
		return absoluteImageFilePath;
	}

	/**
	 * @param Sets Image's absolute path except image suffix.  
	 */
	public void setAbsoluteImageFilePath(String absoluteImageFilePath) {
		this.absoluteImageFilePath = absoluteImageFilePath;
	}
	
	
}
