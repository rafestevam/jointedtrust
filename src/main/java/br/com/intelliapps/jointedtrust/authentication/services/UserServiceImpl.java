package br.com.intelliapps.jointedtrust.authentication.services;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.intelliapps.jointedtrust.authentication.models.Role;
import br.com.intelliapps.jointedtrust.authentication.models.User;
import br.com.intelliapps.jointedtrust.authentication.repositories.UserRepository;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	public User findByGuid(String guid) {
		return userRepository.findByGuid(guid);
	}
	
	public void save(User user) {
		
		Set<Role> roles = new HashSet<Role>();
		roles.add(new Role(this.getGuid(), "ROLE_ADMIN"));
		
		String encodedPass = encoder.encode(user.getPassword());
		user.setPassword(encodedPass);
		user.setConfpass(encodedPass);
		user.setRoles(roles);
		
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
	public User findByUsername(String username) {
		return userRepository.findByUsername(username);
	}
	
}
