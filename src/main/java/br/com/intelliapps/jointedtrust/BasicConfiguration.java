package br.com.intelliapps.jointedtrust;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.com.intelliapps.jointedtrust.authentication.configs.SecurityConfiguration;
import br.com.intelliapps.jointedtrust.authentication.controllers.LoginController;
import br.com.intelliapps.jointedtrust.authentication.models.UserEntity;
import br.com.intelliapps.jointedtrust.authentication.repositories.UserEntityRepository;
import br.com.intelliapps.jointedtrust.authentication.services.UserService;
import br.com.intelliapps.jointedtrust.core.controllers.DashboardController;
import br.com.intelliapps.jointedtrust.core.controllers.ProfileController;
import br.com.intelliapps.jointedtrust.core.converters.StringToDateConverter;
import br.com.intelliapps.jointedtrust.core.models.Profile;
import br.com.intelliapps.jointedtrust.core.repositories.ProfileRepository;
import br.com.intelliapps.jointedtrust.core.services.ProfileService;
import br.com.intelliapps.jointedtrust.main.components.FileSaverComponent;
import br.com.intelliapps.jointedtrust.main.configs.WebConfig;
import br.com.intelliapps.jointedtrust.main.controllers.HomeController;

@SpringBootApplication(scanBasePackageClasses={
//Configurations
WebConfig.class,
SecurityConfiguration.class,

//Models
UserEntity.class,
Profile.class,

//Repositories
UserEntityRepository.class,
ProfileRepository.class,

//Services
UserService.class,
ProfileService.class,

//Components
FileSaverComponent.class,

//Converters
StringToDateConverter.class,

//Controllers
HomeController.class,
LoginController.class,
DashboardController.class,
ProfileController.class
})
public class BasicConfiguration {

	public static void main(String[] args) {
		SpringApplication.run(BasicConfiguration.class, args);
	}
	
}
