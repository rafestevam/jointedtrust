package br.com.intelliapps.jointedtrust.authentication.validators;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import br.com.intelliapps.jointedtrust.authentication.models.User;

public class UserValidator implements Validator {
	
	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	private Locale locale;

	public boolean supports(Class<?> clazz) {
		return User.class.isAssignableFrom(clazz);
	}

	public void validate(Object target, Errors errors) {
		
		String usernameField = messageSource.getMessage("field.username", new String[] {}, locale);
		String passwordField = messageSource.getMessage("field.password", new String[] {}, locale);
		String emailField = messageSource.getMessage("field.email", new String[] {}, locale);
		
		//Validating if fields are empty
		ValidationUtils.rejectIfEmpty(errors, "username", "valid.common.error.empty", new String[] {usernameField});
		ValidationUtils.rejectIfEmpty(errors, "email", "valid.common.error.empty", new String[] {emailField});
		ValidationUtils.rejectIfEmpty(errors, "password", "valid.common.error.empty", new String[] {passwordField});
		ValidationUtils.rejectIfEmpty(errors, "confpass", "valid.common.error.empty", new String[] {passwordField});
		
		
	}

	

}
