/**
 * 
 */
package com.sitegraph.web.controller;

import java.io.File;
import java.util.Date;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.ServletContextAware;

import com.sitegraph.core.attributes.pdf.PdfAttributes;
import com.sitegraph.core.pdf.PdfThumbnailer;
import com.sitegraph.core.util.WebAppConstants;
import com.sitegraph.core.util.WebAppUtils;
import com.trolltech.qt.core.QUrl;

@Controller
@RequestMapping("/pdf")
public class PdfController implements ServletContextAware{

	@Autowired
	private PdfThumbnailer thumbnailer;
	private ServletContext servletContext;
	
	/**
	 * Generates image using default PNG image type and size.
	 * @param url URL of web page.
	 * @return
	 */
	@RequestMapping(method=RequestMethod.GET, value="/makepdf/")
	public String makePdf(@RequestParam("URL") String url,@RequestParam(value="latest",required=false,defaultValue="false") boolean latest){
		thumbnailer.setUrl(new QUrl(url));
		String response = "redirect:/";
		PdfAttributes pdfAttribute = thumbnailer.getPdfAttributes().get(0);
		if(!latest && new File(WebAppUtils.resolvePdfStoragePath(pdfAttribute, url)).exists()){
			String generatedImage =  WebAppUtils.resolvePdfWebPath(pdfAttribute, thumbnailer.getUrl().toString());
			response = "redirect:/"+generatedImage+"?rand="+new Date().getTime();
		}else{
				if(thumbnailer.makePdfFromUrl())
				{
					String generatedImage =  WebAppUtils.resolvePdfWebPath(pdfAttribute, thumbnailer.getUrl().toString());
					response = "redirect:/"+generatedImage+"?rand="+new Date().getTime();
				}
				
		}
		return response;
	}
	
	/**
	 * Generates image using default PNG image type and size.
	 * @param url URL of web page.
	 * @return
	 */
	/*@RequestMapping(method=RequestMethod.GET, value="/makepdf/")
	public String makePdf(@RequestParam("html") String html){
		thumbnailer.setHtml(html);
		String response = "redirect:/";
		PdfAttributes pdfAttribute = thumbnailer.getPdfAttributes().get(0);
		if(thumbnailer.makePdf())
		{
			String generatedImage =  WebAppUtils.resolvePdfWebPath(pdfAttribute, thumbnailer.getUrl().toString());
			response = "redirect:/"+generatedImage+"?rand="+new Date().getTime();
		}	
		
		return response;
	}*/

	/* (non-Javadoc)
	 * @see org.springframework.web.context.ServletContextAware#setServletContext(javax.servlet.ServletContext)
	 */
	@Override
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
		WebAppConstants.PDF_ABSOLUTE_PATH = this.servletContext.getRealPath("/") + WebAppConstants.PDF_ABSOLUTE_PATH; 
		System.out.println("Default path : "+ WebAppConstants.PDF_ABSOLUTE_PATH);
	} 
	
	/**
	 * @return
	 */
	public ServletContext getServletContext(){
		return this.servletContext;
	} 
}
