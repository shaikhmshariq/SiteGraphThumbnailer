/**
 * 
 */
package com.sitegraph.web.controller;

import java.io.File;
import java.util.Date;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.ServletContextAware;

import com.sitegraph.core.SiteGraphThumbnailer;
import com.sitegraph.core.attributes.ImageAttributes;
import com.sitegraph.core.util.WebAppConstants;
import com.sitegraph.core.util.WebAppUtils;
import com.trolltech.qt.core.QSize;
import com.trolltech.qt.core.QUrl;

@Controller
@RequestMapping("/images")
public class SnapController implements ServletContextAware{

	@Autowired
	private SiteGraphThumbnailer thumbnailer;
	private ServletContext servletContext;
	
	/**
	 * Generates image using default PNG image type and size.
	 * @param url URL of web page.
	 * @return
	 */
	@RequestMapping(method=RequestMethod.GET, value="/makesnap/")
	public String makeSnap(@RequestParam("URL") String url,@RequestParam(value="latest",required=false) boolean latest){
		thumbnailer.setUrl(new QUrl(url));
		String response = "redirect:/";
		ImageAttributes imageAttribute = thumbnailer.getImageAttributes().get(0);
		if(!latest && new File(WebAppUtils.resolveImageStoragePath(imageAttribute, url)).exists()){
			String generatedImage =  WebAppUtils.resolveImageWebPath(imageAttribute, thumbnailer.getUrl().toString());
			response = "redirect:/"+generatedImage+"?rand="+new Date().getTime();
		}else{
				if(thumbnailer.makeSnap())
				{
					String generatedImage =  WebAppUtils.resolveImageWebPath(imageAttribute, thumbnailer.getUrl().toString());
					response = "redirect:/"+generatedImage+"?rand="+new Date().getTime();
				}
				
		}
		return response;
	}
	
	/**
	 * Generates image of imageType specified with default size.
	 * @param url URL of web page
	 * @param imageType
	 * @return
	 */
	@RequestMapping(method=RequestMethod.GET, value="/makesnap/{imageType}")
	public String makeSnapOfType(@RequestParam("URL") String url,@PathVariable String imageType,@RequestParam(value="latest",required=false) boolean latest){
		thumbnailer.setUrl(new QUrl(url));
		ImageAttributes imageAttribute = thumbnailer.getImageAttributes().get(0);
		imageType = WebAppUtils.validateImageType(imageType);
		imageAttribute.setImageSuffix(imageType);
		String response = "redirect:/";
		
		if(!latest && new File(WebAppUtils.resolveImageStoragePath(imageAttribute, url)).exists()){
			String generatedImage =  WebAppUtils.resolveImageWebPath(imageAttribute, thumbnailer.getUrl().toString());
			response = "redirect:/"+generatedImage+"?rand="+new Date().getTime();
		}else{
				if(thumbnailer.makeSnap())
				{
					String generatedImage =  WebAppUtils.resolveImageWebPath(imageAttribute, thumbnailer.getUrl().toString());
					response = "redirect:/"+generatedImage+"?rand="+new Date().getTime();
				}
		}
		return response;
	}
	
	/**
	 * Generates image using provided parameters.
	 * @param url
	 * @param imageType
	 * @param imageWidth
	 * @param imageHeight
	 * @return
	 */
	@RequestMapping(method=RequestMethod.GET, value="/makesnap/{imageType}/{imageWidth}/{imageHeight}")
	public String makeSnapOfSize(@RequestParam("URL") String url,@PathVariable String imageType,@PathVariable int imageWidth, @PathVariable int imageHeight,@RequestParam(value="latest",required=false) boolean latest){
		thumbnailer.setUrl(new QUrl(url));
		ImageAttributes imageAttribute = thumbnailer.getImageAttributes().get(0);
		QSize imageSize = imageAttribute.getImageSize();
		imageSize.setHeight(imageHeight);
		imageSize.setWidth(imageWidth);
		imageType = WebAppUtils.validateImageType(imageType);
		imageAttribute.setImageSuffix(imageType);
		
		String response = "redirect:/";
		if(!latest && new File(WebAppUtils.resolveImageStoragePath(imageAttribute, url)).exists()){
			String generatedImage =  WebAppUtils.resolveImageWebPath(imageAttribute, thumbnailer.getUrl().toString());
			response = "redirect:/"+generatedImage+"?rand="+new Date().getTime();
		}else{
				if(thumbnailer.makeSnap())
				{
					String generatedImage =  WebAppUtils.resolveImageWebPath(imageAttribute, thumbnailer.getUrl().toString());
					response = "redirect:/"+generatedImage+"?rand="+new Date().getTime();
				}
			}
		return response;
	}
	
