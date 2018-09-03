package br.com.intelliapps.jointedtrust.authentication.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity(debug=false)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private UserDetailsService userDetailsService;
		
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
//		RequestMatcher csrfMatcher = new RequestMatcher() {
//			
//			private Pattern allowedMethods = Pattern.compile("^(GET|HEAD|TRACE|OPTIONS)$"); 
//			
//			private AntPathRequestMatcher[] reqMatchers = {
//				new AntPathRequestMatcher("/risk/create")	
//			};
//			
//			@Override
//			public boolean matches(HttpServletRequest request) {
//				
//				if(allowedMethods.matcher(request.getMethod()).matches())
//					return false;
//				
//				for(AntPathRequestMatcher rm : reqMatchers) {
//					if(rm.matches(request))
//						return false;
//				}
//				return true;
//			}
//		};
		
		
		String[] resourceFiles = 
			{"/resources/**",
			 "/static/**"
			};
		http
			.authorizeRequests()
				.antMatchers(resourceFiles).permitAll()
				.antMatchers("/").permitAll()
				.antMatchers("/account/**").permitAll()
				.antMatchers("/dashboard").authenticated()
				.antMatchers("/profile/**").authenticated()
				.antMatchers("/testdr").authenticated()
			.and()
			.formLogin()
				.loginPage("/login").permitAll()
				.defaultSuccessUrl("/dashboard");
//			.and()
//			.csrf()
//				.requireCsrfProtectionMatcher(csrfMatcher);
	}
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/resources/**","/static/**");
	}
	
	@Bean
	public BCryptPasswordEncoder passEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Autowired
	protected void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passEncoder());
	}
	
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
	

}
