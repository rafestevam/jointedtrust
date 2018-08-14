package br.com.intelliapps.jointedtrust.authentication.services;

import br.com.intelliapps.jointedtrust.authentication.models.User;

public interface UserService {
	
	public void save(User user);

	public User findByGuid(String guid);
	
	public User findByUsername(String username);
	
	public boolean existsByUsername(String username);
	
	public boolean existsByEmail(String email);
	
}
