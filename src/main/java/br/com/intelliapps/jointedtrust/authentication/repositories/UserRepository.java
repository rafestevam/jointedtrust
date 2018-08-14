package br.com.intelliapps.jointedtrust.authentication.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.intelliapps.jointedtrust.authentication.models.User;

public interface UserRepository extends JpaRepository<User, String> {

	public User findByGuid(String guid);
	
	public User findByUsername(String username);
	
	@Query("select case when count(*) > 0 then 'true' else 'false' end from User u where u.username = ?1")
	public boolean existsByUsername(String username);
	
	@Query("select case when count(*) > 0 then 'true' else 'false' end from User u where u.mail = ?1")
	public boolean existsByEmail(String email);
	
}
