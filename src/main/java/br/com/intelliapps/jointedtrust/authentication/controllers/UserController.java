package br.com.intelliapps.jointedtrust.authentication.controllers;

import java.util.Locale;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.intelliapps.jointedtrust.authentication.models.User;
import br.com.intelliapps.jointedtrust.authentication.services.UserService;
import br.com.intelliapps.jointedtrust.authentication.validators.UserValidator;

@Controller
@RequestMapping("/account")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private MailSender mailSender;
	
	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	private Locale locale;
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.addValidators(new UserValidator());
	}
	
	@RequestMapping(value="/signup", method=RequestMethod.GET)
	public String signupPage(User user) {
		return "sign-up";
	}
	
	@RequestMapping(value="/signup", method=RequestMethod.POST)
	public String createUser(@Valid User user, BindingResult binding, RedirectAttributes rAttr, HttpServletRequest req) {
		
		String usernameField = messageSource.getMessage("field.user.username", new String[] {}, locale);
		String emailField = messageSource.getMessage("field.user.email", new String[] {}, locale);
		
		if(binding.hasErrors())
			return this.signupPage(user);
		
		if(userService.existsByEmail(user.getMail())){
			binding.rejectValue("mail", "valid.common.error.exists", new String[]{emailField}, null);
			return this.signupPage(user);
		}
		
		if(userService.existsByUsername(user.getUsername())) {
			binding.rejectValue("username", "valid.common.error.exists", new String[]{usernameField}, null);
			return this.signupPage(user);
		}
		
		user.setGuid(this.getGuid());
		userService.save(user);

		String appUrl = req.getScheme() + "://" + req.getServerName();
		String messageSubject = messageSource.getMessage("mail.message.registration.subject", new String[] {}, locale);
		String messageText = messageSource.getMessage("mail.message.registration.text", new String[] {appUrl, user.getGuid()}, locale);
		String messageFrom = messageSource.getMessage("mail.message.registration.from", new String[] {}, locale);
		
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setTo(user.getMail());
		mailMessage.setSubject(messageSubject);
		mailMessage.setText(messageText);
		mailMessage.setFrom(messageFrom);
		
		mailSender.send(mailMessage);
		
		String registrationMessage = messageSource.getMessage("notification.user.create.success", new String[] {}, locale);
		rAttr.addFlashAttribute("registrationMessage", registrationMessage);
			
		return "redirect:/login";
	}
	
	@RequestMapping(value="/confirm", method=RequestMethod.GET)
	public String showConfirmationPage(Model model, @RequestParam("token") String guid) {
		
		User user = userService.findByGuid(guid);
		
		model.addAttribute("nameofuser", user.getName() + 
				(user.getLastname() != null ? " " + user.getLastname() : "") );
		
		model.addAttribute(user);
		
		return "confirmation";
	}
	
	@RequestMapping(value="/confirm", method=RequestMethod.POST)
	public String confirmUser(User userToken, RedirectAttributes rAttr, Model model) {
		
		User user = userService.findByGuid(userToken.getGuid());
		user.setActivated(true);
		
		userService.save(user);
		
		String registrationMessage = messageSource.getMessage("notification.user.activate.success", new String[] {}, locale);
		rAttr.addFlashAttribute("registrationMessage", registrationMessage);
		
		return "redirect:/login";
	}
	
	private String getGuid() {
		UUID uuid = UUID.randomUUID();
		return uuid.toString();
	}
	
}
