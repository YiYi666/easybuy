package top.headtop.service;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import top.headtop.pojo.EasyBuyResult;
import top.headtop.pojo.TbUser;

public interface UserService {

	EasyBuyResult userCheck(String param, String type);
	
	EasyBuyResult userRegister(TbUser uiUser);

	EasyBuyResult userLogin(TbUser uiUser,HttpServletRequest request,HttpServletResponse response);

	EasyBuyResult getCookieValue(String token);


}
