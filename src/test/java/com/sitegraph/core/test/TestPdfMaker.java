/**
 * 
 */
package com.sitegraph.core.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.sitegraph.core.pdf.PdfThumbnailer;
import com.trolltech.qt.core.QUrl;
import com.trolltech.qt.gui.QApplication;

/**
 * @author Mohammed
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value="/test-applicationContext.xml")
public class TestPdfMaker {

	private static final String url = "http://www.nividous.com";
	
	@Autowired
	PdfThumbnailer thumbnailer;
	/**
	 * Test method for {@link com.sitegraph.core.pdf.impl.PdfThumbnailerImpl#makePdf()}.
	 */
	@Test	
	public void testMakeSnap() {
		//System.setProperty("com.trolltech.qt.thread-check", "no");
		thumbnailer.setUrl(new QUrl(url));
		QApplication.invokeLater(new Runnable(){
			public void run(){
				thumbnailer.signal.emit();
			}
			
		});
		
		
	}

}
