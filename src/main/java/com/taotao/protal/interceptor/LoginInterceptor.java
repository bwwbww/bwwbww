package com.taotao.protal.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.taotao.common.utils.CookieUtils;
import com.taotao.pojo.TbUser;
import com.taotao.protal.service.UserService;
import com.taotao.protal.service.impl.UserServiceImpl;

public class LoginInterceptor implements HandlerInterceptor {

	@Autowired
	private UserServiceImpl userService;
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// 执行handler前
		//判断用户是否登录
		//从cookie中获取token
		String token = CookieUtils.getCookieValue(request, "TT_TOKEN");
		//根据token获取信息
		TbUser user = userService.getUserByToken(token);
		//如果用户信息不存在 转到登录界面 且吧用户的请求参数传过去
		if (null==user) {
			response.sendRedirect(userService.SSO_BASE_URL+userService.SSO_USER_LOGIN+"?redirect="+request.getRequestURL());
		return false;
		}
		//拦截到用户信息 将其放入到request中
		request.setAttribute("user", user);
		//取到用户信息 放行
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// 执行handler之后 返回modelandview之前

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// 返回modelandview之后

	}

}
