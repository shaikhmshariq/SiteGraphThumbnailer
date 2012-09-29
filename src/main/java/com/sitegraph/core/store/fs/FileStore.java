/**
 * 
 */
package com.sitegraph.core.store.fs;

import org.apache.log4j.Logger;

import com.sitegraph.core.attributes.ImageAttributes;
import com.sitegraph.core.attributes.pdf.PdfAttributes;
import com.sitegraph.core.store.IStore;
import com.sitegraph.core.util.WebAppUtils;
import com.trolltech.qt.core.QUrl;
import com.trolltech.qt.core.Qt.Orientation;
import com.trolltech.qt.core.Qt.ScrollBarPolicy;
import com.trolltech.qt.gui.QPainter;
import com.trolltech.qt.gui.QPrinter;
import com.trolltech.qt.webkit.QWebPage;

/**
 * @author Mohammed
 *
 */
public class FileStore implements IStore {

	public static final Logger logger = Logger.getLogger(FileStore.class);
	@Override
	public String saveImage(QUrl url, QWebPage page,ImageAttributes imageAttributes) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String savePdf(QUrl url, QWebPage page, PdfAttributes pdfAttributes) {
		page.mainFrame().setScrollBarPolicy(Orientation.Horizontal, ScrollBarPolicy.ScrollBarAlwaysOff);
		page.mainFrame().setScrollBarPolicy(Orientation.Vertical, ScrollBarPolicy.ScrollBarAlwaysOff);
		page.setViewportSize(pdfAttributes.getPageSize());
		logger.debug("Load Finished");
		QPrinter printer = new QPrinter();
		printer.setFullPage(true);
		printer.setOrientation(pdfAttributes.getOrientation());
		printer.setPageSize(pdfAttributes.getPdfTemplateSize());
		printer.setResolution(pdfAttributes.getResolution());
		printer.setOutputFormat(QPrinter.OutputFormat.PdfFormat);
		String pdfFileName= WebAppUtils.resolvePdfStoragePath(pdfAttributes, url.toString());
		printer.setOutputFileName(pdfFileName);
	    QPainter painter = new QPainter(printer);
	    page.mainFrame().render(painter);
	    painter.end();
	    logger.debug("pdf created at location : "+pdfFileName);
	    return pdfFileName;
	}

	/*@Override
	public String savePdf(String handler, QWebPage page,PdfAttributes pdfAttributes) {
		page.mainFrame().setScrollBarPolicy(Orientation.Horizontal, ScrollBarPolicy.ScrollBarAlwaysOff);
		page.mainFrame().setScrollBarPolicy(Orientation.Vertical, ScrollBarPolicy.ScrollBarAlwaysOff);
		page.setViewportSize(pdfAttributes.getPageSize());
		logger.debug("Load Finished");
		QPrinter printer = new QPrinter();
		printer.setFullPage(true);
		printer.setOrientation(pdfAttributes.getOrientation());
		printer.setPageSize(pdfAttributes.getPdfTemplateSize());
		printer.setResolution(pdfAttributes.getResolution());
		printer.setOutputFormat(QPrinter.OutputFormat.PdfFormat);
		String pdfFileName= WebAppUtils.resolvePdfStoragePath(pdfAttributes, handler);
		printer.setOutputFileName(pdfFileName);
	    QPainter painter = new QPainter(printer);
	    page.mainFrame().render(painter);
	    painter.end();
	    logger.debug("pdf created at location : "+pdfFileName);
	    return pdfFileName;
	}*/

	

}
