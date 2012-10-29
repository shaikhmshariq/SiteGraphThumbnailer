/**
 * 
 */
package com.sitegraph.core.attributes.pdf;

import com.sitegraph.core.util.SiteGraphConstants;
import com.trolltech.qt.core.QSize;

/**
 * @author Mohammed
 *
 */
public class PdfAttributes {

	private SiteGraphConstants.PageSize pdfTemplateSize = SiteGraphConstants.PageSize.A4;
	private SiteGraphConstants.Orientation orientation = SiteGraphConstants.Orientation.PORTRAIT;
	private int resolution = 130;
	private QSize pageSize = new QSize(SiteGraphConstants.DEFAULT_PDF_WIDTH,SiteGraphConstants.DEFAULT_PDF_HEIGHT);
	private String pdfPath=null;
	private String html=null;
	private String url=null;
	
	
	public QSize getPageSize() {
		return pageSize;
	}
	public void setPageSize(QSize pageSize) {
		this.pageSize = pageSize;
	}
	public SiteGraphConstants.PageSize getPdfTemplateSize() {
		return pdfTemplateSize;
	}
	public void setPdfTemplateSize(SiteGraphConstants.PageSize pdfTemplateSize) {
		this.pdfTemplateSize = pdfTemplateSize;
	}
	public SiteGraphConstants.Orientation getOrientation() {
		return orientation;
	}
	public void setOrientation(SiteGraphConstants.Orientation orientation) {
		this.orientation = orientation;
	}
	public int getResolution() {
		return resolution;
	}
	public void setResolution(int resolution) {
		this.resolution = resolution;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getPdfPath() {
		return pdfPath;
	}
	public void setPdfPath(String pdfPath) {
		this.pdfPath = pdfPath;
	}
	public String getHtml() {
		return html;
	}
	public void setHtml(String html) {
		this.html = html;
	}
	
}
