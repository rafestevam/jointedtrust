package br.com.intelliapps.jointedtrust.core.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import br.com.intelliapps.jointedtrust.authentication.models.UserEntity;
import br.com.intelliapps.jointedtrust.authentication.services.LoggedUserService;
import br.com.intelliapps.jointedtrust.core.models.Profile;
import br.com.intelliapps.jointedtrust.core.services.ProfileService;

@Controller
@RequestMapping("/profile")
public class ProfileController {
	
	@Autowired
	private ProfileService profileService;
	
	@Autowired
	private LoggedUserService loggedUserService;
	
	@RequestMapping(value="", method=RequestMethod.GET)
	public String profileForm(Model model) {
		
		UserEntity loggedUser = loggedUserService.loggedUser();
		Profile profile = profileService.findByUser(loggedUser);
		model.addAttribute("profile", profile);
		model.addAttribute("username", loggedUser.getUsername());
		
		return "profile";
		
	}
	
	public String saveProfile(@RequestParam MultipartFile file, @ModelAttribute("profile") Profile profile, Model model) {
		return null;
	}
	
}
