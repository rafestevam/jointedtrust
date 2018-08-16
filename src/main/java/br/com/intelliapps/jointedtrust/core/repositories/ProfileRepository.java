package br.com.intelliapps.jointedtrust.core.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.intelliapps.jointedtrust.authentication.models.UserEntity;
import br.com.intelliapps.jointedtrust.core.models.Profile;

public interface ProfileRepository extends JpaRepository<Profile, String>{

	public Profile findByGuid(String guid);
	
	public Profile findByUser(UserEntity user);
	
}
