package top.headtop.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import top.headtop.pojo.EasyBuyResult;
import top.headtop.pojo.TbUser;
import top.headtop.service.UserService;

@Controller
public class UserController {
	@Autowired
	private UserService userService;
	
	@RequestMapping("/user/check/{param}/{type}")
	@ResponseBody
	public EasyBuyResult userCheck(@PathVariable String param,@PathVariable String type){
		
		return userService.userCheck(param,type);
	}
	
	@RequestMapping("/user/register")
	@ResponseBody
	public EasyBuyResult userRegister(TbUser uiUser){
		
		return userService.userRegister(uiUser);
	}
	
	@RequestMapping("/user/login")
	@ResponseBody
	public EasyBuyResult userLogin(TbUser uiUser,HttpServletRequest request,HttpServletResponse response){
		
		return userService.userLogin(uiUser,request,response);
	}
	

}
