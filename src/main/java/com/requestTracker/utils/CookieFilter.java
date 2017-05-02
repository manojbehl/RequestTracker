package com.requestTracker.utils;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class CookieFilter implements Filter {

	public void destroy() {

	}

	public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain arg2)
			throws IOException, ServletException {
		System.out.println("inside doFilter");

		HttpServletRequest request = (HttpServletRequest) arg0;
		HttpServletResponse response = (HttpServletResponse) arg1;
		HttpSession httpSession = request.getSession();
		if (request.getParameter("JSESSIONID") != null) {
			Cookie cookie = new Cookie("JSESSIONID", request.getParameter("JSESSIONID"));
			response.addCookie(cookie);
		} else {
			String sessionId = httpSession.getId();
			Cookie cookie = new Cookie("JSESSIONID", sessionId);
			response.addCookie(cookie);

		}

		arg2.doFilter(arg0, arg1);
	}

	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}

}