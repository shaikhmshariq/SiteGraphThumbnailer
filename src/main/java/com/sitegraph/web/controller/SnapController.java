/**
 * 
 */
package com.sitegraph.web.controller;

import java.util.Date;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.ServletContextAware;

import com.sitegraph.core.SiteGraphThumbnailer;
import com.sitegraph.core.util.WebAppConstants;
import com.trolltech.qt.core.QUrl;

@Controller
public class SnapController implements ServletContextAware{

	@Autowired
	private SiteGraphThumbnailer thumbnailer;
	private ServletContext servletContext;
	
	@RequestMapping(method=RequestMethod.GET, value="/click")
	public String makeImage(@RequestParam("URL") String url){
		thumbnailer.setUrl(new QUrl(url));
		if(thumbnailer.makeSnap())
		{
			//String generatedImage =  WebAppUtils.resolveImageName(thumbnailer.getImageAttributes().get(0), thumbnailer.getUrl().toString());
			return "redirect:/image.png?rand="+new Date().getTime();
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
		WebAppConstants.DEFAULT_IMAGE_ABSOLUTH_PATH = this.servletContext.getRealPath("/") + WebAppConstants.DEFAULT_IMAGE_ABSOLUTH_PATH; 
		System.out.println("Default path : "+ WebAppConstants.DEFAULT_IMAGE_ABSOLUTH_PATH);
	} 
	public ServletContext getServletContext(){
		return this.servletContext;
	}
}
