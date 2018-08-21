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
import br.com.intelliapps.jointedtrust.main.components.FileSaverComponent;

@Controller
@RequestMapping("/profile")
public class ProfileController {
	
	@Autowired
	private ProfileService profileService;
	
	@Autowired
	private LoggedUserService loggedUserService;
	
	@Autowired
	private FileSaverComponent fileSaver;
	
//	@Autowired
//	private UserService userService;
	
	@RequestMapping(value="", method=RequestMethod.GET)
	public String profileForm(Model model) {
		
		UserEntity loggedUser = loggedUserService.loggedUser();
		Profile profile = profileService.findByUser(loggedUser);
		model.addAttribute("profile", profile);
		model.addAttribute("username", loggedUser.getUsername());
		
		return "profile";
		
	}
	
	@RequestMapping(value="/update", method=RequestMethod.POST)
	public String saveProfile(@RequestParam MultipartFile file, @ModelAttribute("profile") Profile formProfile, Model model, @ModelAttribute("username") String username) {
		
		String fileAddr = fileSaver.write("profile_files", file, username);
		
		//UserEntity user = userService.findByUsername(username);
		Profile profile = profileService.findByGuid(formProfile.getGuid());
		
		if(formProfile.getName() != null)
			profile.setName(formProfile.getName());
		
		if(formProfile.getLastname() != null)
			profile.setLastname(formProfile.getLastname());
		
		if(formProfile.getPhonenumber() != null)
			profile.setPhonenumber(formProfile.getPhonenumber());
		
		if(formProfile.getBirthday() != null)
			profile.setBirthday(formProfile.getBirthday());
		
		if(fileAddr.isEmpty())
			profile.setPhotoaddress(fileAddr);
		
		profileService.save(profile);
		
		return "redirect:/dashboard";
	}
	
}
