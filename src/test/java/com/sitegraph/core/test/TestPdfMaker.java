/**
 * 
 */
package com.sitegraph.core.test;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.sitegraph.core.pdf.impl.PdfThumbnailerImpl;

/**
 * @author Mohammed
 *
 */
public class TestPdfMaker {

	private static final String url = "http://www.google.com";
	
	/**
	 * Test method for {@link com.sitegraph.core.pdf.impl.PdfThumbnailerImpl#makePdf()}.
	 */
	@Test
	public void testMakeSnap() {
		assertTrue(new PdfThumbnailerImpl(url).makePdf());
	}

}
