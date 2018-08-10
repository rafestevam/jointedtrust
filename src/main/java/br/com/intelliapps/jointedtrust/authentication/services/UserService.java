package br.com.intelliapps.jointedtrust.authentication.services;

import br.com.intelliapps.jointedtrust.authentication.models.User;

public interface UserService {

	public User findByGuid(String guid);
	
}
