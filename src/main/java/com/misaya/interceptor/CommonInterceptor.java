package com.misaya.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class CommonInterceptor implements HandlerInterceptor {

	// 利用正则映射到需要拦截的路径
	private String mappingURL;
	
	public void setMappingURL(String mappingURL) {
		this.mappingURL = mappingURL;
	}

	/**
	 * 在业务处理器处理请求之前被调用 如果返回false 从当前的拦截器往回执行所有拦截器的afterCompletion(),再退出拦截器链
	 * 
	 * 如果返回true 执行下一个拦截器,直到所有的拦截器都执行完毕 再执行被拦截的Controller 然后进入拦截器链,
	 * 从最后一个拦截器往回执行所有的postHandle() 接着再从最后一个拦截器往回执行所有的afterCompletion()
	 */
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("==============执行顺序: 1、preHandle================");
		String url = request.getRequestURL().toString();
		String mainHeading = (String)request.getServletContext().getAttribute("mainHeading");
		String subHeading = (String)request.getServletContext().getAttribute("subHeading");
		System.out.println(mainHeading + " " + subHeading);
		if (mappingURL == null || url.matches(mappingURL)) {
			//request.getRequestDispatcher("/msg.jsp").forward(request, response);
			//return false;
		}
		return true;
	}


	// 在业务处理器处理请求执行完成后,生成视图之前执行的动作
	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("==============执行顺序: 2、postHandle================");
		String url = request.getRequestURL().toString();
		System.out.println("url = " + url);
		request.setAttribute("resourcesPath", request.getContextPath()+"/resources/AmazeUI/");
		System.out.println(request.getContextPath()+"/resources/AmazeUI/");
		String mainHeading = (String)request.getServletContext().getAttribute("mainHeading");
		String subHeading = (String)request.getServletContext().getAttribute("subHeading");
		System.out.println(mainHeading + " " + subHeading);
	}

	/**
	 * 在DispatcherServlet完全处理完请求后被调用
	 * 
	 * 当有拦截器抛出异常时,会从当前拦截器往回执行所有的拦截器的afterCompletion()
	 */
	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		System.out.println("==============执行顺序: 3、afterCompletion================");
		request.setAttribute("resourcesPath", request.getContextPath()+"/resources/AmazeUI/");
		String mainHeading = (String)request.getServletContext().getAttribute("mainHeading");
		String subHeading = (String)request.getServletContext().getAttribute("subHeading");
		System.out.println(mainHeading + " " + subHeading);
	}

}