	/**
	 * Generates mirror image of web page.
	 * @param url
	 * @return
	 */
	@RequestMapping(method=RequestMethod.GET, value="/mirrorsnap/")
	public String makeMirrorSnap(@RequestParam("URL") String url,@RequestParam(value="latest",required=false) boolean latest){
		thumbnailer.setUrl(new QUrl(url));
		ImageAttributes imageAttribute = thumbnailer.getImageAttributes().get(0);
		imageAttribute.setMirrored(true);
		
		String response = "redirect:/";
		if(!latest && new File(WebAppUtils.resolveImageStoragePath(imageAttribute, url)).exists()){
			String generatedImage =  WebAppUtils.resolveImageWebPath(imageAttribute, thumbnailer.getUrl().toString());
			response = "redirect:/"+generatedImage+"?rand="+new Date().getTime();
		}else{
				if(thumbnailer.makeSnap())
				{
					String generatedImage =  WebAppUtils.resolveImageWebPath(imageAttribute, thumbnailer.getUrl().toString());
					response = "redirect:/"+generatedImage+"?rand="+new Date().getTime();
				}
			}
		return response;
	}
	
	/**
	 * Generates mirror image of imageType provided.
	 * @param url
	 * @param imageType
	 * @return
	 */
	@RequestMapping(method=RequestMethod.GET, value="/mirrorsnap/{imageType}")
	public String makeMirrorSnapOfType(@RequestParam("URL") String url,@PathVariable String imageType,@RequestParam(value="latest",required=false) boolean latest){
		thumbnailer.setUrl(new QUrl(url));
		ImageAttributes imageAttribute = thumbnailer.getImageAttributes().get(0);
		imageAttribute.setMirrored(true);
		imageType = WebAppUtils.validateImageType(imageType);
		imageAttribute.setImageSuffix(imageType);
		
		String response = "redirect:/";
		if(!latest && new File(WebAppUtils.resolveImageStoragePath(imageAttribute, url)).exists()){
			String generatedImage =  WebAppUtils.resolveImageWebPath(imageAttribute, thumbnailer.getUrl().toString());
			response = "redirect:/"+generatedImage+"?rand="+new Date().getTime();
		}else{
				if(thumbnailer.makeSnap())
				{
					String generatedImage =  WebAppUtils.resolveImageWebPath(imageAttribute, thumbnailer.getUrl().toString());
					response = "redirect:/"+generatedImage+"?rand="+new Date().getTime();
				}
			}
		return response;
	}
	
	/**
	 * Generates mirror image of provided parameters.
	 * @param url
	 * @param imageType
	 * @param imageWidth
	 * @param imageHeight
	 * @return
	 */
	@RequestMapping(method=RequestMethod.GET, value="/mirrorsnap/{imageType}/{imageWidth}/{imageHeight}")
	public String makeMirrorSnapOfSize(@RequestParam("URL") String url,@PathVariable String imageType,@PathVariable int imageWidth, @PathVariable int imageHeight,@RequestParam(value="latest",required=false) boolean latest){
		thumbnailer.setUrl(new QUrl(url));
		ImageAttributes imageAttribute = thumbnailer.getImageAttributes().get(0);
		imageAttribute.setMirrored(true);
		QSize imageSize = imageAttribute.getImageSize();
		imageSize.setHeight(imageHeight);
		imageSize.setWidth(imageWidth);
		imageType = WebAppUtils.validateImageType(imageType);
		imageAttribute.setImageSuffix(imageType);
		
		String response = "redirect:/";
		if(!latest && new File(WebAppUtils.resolveImageStoragePath(imageAttribute, url)).exists()){
			String generatedImage =  WebAppUtils.resolveImageWebPath(imageAttribute, thumbnailer.getUrl().toString());
			response = "redirect:/"+generatedImage+"?rand="+new Date().getTime();
		}else{
				if(thumbnailer.makeSnap())
				{
					String generatedImage =  WebAppUtils.resolveImageWebPath(imageAttribute, thumbnailer.getUrl().toString());
					response = "redirect:/"+generatedImage+"?rand="+new Date().getTime();
				}
		}
		return response;
	}
	
	/**
	 * Default landing page.
	 * @param response
	 * @return
	 */
	@RequestMapping("/")
	@ResponseBody()
	public String homePage(HttpServletResponse response){
		response.setContentType("text/html");
		return "<title>SiteGraphThumbnailer</title><h1>Welcome to SiteGraph</h1>Soon some meaningful contents will be here. ";
	}

	/* (non-Javadoc)
	 * @see org.springframework.web.context.ServletContextAware#setServletContext(javax.servlet.ServletContext)
	 */
	@Override
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
		WebAppConstants.IMAGE_ABSOLUTE_PATH = this.servletContext.getRealPath("/") + WebAppConstants.IMAGE_ABSOLUTE_PATH; 
		System.out.println("Default path : "+ WebAppConstants.IMAGE_ABSOLUTE_PATH);
	} 
	
	/**
	 * @return
	 */
	public ServletContext getServletContext(){
		return this.servletContext;
	} 
}
