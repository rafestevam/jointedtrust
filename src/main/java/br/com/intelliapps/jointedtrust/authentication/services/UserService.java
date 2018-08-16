package br.com.intelliapps.jointedtrust.authentication.services;

import br.com.intelliapps.jointedtrust.authentication.models.UserEntity;

public interface UserService {
	
	public void save(UserEntity user);

	public UserEntity findByGuid(String guid);
	
	public UserEntity findByUsername(String username);
	
	public boolean existsByUsername(String username);
	
	public boolean existsByEmail(String email);
	
}
