package com.misaya.tools;

import javax.servlet.ServletContext;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.context.ServletContextAware;

public class WebTool implements ServletContextAware, ApplicationContextAware {

	private static WebTool instance = new WebTool();
	
	private ServletContext application;
	
	private ApplicationContext springContext;
	
	private String mainHeading;
	
	private String subHeading;
	
	public static WebTool getInstance()
	{
		return instance;
	}

	public void setMainHeading(String mainHeading) {
		this.mainHeading = mainHeading;
	}

	public void setSubHeading(String subHeading) {
		this.subHeading = subHeading;
	}

	@Override
	public void setServletContext(ServletContext servletContext) {
		this.application = servletContext;
		initParam();
	    System.out.println("项目应用的绝对路径为："+servletContext.getRealPath("/") );

	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		// TODO Auto-generated method stub
		this.springContext = applicationContext;
	    System.out.println("springContext = " + springContext.getDisplayName());
	}

	public void initParam()
	{
		application.setAttribute("mainHeading", mainHeading);
		application.setAttribute("subHeading", subHeading);
	}

}
