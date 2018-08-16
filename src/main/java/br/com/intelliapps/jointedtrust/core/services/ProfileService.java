package br.com.intelliapps.jointedtrust.core.services;

import br.com.intelliapps.jointedtrust.authentication.models.UserEntity;
import br.com.intelliapps.jointedtrust.core.models.Profile;

public interface ProfileService {
	
	public void save(Profile profile);
	
	public Profile findByGuid(String guid);
	
	public Profile findByUser(UserEntity user);

}
