package com.example.demo.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.entity.MemberAccount;
import com.example.demo.service.MemberAccountService;
import com.example.demo.vo.MemberAccountVO;

@Controller
public class MemberAccountController {

	@Autowired
	private MemberAccountService memberAccountService;
	
	private static final Logger logger = LoggerFactory.getLogger(MemberAccountController.class);
	
	@RequestMapping(value = {"/", "/login"}, method = RequestMethod.GET)
	public String login(
			@ModelAttribute MemberAccount memberAccount,
			@ModelAttribute(value = "MESSAGE") String message) {
		
		return "login";
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String doLogin(
			@Valid @ModelAttribute MemberAccount memberAccount,
			HttpSession session, 
			RedirectAttributes redirectAttributes) {
		
		MemberAccountVO memberAccountVO = memberAccountService.login(memberAccount);
		session.setAttribute("member", memberAccountVO);
		logger.info(memberAccountVO.getName() + " 登入成功");
		return "redirect:information";
	}
	
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public String register(@ModelAttribute MemberAccountVO memberAccountVO) {
		
		return "register";
	}
	
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String doRegister(
			@Valid @ModelAttribute MemberAccountVO memberAccountVO,
			RedirectAttributes redirectAttributes) {

		memberAccountService.register(memberAccountVO);
		redirectAttributes.addFlashAttribute("MESSAGE", "註冊成功");
		logger.info(memberAccountVO.getName() + " 註冊成功");
		return "redirect:login";
	}
	
}
