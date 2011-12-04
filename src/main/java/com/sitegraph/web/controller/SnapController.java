/**
 * 
 */
package com.sitegraph.web.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sitegraph.core.FileSystemThumbnailer;
import com.sitegraph.core.SiteGraphThumbnailer;

@Controller
public class SnapController {

	@Autowired
	private SiteGraphThumbnailer thumbnailer;
	
	@RequestMapping("/click")
	@ResponseBody
	public String makeImage(){
		//thumbnailer.makeSnap();
		return "done";
	}
	
	@RequestMapping("pinch")
	@ResponseBody
	public String doPinch(){
		System.out.println(thumbnailer.toString());
		new FileSystemThumbnailer("http://www.google.com").makeSnap();
		new FileSystemThumbnailer("http://www.facebook.com").makeSnap();
	 //boolean vlaue = thumbnailer.makeSnap();
	 return "Done";
	}
	
	@RequestMapping("/")
	@ResponseBody()
	public String homePage(HttpServletResponse response){
		response.setContentType("text/html");
		return "Welcome to SiteGraph";
	} 
}
