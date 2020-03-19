package ro.msg.learning.configuration;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.provisioning.InMemoryUserDetailsManagerConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.RequiredArgsConstructor;
import ro.msg.learning.entity.CustomerCredentials;
import ro.msg.learning.service.interfaces.CustomerCredentialsService;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@Profile("form-based")
public class FormBasedWebSecurityConfiguration extends WebSecurityConfigurerAdapter {
	private final CustomerCredentialsService customerCredentialsService;

	@Autowired
	public void configureGlobal(final AuthenticationManagerBuilder auth) throws Exception {
		final List<CustomerCredentials> allCustomerCredentials = customerCredentialsService.getCredentialsForAllUsers();

		final InMemoryUserDetailsManagerConfigurer<AuthenticationManagerBuilder> authenticationConfig = auth.inMemoryAuthentication();

		allCustomerCredentials.forEach(credentials -> authenticationConfig
				.withUser(credentials.getUsername())
				.password(passwordEncoder().encode(credentials.getPassword()))
				.authorities("ROLE_USER"));
	}

	@Override
	protected void configure(final HttpSecurity http) throws Exception {
		http.authorizeRequests()
		.antMatchers("/securityNone").permitAll()
		.anyRequest().authenticated()
		.and()
		.formLogin();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}