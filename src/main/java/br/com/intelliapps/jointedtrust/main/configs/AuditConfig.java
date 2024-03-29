package br.com.intelliapps.jointedtrust.main.configs;

import java.util.Optional;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Configuration
@EnableJpaAuditing
public class AuditConfig {
	
	@Bean
	public AuditorAware<String> createAuditorAware(){
		return new SecurityAuditor();
	}
	
	@Bean
	public AuditingEntityListener createAuditingListener() {
		return new AuditingEntityListener();
	}
	
	public static class SecurityAuditor implements AuditorAware<String>{

		@Override
		public Optional<String> getCurrentAuditor() {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			String username = auth.getName();
			return Optional.of(username);
		}
		
	}

}
