package br.com.intelliapps.jointedtrust.authentication.services;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.intelliapps.jointedtrust.authentication.models.Role;
import br.com.intelliapps.jointedtrust.authentication.models.UserEntity;
import br.com.intelliapps.jointedtrust.authentication.repositories.UserEntityRepository;

@Service
@Transactional
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserEntityRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	public UserEntity findByGuid(String guid) {
		return userRepository.findByGuid(guid);
	}
	
	public void save(UserEntity user) {
		
		Set<Role> roles = new HashSet<Role>();
		roles.add(new Role(this.getGuid(), "ROLE_ADMIN"));
		
		if(user.getPassword().length() < 11) {
			String encodedPass = encoder.encode(user.getPassword());
			user.setPassword(encodedPass);
			user.setConfpass(encodedPass);
			user.setRoles(roles);
		}
		
		userRepository.save(user);		
	}
	
	public boolean existsByUsername(String username) {
		return userRepository.existsByUsername(username);
	}

	public boolean existsByEmail(String email) {
		return userRepository.existsByEmail(email);
	}

	private String getGuid() {
		UUID uuid = UUID.randomUUID();
		return uuid.toString();
	}

	@Override
	public UserEntity findByUsername(String username) {
		return userRepository.findByUsername(username);
	}
	
}
