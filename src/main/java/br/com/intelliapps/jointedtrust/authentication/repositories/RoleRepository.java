package br.com.intelliapps.jointedtrust.authentication.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.intelliapps.jointedtrust.authentication.models.Role;

public interface RoleRepository extends JpaRepository<Role, String> {

}
