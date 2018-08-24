package br.com.intelliapps.jointedtrust.core.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.intelliapps.jointedtrust.core.models.Risk;

public interface RiskRepository extends JpaRepository<Risk, String> {

	public Risk findByGuid(String guid);
	
	@Query("select case when count(*) > 0 then 'true' else 'false' end from Risk r where r.risk_id = ?1")
	public boolean existsByRiskId(String riskId);
	
}
