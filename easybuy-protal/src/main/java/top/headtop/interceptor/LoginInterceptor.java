package top.headtop.interceptor;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import top.headtop.utils.CookieUtils;
import top.headtop.pojo.EasyBuyResult;
import top.headtop.pojo.TbUser;
import top.headtop.service.UserService;

public class LoginInterceptor implements HandlerInterceptor{

	@Autowired
	private UserService userService;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String token = CookieUtils.getCookieValue(request, "eb_token");
		EasyBuyResult result = userService.getCookieValue(token);
		TbUser user = (TbUser)result.getData();
		if(user==null){
			StringBuffer url = request.getRequestURL();
			response.sendRedirect("http://localhost:8083/login?redirectUrl="+url);
			return false;
		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
	}

}
