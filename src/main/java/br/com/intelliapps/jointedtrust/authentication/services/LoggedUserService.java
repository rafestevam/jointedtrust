package br.com.intelliapps.jointedtrust.authentication.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import br.com.intelliapps.jointedtrust.authentication.models.UserEntity;

@Service
public class LoggedUserService {
	
	@Autowired
	private UserService userService;
	
	public UserEntity loggedUser() {
		User loggedUser = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return userService.findByUsername(loggedUser.getUsername());
	}

}
