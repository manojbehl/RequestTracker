package com.requestTracker.controller;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.requestTracker.domain.Login;
import com.requestTracker.domain.User;
import com.requestTracker.service.IAdminService;
import com.requestTracker.service.ILoginService;

@Controller

public class IndexController {

	@Autowired
	private ILoginService iloginservice;
	
	@Autowired
	private IAdminService iadminService;


	private static final Logger logger = Logger.getLogger(IndexController.class);

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView rootController1() {
		ModelAndView modelView = new ModelAndView();
		if (logger.isDebugEnabled()) {
			logger.debug("logs enable");
		}

		System.out.println("Root Controller");
		modelView.setViewName("login");
		return modelView;
	}

	@RequestMapping(value = "/validLogin", method = RequestMethod.POST)
	public ModelAndView login(Login login , HttpSession session,RedirectAttributes redirectAttrs) {
		ModelAndView modelView = new ModelAndView();
		User userdetail = iloginservice.validateLogin(login);
		if (userdetail != null) {
			session.setAttribute("userSessionObject", userdetail);

			if (userdetail.getRole().equals("admin")) {
				modelView.addObject("PartnerList", iadminService.getAllPartner());
				modelView.setViewName("dashboard");

//				modelView.setViewName("redirect:/getPartnerList");
				return modelView;
			} else {
				int partnerId = userdetail.getPartnerId();
				modelView.addObject("PartnerAppIpInfo", iadminService.getPartnerApps(partnerId));
				modelView.setViewName("partnerDashboard");
//
//				modelView.setViewName("redirect:/partnerDashboard");
				return modelView;
			}
		}
		else{
			//redirectAttrs.addFlashAttribute("Errormsg", " Sorry, you entered an incorrect email address or password. ");
			modelView.addObject("Errormsg", " Sorry, you entered an incorrect email address or password. ");
		}
		
		modelView.setViewName("login");

//		modelView.setViewName("redirect:/");
		return modelView;

	}

	@GetMapping(value = "/logout")
	public ModelAndView logout(HttpSession session,RedirectAttributes redirectAttrs) {
				ModelAndView modelView = new ModelAndView();
			if(session!=null){
			session.removeAttribute("userSessionObject");
			session.invalidate();
			}
			redirectAttrs.addFlashAttribute("Errormsg", " You Have Logged Out Successfully ");
			modelView.addObject("Errormsg", " You Have Logged Out Successfully ");
			modelView.setViewName("login");

//			modelView.setViewName("redirect:/");
		return modelView;

	}
}
