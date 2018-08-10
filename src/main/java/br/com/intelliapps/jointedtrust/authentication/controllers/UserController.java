package br.com.intelliapps.jointedtrust.authentication.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.intelliapps.jointedtrust.authentication.models.User;
import br.com.intelliapps.jointedtrust.authentication.validators.UserValidator;

@Controller
@RequestMapping("/account")
public class UserController {
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.addValidators(new UserValidator());
	}
	
	@RequestMapping(value="/signup", method=RequestMethod.GET)
	public String signupPage() {
		return "sign-up";
	}
	
	public String createUser(@Valid User user, BindingResult binding, RedirectAttributes rAttr, HttpServletRequest req) {
		
		
		
		return null;
	}
	
}
