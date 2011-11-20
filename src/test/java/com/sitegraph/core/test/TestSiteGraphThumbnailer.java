package com.sitegraph.core.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.sitegraph.core.SiteGraphThumbnailer;
import com.sitegraph.core.attributes.ImageAttributes;
import com.sitegraph.core.attributes.JPEGImageAttributes;
import com.sitegraph.core.attributes.PNGImageAttributes;
import com.sitegraph.core.util.Constants;
import com.trolltech.qt.core.QSize;

public class TestSiteGraphThumbnailer {

	private static final String url = "http://www.yahoo.com";
	private static final String IMAGE_PATH = "C:\\temp\\Image";
	private static final QSize Q_800x600 = new QSize(800,600);
	private static final ImageAttributes pngImageAttributes = new PNGImageAttributes(IMAGE_PATH + "_PNG");
	
	@Test
	public void testMakeSnap() {
		 //will create image in current directory from where this application is running
		  assertTrue(new SiteGraphThumbnailer(url).makeSnap());
	}
	
	@Test
	public void testGetUrl(){
		SiteGraphThumbnailer thumbnailer = new SiteGraphThumbnailer(url);
		assertTrue(url.equals(thumbnailer.getUrl().toString()));
		assertTrue(thumbnailer.makeSnap());
	}
	
	@Test
	public void testPNGImageAttributes(){
		SiteGraphThumbnailer thumbnailer = new SiteGraphThumbnailer(url,pngImageAttributes);
		assertTrue(thumbnailer.getImageAttributes().size() == 1);
		ImageAttributes attributes = thumbnailer.getImageAttributes().get(0);
		assertNotNull(attributes);
		assertTrue(Constants.PNG_IMAGE_SUFFIX.equals(attributes.getImageSuffix()));
		assertTrue(attributes.getImageSize().height() == Constants.DEFAULT_IMAGE_HEIGHT);
		assertTrue(attributes.getImageSize().width() == Constants.DEFAULT_IMAGE_WIDTH);
		assertTrue(url.equals(thumbnailer.getUrl().toString()));		
		assertTrue(thumbnailer.makeSnap());
	}
	
	@Test
	public void testMultipleImageAttributes(){
		  List<ImageAttributes> imageAttributes = new ArrayList<ImageAttributes>();
		  imageAttributes.add(new JPEGImageAttributes(IMAGE_PATH + "_JPEG"));
		  imageAttributes.add(new JPEGImageAttributes(Q_800x600,IMAGE_PATH + "_JPEG_800_600"));
		  imageAttributes.add(new PNGImageAttributes(Q_800x600,IMAGE_PATH + "_PNG_800_600"));
		  SiteGraphThumbnailer thumbnailer = new SiteGraphThumbnailer(url,imageAttributes);
		  //TODO add assert statement to check each image attribute in list
		  assertTrue(thumbnailer.makeSnap());
	}
	
}
