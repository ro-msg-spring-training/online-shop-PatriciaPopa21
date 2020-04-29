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

import lombok.AllArgsConstructor;
import ro.msg.learning.entity.CustomerCredentials;
import ro.msg.learning.service.interfaces.CustomerCredentialsService;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
@Profile("thymeleaf-form")
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private CustomerCredentialsService customerCredentialsService;

	@Autowired
	public void configureGlobal(final AuthenticationManagerBuilder auth) throws Exception {
		final List<CustomerCredentials> allCustomerCredentials = customerCredentialsService.getCredentialsForAllUsers();

		final InMemoryUserDetailsManagerConfigurer<AuthenticationManagerBuilder> userDetailsManager = auth
				.inMemoryAuthentication();

		registerCredentialsWithUserDetailsManager(allCustomerCredentials, userDetailsManager);
	}

	private void registerCredentialsWithUserDetailsManager(final List<CustomerCredentials> allCustomerCredentials,
			final InMemoryUserDetailsManagerConfigurer<AuthenticationManagerBuilder> authenticationConfig) {
		allCustomerCredentials.forEach(credentials -> authenticationConfig.withUser(credentials.getUsername())
				.password(passwordEncoder().encode(credentials.getPassword())).authorities("ROLE_USER"));
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(final HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/resources/**").permitAll().anyRequest().authenticated().and().formLogin()
				.loginPage("/login").permitAll().defaultSuccessUrl("/homepage", true).and().logout().permitAll();
	}
}