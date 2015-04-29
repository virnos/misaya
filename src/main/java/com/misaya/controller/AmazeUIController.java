package com.misaya.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AmazeUIController {

	//链接到add页面时是GET请求，会访问这段代码
	@RequestMapping(value="/{path}")
	public String add(@PathVariable String path, ModelMap model) {
		return path;
	}

}
