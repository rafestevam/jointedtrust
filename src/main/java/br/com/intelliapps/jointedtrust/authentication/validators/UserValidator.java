package br.com.intelliapps.jointedtrust.authentication.validators;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import br.com.intelliapps.jointedtrust.authentication.models.User;

public class UserValidator implements Validator {
	
	public boolean supports(Class<?> clazz) {
		return User.class.isAssignableFrom(clazz);
	}

	public void validate(Object target, Errors errors) {
		
		//Validating if fields are empty
		ValidationUtils.rejectIfEmpty(errors, "username", "valid.user.error.username.empty", new String[] {});
		ValidationUtils.rejectIfEmpty(errors, "mail", "valid.user.error.mail.empty", new String[] {});
		ValidationUtils.rejectIfEmpty(errors, "password", "valid.user.error.password.empty", new String[] {});
		ValidationUtils.rejectIfEmpty(errors, "confpass", "valid.user.error.confpass.empty", new String[] {});
		
		User user = (User) target;
		
		if(user.getPassword() != null && user.getConfpass() != null) {
			if(!user.getPassword().equals(user.getConfpass())) {
				errors.rejectValue("password", "valid.user.error.passwordconflict");
				errors.rejectValue("confpass", "valid.user.error.passwordconflict");
			}
			if(user.getPassword().length() > 10) {
				errors.rejectValue("password", "valid.user.error.password.size");
				errors.rejectValue("confpass", "valid.user.error.password.size");
			}
			Pattern pattern = Pattern.compile("\\p{Alnum}+");
			Matcher matcher = pattern.matcher(user.getPassword());
			Matcher matcher2 = pattern.matcher(user.getConfpass());
			if(!matcher.matches() && !matcher2.matches()) {
				errors.rejectValue("password", "valid.user.error.password.charInvalid");
				errors.rejectValue("confpass", "valid.user.error.password.charInvalid");
			}
			
			Pattern patternUC = Pattern.compile("[A-Z][^A-Z]*$");
			Matcher matcherUC = patternUC.matcher(user.getPassword());
			Matcher matcherUC2 = patternUC.matcher(user.getConfpass());
			if(!matcherUC.matches() && !matcherUC2.matches()) {
				errors.rejectValue("password", "valid.user.error.password.upperCase");
				errors.rejectValue("confpass", "valid.user.error.password.upperCase");
			}
			
			Pattern patternN = Pattern.compile("[0-9]");
			Matcher matcherN = patternN.matcher(user.getPassword());
			Matcher matcherN2 = patternN.matcher(user.getConfpass());
			if(!matcherN.find() && !matcherN2.find()) {
				errors.rejectValue("password", "valid.user.error.password.numeric");
				errors.rejectValue("confpass", "valid.user.error.password.numeric");
			}
			
		}
		
	}

	

}
