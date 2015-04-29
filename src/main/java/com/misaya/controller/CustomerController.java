package com.misaya.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/customer")
public class CustomerController {

	//链接到add页面时是GET请求，会访问这段代码
	@RequestMapping(value="/add", method=RequestMethod.GET)
	public String add(ModelMap model) {

		return "customer-add";
	}

	//链接到add页面时是GET请求，会访问这段代码
	@RequestMapping(value="/edit", method=RequestMethod.GET)
	public String edit(@PathVariable String path, ModelMap model) {
		
		return "customer-edit";
	}
	
	//链接到add页面时是GET请求，会访问这段代码
	@RequestMapping(value="/index", method=RequestMethod.GET)
	public String index(@PathVariable String path, ModelMap model) {
		
		return "customer-index";
	}

}
