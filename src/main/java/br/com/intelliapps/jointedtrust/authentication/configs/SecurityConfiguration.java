package br.com.intelliapps.jointedtrust.authentication.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		String[] resourceFiles = 
			{"/resources/**",
			 "/static/**"
			};
		
		http.authorizeRequests()
			.antMatchers(resourceFiles).permitAll()
			.antMatchers("/").permitAll()
		.and()
			.formLogin().loginPage("/login").permitAll();
	}
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/resources/**","/static/**");
	}

}
