package br.com.intelliapps.jointedtrust.authentication.services;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.intelliapps.jointedtrust.authentication.models.User;
import br.com.intelliapps.jointedtrust.authentication.repositories.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;
	
	@Transactional(readOnly=true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		boolean enabled = true;
		boolean accountNonExpired = true;
		boolean credentialsNonExpired = true;
		boolean accountNonLocked = true;
		
		User user = userRepository.findByUsername(username);
		if(user == null)
			throw new UsernameNotFoundException(username);
		
		Set<GrantedAuthority> roles = new HashSet<GrantedAuthority>();
		user.getRoles()
			.forEach(role -> roles.add(new SimpleGrantedAuthority(role.getName())));
		
		if(!user.getActivated())
			enabled = false;
		
		if(user.getLocked())
			accountNonLocked = false;
		
		return new org.springframework.security.core.userdetails.
				User(user.getUsername(), user.getPassword(), enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, roles);
		
	}

}
