/**
 *  Main class for loading HTML content of Web page, Generate images and store it on local file system.
 */
package com.sitegraph.core.image.impl;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;

import com.sitegraph.core.attributes.ImageAttributes;
import com.sitegraph.core.image.IImageThumbnailer;
import com.sitegraph.core.util.SiteGraphConstants;
import com.trolltech.qt.QThread;
import com.trolltech.qt.core.QEventLoop;
import com.trolltech.qt.core.QObject;
import com.trolltech.qt.core.QSize;
import com.trolltech.qt.core.QUrl;
import com.trolltech.qt.core.Qt.AspectRatioMode;
import com.trolltech.qt.core.Qt.Orientation;
import com.trolltech.qt.core.Qt.ScrollBarPolicy;
import com.trolltech.qt.core.Qt.TransformationMode;
import com.trolltech.qt.gui.QColor;
import com.trolltech.qt.gui.QImage;
import com.trolltech.qt.gui.QPainter;
import com.trolltech.qt.webkit.QWebPage;

public class ImageThumbnailerImpl implements IImageThumbnailer {

	private static final Logger logger = Logger.getLogger(ImageThumbnailerImpl.class);
	protected QWebPage page;
	protected QUrl url;
	protected List<ImageAttributes> imageAttributes=null;
	
	
	/*
	 * Default constructor forcefully added for aop scoped auto proxy 
	 */
	public ImageThumbnailerImpl(){
	}
	/**
	 * @param url URL of Web Page in String
	 */
	public ImageThumbnailerImpl(String url){
		this.url= new QUrl(url);
		this.imageAttributes = Arrays.asList(new ImageAttributes[]{ new ImageAttributes()});
	}
	
	/**
	 * @param url URL of Web Page in String
	 * @param imageAttributes object of ImageAttribute Class to provide specific image related information
	 */
	public ImageThumbnailerImpl(String url,ImageAttributes imageAttribute){
		this.url = new QUrl(url);
		this.imageAttributes= Arrays.asList( new ImageAttributes[]{imageAttribute});
	}
	
	/**
	 * @param url URL of Web Page in String
	 * @param imageAttributes List of ImageAttribute Class to provide specific image related information
	 */
	public ImageThumbnailerImpl(String url,List<ImageAttributes> imageAttributes){
		this.url= new QUrl(url);
		this.imageAttributes = imageAttributes;
	}
	
	/**
	 * @return Returns QUrl Object of Url associated with ImageThumbnailer Class  
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
	 * @return Returns QUrl Object of Url associated with ImageThumbnailer Class  
	 */
	public List<ImageAttributes> getImageAttributes() {
		return imageAttributes;
	}
	
	/**
	 * @return Returns QUrl Object of Url associated with ImageThumbnailer Class  
	 */
	public void setImageAttributes(List<ImageAttributes> imageAttributes) {
		this.imageAttributes = imageAttributes;
	}
	
	/**
	 * Method to load html content from provided url   
	 */
	public boolean makeSnap(ImageAttributes imageAttribute){
		
		MyRunnable runnable = new MyRunnable(imageAttribute);
	     QThread thread = new QThread(runnable);
	     runnable.moveToThread(thread);
	     thread.start();
	     while(runnable.getFile()==null){
	    	 try {
				Thread.sleep(1000);
				System.out.println("Waiting for image to be generated . . .");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
	     }
	     System.out.println("File Generated "+runnable.getFile().getAbsolutePath());
	     return runnable.getFile()!=null;
	}
	
    private class MyRunnable extends QObject implements Runnable {

        private QWebPage page;
        private QEventLoop loop;
        private QUrl url;
        private File file;
        private ImageAttributes imageAttribute;
        
        public MyRunnable(ImageAttributes imageAttribute) {
        	this.url=new QUrl(imageAttribute.getUrl());
            page = new QWebPage(this);
            this.imageAttribute=imageAttribute;
        }
        
        public MyRunnable(String url) {
            page = new QWebPage(this);
            this.url= new QUrl(url);
        }

        public void run() {
            try {
                page.loadFinished.connect(this, "loadFinished()");
                page.loadProgress.connect(this,  "loadProgress(int)");
                page.mainFrame().load(new QUrl(imageAttribute.getUrl()));
                System.out.println("Loaded ");
               
            } catch (Throwable e) {
                e.printStackTrace();
            }

            loop = new QEventLoop();
            loop.exec();
        }

        public void loadProgress(final int progress) {
        	System.out.println("In progress of "+url.toString()+" % Completed "+progress);
        }

        public void loadFinished() {
        	QSize size =new QSize(imageAttribute.getImageWidth(),imageAttribute.getImageHeight());
        	page.setViewportSize(size);
			page.mainFrame().setScrollBarPolicy(Orientation.Horizontal, ScrollBarPolicy.ScrollBarAlwaysOff);
			page.mainFrame().setScrollBarPolicy(Orientation.Vertical, ScrollBarPolicy.ScrollBarAlwaysOff);
			
		    QImage image = new QImage(page.viewportSize(), QImage.Format.Format_ARGB32);
		    image.fill(QColor.white.rgb());
		    QPainter painter = new QPainter(image);
		    page.mainFrame().render(painter);
		    painter.end();
		    image = image.scaled(size,AspectRatioMode.IgnoreAspectRatio,TransformationMode.FastTransformation);
		    if(imageAttribute.isMirrored())
		    	image = image.mirrored();
		    String imageName= imageAttribute.getImagePath();
		    logger.debug("Preparing image : "+ imageName);
		    logger.info("Image prepared: "+image.save(imageName));
		    /*
        		page.setViewportSize(page.mainFrame().contentsSize());
            
	            QPrinter printer = new QPrinter();
	            String fileName=url.toString().replaceAll("//","-").replaceAll(":","-" )+new Date().getTime()+".png";
	            printer.setOutputFileName(fileName);
	            //printer.setOutputFormat(QPrinter.OutputFormat.PdfFormat);
	            //QImage w = new QImage(page.viewportSize(), QImage.Format.Format_ARGB32);
	            
	            QPainter painter = new QPainter();
	            painter.begin(printer);
	            page.mainFrame().render(painter);
	            painter.end();
	            //w.save(fileName);
            	System.out.println("Load Finished");
	        */
		    setFile(new File(imageName));
	            loop.exit();
        
        }

		public File getFile() {
			return file;
		}

		public void setFile(File file) {
			this.file = file;
		}
        
    }
	
	/*
	public static void main(String[] args) {
		  new ImageThumbnailerImpl("http://www.google.com").makeSnap();
		  new ImageThumbnailerImpl("http://www.google.com",new PNGImageAttributes()).makeSnap();
		  new ImageThumbnailerImpl("http://www.google.com",new PNGImageAttributes("C:\\temp\\new")).makeSnap();
		  List<ImageAttributes> imageAttributes = new ArrayList<ImageAttributes>();
		  imageAttributes.add(new JPEGImageAttributes("C:\\temp\\JPEGImage"));
		  imageAttributes.add(new JPEGImageAttributes(new QSize(800,600),"C:\\temp\\JPEGImage_800_600"));
		  imageAttributes.add(new PNGImageAttributes(new QSize(800,600),"C:\\temp\\PNGImage_800_600"));
		  new ImageThumbnailerImpl("http://www.facebook.com",imageAttributes).makeSnap();
	}
	*/
}