/**
 * 
 */
package com.sitegraph.core.attributes.pdf;

import com.sitegraph.core.util.Constants;
import com.sitegraph.core.util.WebAppConstants;
import com.trolltech.qt.core.QSize;
import com.trolltech.qt.gui.QPrinter;

/**
 * @author Mohammed
 *
 */
public class PdfAttributes {

	private QPrinter.PageSize pdfTemplateSize = QPrinter.PageSize.A4;
	private QPrinter.Orientation orientation = QPrinter.Orientation.Portrait;
	private int resolution = 130;
	private QSize pageSize = new QSize(Constants.DEFAULT_PDF_WIDTH,Constants.DEFAULT_PDF_HEIGHT);
	private String absolutePdfFilePath = WebAppConstants.PDF_ABSOLUTE_PATH;;
	
	public String getAbsolutePdfFilePath() {
		return absolutePdfFilePath;
	}
	public void setAbsolutePdfFilePath(String absolutePdfFilePath) {
		this.absolutePdfFilePath = absolutePdfFilePath;
	}
	public QSize getPageSize() {
		return pageSize;
	}
	public void setPageSize(QSize pageSize) {
		this.pageSize = pageSize;
	}
	public QPrinter.PageSize getPdfTemplateSize() {
		return pdfTemplateSize;
	}
	public void setPdfTemplateSize(QPrinter.PageSize pdfTemplateSize) {
		this.pdfTemplateSize = pdfTemplateSize;
	}
	public QPrinter.Orientation getOrientation() {
		return orientation;
	}
	public void setOrientation(QPrinter.Orientation orientation) {
		this.orientation = orientation;
	}
	public int getResolution() {
		return resolution;
	}
	public void setResolution(int resolution) {
		this.resolution = resolution;
	}
	
	
}
