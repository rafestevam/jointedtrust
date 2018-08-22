package br.com.intelliapps.jointedtrust.core.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.intelliapps.jointedtrust.core.models.Risk;

public interface RiskRepository extends JpaRepository<Risk, String> {

	public Risk findByGuid(String guid);
	
}
