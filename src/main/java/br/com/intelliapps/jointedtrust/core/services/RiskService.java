package br.com.intelliapps.jointedtrust.core.services;

import br.com.intelliapps.jointedtrust.core.models.Risk;

public interface RiskService {
	
	public void save(Risk risk);
	
	public Risk findByGuid(String guid);
	
	public boolean existsByRiskId(String riskId);

}
