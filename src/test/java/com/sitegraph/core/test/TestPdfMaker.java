/**
 * 
 */
package com.sitegraph.core.test;

import static org.junit.Assert.*;

import org.junit.Test;

import com.sitegraph.core.fs.PdfMaker;

/**
 * @author Mohammed
 *
 */
public class TestPdfMaker {

	private static final String url = "http://www.shaikhmshariq.wordpress.com";
	private static final String IMAGE_PATH = "C:\\temp\\Image";
	
	/**
	 * Test method for {@link com.sitegraph.core.fs.PdfMaker#makeSnap()}.
	 */
	@Test
	public void testMakeSnap() {
		assertTrue(new PdfMaker(url).makeSnap());
	}

}
