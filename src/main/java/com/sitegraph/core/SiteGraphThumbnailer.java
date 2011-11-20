/**
 *  Main class for loading HTML content of Web page and Generate images.
 */
package com.sitegraph.core;

import java.util.Arrays;
import java.util.List;

import com.sitegraph.core.attributes.ImageAttributes;
import com.sitegraph.core.attributes.PNGImageAttributes;
import com.trolltech.qt.core.QObject;
import com.trolltech.qt.core.QUrl;
import com.trolltech.qt.core.Qt.Orientation;
import com.trolltech.qt.core.Qt.ScrollBarPolicy;
import com.trolltech.qt.gui.QApplication;
import com.trolltech.qt.gui.QColor;
import com.trolltech.qt.gui.QImage;
import com.trolltech.qt.gui.QPainter;
import com.trolltech.qt.network.QNetworkRequest;
import com.trolltech.qt.webkit.QWebPage;


public class SiteGraphThumbnailer extends QObject{

	/**
	 * @param args
	 */
	private QWebPage page;
	private QUrl url;
	private List<ImageAttributes> imageAttributes=null;
	public Signal0 finished = new Signal0();
	
	/**
	 * @param url URL of Web Page in String
	 */
	public SiteGraphThumbnailer(String url){
		this(null,url,Arrays.asList(new ImageAttributes[]{ new PNGImageAttributes()}));
	}
	
	/**
	 * @param url URL of Web Page in String
	 * @param imageAttributes object of ImageAttribute Class to provide specific image related information
	 */
	public SiteGraphThumbnailer(String url,ImageAttributes imageAttribute){
		this(null,url,Arrays.asList(imageAttribute));
	}
	
	/**
	 * @param url URL of Web Page in String
	 * @param imageAttributes List of ImageAttribute Class to provide specific image related information
	 */
	public SiteGraphThumbnailer(String url,List<ImageAttributes> imageAttributes){
		this(null,url,imageAttributes);
	}
	
	/**
	 * @param object object of QObject class 
	 * @param url URL of Web Page in String
	 * @param imageAttributes object of ImageAttribute Class to provide specific image related information
	 */
	public SiteGraphThumbnailer(QObject obj,String url,List<ImageAttributes> imageAttributes){
		super(obj);
		this.imageAttributes=imageAttributes;
		this.url=new QUrl(url);
	}
	
	/**
	 * @return Returns QUrl Object of Url associated with SiteGraphThumbnailer Class  
	 */
	public QUrl getUrl() {
		return url;
	}
	
	/**
	 * @param QUrl object
	 */
	public void setUrl(QUrl url) {
		this.url = url;
	}
	
	/**
	 * @return Returns QUrl Object of Url associated with SiteGraphThumbnailer Class  
	 */
	public List<ImageAttributes> getImageAttributes() {
		return imageAttributes;
	}
	
	/**
	 * @return Returns QUrl Object of Url associated with SiteGraphThumbnailer Class  
	 */
	public void setImageAttributes(List<ImageAttributes> imageAttributes) {
		this.imageAttributes = imageAttributes;
	}
	
	/**
	 * Method to load html content from provided url   
	 */
	public boolean makeSnap(){
		try{
		QApplication.initialize(new String[] { });
		page = new QWebPage(null);
		page.mainFrame().load(new QNetworkRequest(this.url));
		page.loadFinished.connect(this, "loadDone()");
		finished.connect(QApplication.instance(), "quit()");
        QApplication.exec();
		}catch(Exception exp){
			return false;
		}
		return true;
	}
	
	/**
	 * Called internally by makeSnap() method to save loaded image(s) based on provided ImageAttribute details.  
	 */
	public boolean loadDone() {
		for(ImageAttributes imageAttributes: this.imageAttributes){
			page.setViewportSize(imageAttributes.getImageSize());
			page.mainFrame().setScrollBarPolicy(Orientation.Horizontal, ScrollBarPolicy.ScrollBarAlwaysOff);
			page.mainFrame().setScrollBarPolicy(Orientation.Vertical, ScrollBarPolicy.ScrollBarAlwaysOff);
		    QImage image = new QImage(page.viewportSize(), QImage.Format.Format_ARGB32);
		    image.fill(QColor.white.rgb());
		    QPainter painter = new QPainter(image);
		    page.mainFrame().render(painter);
		    painter.end();
		    String imageName=imageAttributes.getAbsoluteImageFilePath() + imageAttributes.getImageSuffix();
		    image.save(imageName);
		}
	    finished.emit();
	    return true;
    }
	
	/*
	public static void main(String[] args) {
		  new SiteGraphThumbnailer("http://www.google.com").makeSnap();
		  new SiteGraphThumbnailer("http://www.google.com",new PNGImageAttributes()).makeSnap();
		  new SiteGraphThumbnailer("http://www.google.com",new PNGImageAttributes("C:\\temp\\new")).makeSnap();
		  List<ImageAttributes> imageAttributes = new ArrayList<ImageAttributes>();
		  imageAttributes.add(new JPEGImageAttributes("C:\\temp\\JPEGImage"));
		  imageAttributes.add(new JPEGImageAttributes(new QSize(800,600),"C:\\temp\\JPEGImage_800_600"));
		  imageAttributes.add(new PNGImageAttributes(new QSize(800,600),"C:\\temp\\PNGImage_800_600"));
		  new SiteGraphThumbnailer("http://www.facebook.com",imageAttributes).makeSnap();
	}
	*/
	

}
