package br.com.intelliapps.jointedtrust.core.services;

import java.util.List;

import org.springframework.data.domain.Pageable;

import br.com.intelliapps.jointedtrust.core.models.Risk;

public interface RiskService {
	
	public void save(Risk risk);
	
	public Risk findByGuid(String guid);
	
	public boolean existsByRiskId(String riskId);
	
	public List<Risk> findTopRisks(Pageable page);

}
