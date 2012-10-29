/**
 *   Generic Class holds image attributes.

 */
package com.sitegraph.core.attributes;

import java.io.Serializable;

import com.sitegraph.core.util.SiteGraphConstants;

public class ImageAttributes implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2098837372709682497L;
	protected String imageSuffix=null;
	protected int imageHeight;
	protected int imageWidth;
	protected boolean mirrored = false;
	protected String url=null;
	protected String imagePath=null;
	/**
	 *  Class Default constructor
	 */
	public ImageAttributes(){
		this.imageSuffix = SiteGraphConstants.PNG_IMAGE_SUFFIX;
		this.imageWidth = SiteGraphConstants.DEFAULT_IMAGE_WIDTH;
		this.imageHeight= SiteGraphConstants.DEFAULT_IMAGE_HEIGHT;
	}
	
	/**
	 * @param imageSuffix Image suffix .png.jpeg etc 
	 * @param imageSize Image size wrapped in QSize Object
	 * @param absoluteImageFilePath Absolute Image path without extension details
	 */
	public ImageAttributes(String imageSuffix, int imageHeight,int imageWidth,String absoluteImageFilePath) {

		super();
		this.imageSuffix = imageSuffix;
		this.imageHeight = imageHeight;
		this.imageWidth= imageWidth;
	}
	
	/**
	 * @param imageSuffix Image suffix .png.jpeg etc 
	 * @param imageSize Image size wrapped in QSize Object
	 */
	public ImageAttributes(String imageSuffix, int imageHeight,int imageWidth) {
		super();
		this.imageSuffix = imageSuffix;
		this.imageHeight = imageHeight;
		this.imageWidth= imageWidth;
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
	
		
	public int getImageHeight() {
		return imageHeight;
	}

	public void setImageHeight(int imageHeight) {
		this.imageHeight = imageHeight;
	}

	public int getImageWidth() {
		return imageWidth;
	}

	public void setImageWidth(int imageWidth) {
		this.imageWidth = imageWidth;
	}

	/**
	 * @param Denotes if mirror image needs to be created.  
	 */
	public boolean isMirrored() {
		return mirrored;
	}

	/**
	 * @param Sets mirror image indicator.  
	 */
	public void setMirrored(boolean mirrored) {
		this.mirrored = mirrored;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	
	
}
