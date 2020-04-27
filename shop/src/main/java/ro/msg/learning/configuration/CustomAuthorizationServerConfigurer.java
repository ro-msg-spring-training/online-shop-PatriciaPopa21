//package ro.msg.learning.configuration;
//
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
//import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
//import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
//import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
//import org.springframework.stereotype.Component;
//
//@Component
//public class CustomAuthorizationServerConfigurer extends AuthorizationServerConfigurerAdapter {
//
//	AuthenticationManager authenticationManager;
//
//	public CustomAuthorizationServerConfigurer(final AuthenticationConfiguration authenticationConfiguration)
//			throws Exception {
//		authenticationManager = authenticationConfiguration.getAuthenticationManager();
//	}
//
//	@Override
//	public void configure(final ClientDetailsServiceConfigurer clients) throws Exception {
//		clients.inMemory().withClient("patris11").authorizedGrantTypes("client_credentials")
//				.secret("{noop}secretpassword").scopes("all");
//	}
//
//	@Override
//	public void configure(final AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
//		endpoints.authenticationManager(authenticationManager);
//	}
//}