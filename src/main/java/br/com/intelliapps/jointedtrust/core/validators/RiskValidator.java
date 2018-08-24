package br.com.intelliapps.jointedtrust.core.validators;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import br.com.intelliapps.jointedtrust.core.models.Risk;

public class RiskValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return Risk.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {

		//Validating if fields are empty
		ValidationUtils.rejectIfEmpty(errors, "risk_id", "valid.risk.error.riskid.empty");
		ValidationUtils.rejectIfEmpty(errors, "name", "valid.risk.error.name.empty");
		
	}

}
