package com.mdbs.controller;

//import java.util.HashMap;
import java.util.Map;

//import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mdbs.pojo.User;
import com.mdbs.service.LoginService;

@Controller
@RequestMapping("/")
public class LoginController {
	
	@Autowired
	private LoginService loginService;
	/*
	 * 跳转登录页面
	 */
/*	@RequestMapping("/toLogin.do")
	@ResponseBody
    public Map<String, Object> toLogin(@RequestBody User user){
        Map< String, Object>map=new HashMap<>();
		map.put("status", 1);
		map.put("uid", 1);
        return map;
    }*/
	/*
	 * 登录
	 */
	 @RequestMapping(value = "/login.do",method = RequestMethod.POST)
	 @ResponseBody
	    public Map<String,Object> login(@RequestBody User user){
	
	        Map<String,Object> map = loginService.login(user);
	            return map;
	        }
	
}
