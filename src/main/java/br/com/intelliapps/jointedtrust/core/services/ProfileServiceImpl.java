package br.com.intelliapps.jointedtrust.core.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.intelliapps.jointedtrust.authentication.models.UserEntity;
import br.com.intelliapps.jointedtrust.core.models.Profile;
import br.com.intelliapps.jointedtrust.core.repositories.ProfileRepository;

@Service
public class ProfileServiceImpl implements ProfileService {
	
	@Autowired
	private ProfileRepository profileRepository;
	
	@Transactional
	public void save(Profile profile) {
		profileRepository.save(profile);
	}

	public Profile findByGuid(String guid) {
		return profileRepository.findByGuid(guid);
	}

	public Profile findByUser(UserEntity user) {
		return profileRepository.findByUser(user);
	}
	
}
