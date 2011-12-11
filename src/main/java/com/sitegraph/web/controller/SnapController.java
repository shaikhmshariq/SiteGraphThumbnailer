/**
 * 
 */
package com.sitegraph.web.controller;

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
@RequestMapping("/image")
public class SnapController implements ServletContextAware{

	@Autowired
	private SiteGraphThumbnailer thumbnailer;
	private ServletContext servletContext;
	
	@RequestMapping(method=RequestMethod.GET, value="/makesnap/")
	public String makeSnap(@RequestParam("URL") String url){
		thumbnailer.setUrl(new QUrl(url));
		if(thumbnailer.makeSnap())
		{
			String generatedImage =  WebAppUtils.resolveImageWebPath(thumbnailer.getImageAttributes().get(0), thumbnailer.getUrl().toString());
			System.out.println(generatedImage);
			return "redirect:/"+generatedImage+"?rand="+new Date().getTime();
		}
		else
			return "redirect:/";
	}
	
	@RequestMapping(method=RequestMethod.GET, value="/makesnap/{imageType}")
	public String makeSnapOfType(@RequestParam("URL") String url,@PathVariable String imageType){
		thumbnailer.setUrl(new QUrl(url));
		ImageAttributes attribute = thumbnailer.getImageAttributes().get(0);
		imageType = WebAppUtils.validateImageType(imageType);
		attribute.setImageSuffix(imageType);
		if(thumbnailer.makeSnap())
		{
			String generatedImage =  WebAppUtils.resolveImageWebPath(thumbnailer.getImageAttributes().get(0), thumbnailer.getUrl().toString());
			return "redirect:/"+generatedImage+"?rand="+new Date().getTime();
		}
		else
			return "redirect:/";
	}
	/*
	@RequestMapping(method=RequestMethod.GET, value="/makesnap/{imageType}/{imageWidth}")
	public String makeSnapOfHeight(@RequestParam("URL") String url,@PathVariable String imageType,@PathVariable int imageWidth){
		thumbnailer.setUrl(new QUrl(url));
		ImageAttributes attribute = thumbnailer.getImageAttributes().get(0);
		QSize imageSize = attribute.getImageSize();
		imageSize.setWidth(imageWidth);
		imageType = WebAppUtils.validateImageType(imageType);
		attribute.setImageSuffix(imageType);
		if(thumbnailer.makeSnap())
		{
			String generatedImage =  WebAppUtils.resolveImageWebPath(thumbnailer.getImageAttributes().get(0), thumbnailer.getUrl().toString());
			return "redirect:/"+generatedImage+"?rand="+new Date().getTime();
		}
		else
			return "redirect:/";
	}
	
	@RequestMapping(method=RequestMethod.GET, value="/makesnap/{imageType}/{imageHeight}")
	public String makeSnapOfWidth(@RequestParam("URL") String url,@PathVariable String imageType,@PathVariable int imageHeight){
		thumbnailer.setUrl(new QUrl(url));
		ImageAttributes attribute = thumbnailer.getImageAttributes().get(0);
		QSize imageSize = attribute.getImageSize();
		imageSize.setHeight(imageHeight);
		imageType = WebAppUtils.validateImageType(imageType);
		attribute.setImageSuffix(imageType);
		if(thumbnailer.makeSnap())
		{
			String generatedImage =  WebAppUtils.resolveImageWebPath(thumbnailer.getImageAttributes().get(0), thumbnailer.getUrl().toString());
			return "redirect:/"+generatedImage+"?rand="+new Date().getTime();
		}
		else
			return "redirect:/";
	}
	*/
	@RequestMapping(method=RequestMethod.GET, value="/makesnap/{imageType}/{imageHeight}/{imageWidth}")
	public String makeSnapOfSize(@RequestParam("URL") String url,@PathVariable String imageType,@PathVariable int imageHeight, @PathVariable int imageWidth){
		thumbnailer.setUrl(new QUrl(url));
		ImageAttributes attribute = thumbnailer.getImageAttributes().get(0);
		QSize imageSize = attribute.getImageSize();
		imageSize.setHeight(imageHeight);
		imageSize.setWidth(imageWidth);
		imageType = WebAppUtils.validateImageType(imageType);
		attribute.setImageSuffix(imageType);
		if(thumbnailer.makeSnap())
		{
			String generatedImage =  WebAppUtils.resolveImageWebPath(thumbnailer.getImageAttributes().get(0), thumbnailer.getUrl().toString());
			return "redirect:/"+generatedImage+"?rand="+new Date().getTime();
		}
		else
			return "redirect:/";
	}
	
	@RequestMapping("/")
	@ResponseBody()
	public String homePage(HttpServletResponse response){
		response.setContentType("text/html");
		return "<title>SiteGraphThumbnailer</title><h1>Welcome to SiteGraph</h1>";
	}

	@Override
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
		WebAppConstants.IMAGE_ABSOLUTE_PATH = this.servletContext.getRealPath("/") + WebAppConstants.IMAGE_ABSOLUTE_PATH; 
		System.out.println("Default path : "+ WebAppConstants.IMAGE_ABSOLUTE_PATH);
	} 
	public ServletContext getServletContext(){
		return this.servletContext;
	}
}
