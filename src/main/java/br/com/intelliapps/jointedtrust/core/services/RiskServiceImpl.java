package br.com.intelliapps.jointedtrust.core.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.intelliapps.jointedtrust.core.models.Risk;
import br.com.intelliapps.jointedtrust.core.repositories.RiskRepository;

@Service
public class RiskServiceImpl implements RiskService{

	@Autowired
	private RiskRepository riskRepository;
	
	public void save(Risk risk) {
		riskRepository.save(risk);
	}

	public Risk findByGuid(String guid) {
		return riskRepository.findByGuid(guid);
	}

	public boolean existsByRiskId(String riskId) {
		return riskRepository.existsByRiskId(riskId);
	}

	public List<Risk> findTopRisks(Pageable page) {
		return riskRepository.findTopRisks(page);
	}

}
