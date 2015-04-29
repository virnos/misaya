package com.misaya.controller;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.misaya.exception.UserException;
import com.misaya.pojo.User;


@Controller
@RequestMapping("/user")
public class UserController {
	private Map<String,User> users = new HashMap<String,User>();
	
	public UserController() {
		users.put("sdy",new User("sdy","123","宋冬野","asss"));
		users.put("ldm",new User("ldm","123","刘东明","asss"));
		users.put("zyp",new User("zyp","123","周云蓬","asss"));
		users.put("zww",new User("zww","123","张玮玮","asss"));
		users.put("wt",new User("wt","123","吴吞","asss"));
	}
	
	@RequestMapping(value="/users",method=RequestMethod.GET)
	public String list(Model model) {
		model.addAttribute("users",users);
		return "user/list";
	}
	
	//链接到add页面时是GET请求，会访问这段代码
	@RequestMapping(value="/add",method=RequestMethod.GET)
	public String add(@ModelAttribute("user")User user) {
		//开启modelDriven
		//model.addAttribute(new User());
		return "user/add";
	}
	
	//在具体添加用户时，是post请求，就访问以下代码
	@RequestMapping(value="/add",method=RequestMethod.POST)
	public String add(@Validated User user,BindingResult br,@RequestParam("attachs")MultipartFile[] attachs,HttpServletRequest req) throws IOException {//一定要紧跟Validate之后写验证结果类
		if(br.hasErrors()) {
			//如果有错误直接跳转到add视图
			return "user/add";
		}
		String realpath = req.getSession().getServletContext().getRealPath("/resources/upload");
		System.out.println(realpath);
		for(MultipartFile attach:attachs) {
			if(attach.isEmpty()) continue;
			File f = new File(realpath+"/"+attach.getOriginalFilename());
			FileUtils.copyInputStreamToFile(attach.getInputStream(),f);
		}
		users.put(user.getUsername(), user);
		return "redirect:/user/users";
	}
	
	@RequestMapping(value="/{username}",method=RequestMethod.GET)
	public String show(@PathVariable String username,Model model) {
		model.addAttribute(users.get(username));
		return "user/show";
	}
	
	@RequestMapping(value="/{username}",method=RequestMethod.GET,params="json")
	@ResponseBody
	public User show(@PathVariable String username) {
		return users.get(username);
	}
	
	@RequestMapping(value="/{username}/update",method=RequestMethod.GET)
	public String update(@PathVariable String username,Model model) {
		model.addAttribute(users.get(username));
		return "user/update";
	}
	
	@RequestMapping(value="/{username}/update",method=RequestMethod.POST)
	public String update(@PathVariable String username,@Validated User user,BindingResult br) {
		if(br.hasErrors()) {
			//如果有错误直接跳转到add视图
			return "user/update";
		}
		users.put(username, user);
		return "redirect:/user/users";
	}
	
	@RequestMapping(value="/{username}/delete",method=RequestMethod.GET)
	public String delete(@PathVariable String username) {
		users.remove(username);
		return "redirect:/user/users";
	}
	
	@RequestMapping(value="/login",method=RequestMethod.POST)
	public String login(String username,String password,HttpSession session) {
		if(!users.containsKey(username)) {
			throw new UserException("用户名不存在");
		}
		User u = users.get(username);
		if(!u.getPassword().equals(password)) {
			throw new UserException("用户密码不正确");
		}
		session.setAttribute("loginUser", u);
		return "redirect:/user/users";
	}
	
	/**
	 * 局部异常处理,仅仅只能处理这个控制器中的异常
	 */
	/*@ExceptionHandler(value={UserException.class})
	public String handlerException(UserException e,HttpServletRequest req) {
		req.setAttribute("e",e);
		return "error";
	}
*/}
