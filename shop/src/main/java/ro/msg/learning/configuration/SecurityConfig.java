//package ro.msg.learning.configuration;
//
//import org.springframework.boot.context.properties.EnableConfigurationProperties;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//
//@Configuration
//@EnableWebSecurity
//@EnableConfigurationProperties
//public class SecurityConfig extends WebSecurityConfigurerAdapter {
//
//	@Override
//	protected void configure(final HttpSecurity http) throws Exception {
//
//		http.authorizeRequests()
//		.antMatchers("/**")
//		.permitAll()
//		.and()
//		.csrf().disable();
//	}
//
//}
