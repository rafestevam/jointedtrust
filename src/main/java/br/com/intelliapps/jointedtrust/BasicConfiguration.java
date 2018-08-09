package br.com.intelliapps.jointedtrust;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.com.intelliapps.jointedtrust.authentication.configs.SecurityConfiguration;
import br.com.intelliapps.jointedtrust.authentication.controllers.LoginController;
import br.com.intelliapps.jointedtrust.authentication.models.User;
import br.com.intelliapps.jointedtrust.main.configs.WebConfig;
import br.com.intelliapps.jointedtrust.main.controllers.HomeController;

@SpringBootApplication(scanBasePackageClasses={
WebConfig.class,
SecurityConfiguration.class,
User.class,
HomeController.class,
LoginController.class
})
public class BasicConfiguration {

	public static void main(String[] args) {
		SpringApplication.run(BasicConfiguration.class, args);
	}
	
}
