package br.com.intelliapps.jointedtrust.authentication.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.intelliapps.jointedtrust.authentication.models.User;

public interface UserRepository extends JpaRepository<User, String> {

	public User findByGuid(String guid);
	
}
