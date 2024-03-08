/**
 * 
 */
package com.dxc.mydxc.auth.conf;

import java.security.KeyPair;
import java.security.interfaces.RSAPublicKey;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;
import org.springframework.web.cors.CorsConfiguration;

import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.KeyUse;
import com.nimbusds.jose.jwk.RSAKey;


/**
 * @author aeltayary
 *
 */

@Configuration
public class CustomAuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	DataSource ds;

	

	@Autowired
	PasswordEncoder passwordEncoder;
	

	/**
	 * Symetric key
	 * @return
	
	@Bean
	JwtAccessTokenConverter accessTokenConverter() {
		JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
		converter.setSigningKey("JWTKey@123");
		return converter;
	}
 */

	/**
	 * This method convert access token to JWT
	 * Then sign this token
	 * @return
	 
	
	@Bean
	@Autowired
	JwtAccessTokenConverter accessTokenConverter(KeyPair keyPair) {
		JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
		converter.setKeyPair(keyPair);
		return converter;
	}
	
	*/
	@Bean
	public JwtAccessTokenConverter accessTokenConverter(KeyPair keyPair) {
	    Map<String, String> customHeaders =
	      Collections.singletonMap("kid", "bael-key-id");
	    return new  JwtCustomHeadersAccessTokenConverter(
	      customHeaders,
	      keyPair());
	}
	
	@Bean
	public KeyPair keyPair() {
		ClassPathResource ksFile =
				  new ClassPathResource("bael-jwt.jks");
		KeyStoreKeyFactory ksFactory =
				  new KeyStoreKeyFactory(ksFile, "bael-pass".toCharArray());
		KeyPair keyPair = ksFactory.getKeyPair("bael-oauth-jwt");
		return keyPair;
	}
	

	@Bean
	public JWKSet jwkSet() {
	    RSAKey.Builder builder = new RSAKey.Builder((RSAPublicKey) keyPair().getPublic())
	      .keyUse(KeyUse.SIGNATURE)
	      .algorithm(JWSAlgorithm.RS256)
	      .keyID("bael-key-id");
	    return new JWKSet(builder.build());
	}
	
	
	

	/**
	 * Client details configuration could be in memory or DB
	 * But it is implemented using DB
	 */

	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {

		clients.jdbc(ds).passwordEncoder(passwordEncoder);

		// System.out.println("password="+enc.encode("secret"));

		/*
		 * clients.inMemory().withClient("client").authorizedGrantTypes("password",
		 * "code", "authorization_code", "implicit", "refresh_token",
		 * "check_token").redirectUris("http://my.redirect.uri")
		 * .secret("{noop}secret").scopes("all");
		 */
	}

	/**
	 * (1) server always deny the below request even the user is authenticated This
	 * method used to secure or to determine the security of the tooken endpoints
	 * the server will respond with 403 forbidden 
	 * (1) POST http://localhost:9999/oauth/token    	Generate token
	 * (2) GET  http://localhost:9999/oauth/token_key    return the key of the token
	 * (3) POST http://localhost:9999/oauth/check_token  check the token value itself
	 */

	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		security.checkTokenAccess("isAuthenticated()");
		security.tokenKeyAccess("isAuthenticated()");

	}

	/**
	 * (3) This is to configure the autehntication manager of our autorization
	 * server which comes from the autowired AuthenticationManager Also , the token
	 * converter (if exists) Also , the token store if exist
	 * Also used to enable CORS
	 */

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		// enable cors for "/oauth/token"
		Map<String, CorsConfiguration> corsConfigMap = new HashMap<>();
	    CorsConfiguration config = new CorsConfiguration();
	    config.setAllowCredentials(true);
	    config.setAllowedOrigins(Collections.singletonList("*"));
	    config.setAllowedMethods(Collections.singletonList("*"));
	    config.setAllowedHeaders(Collections.singletonList("*"));
	    corsConfigMap.put("/oauth/token", config);
	    endpoints.getFrameworkEndpointHandlerMapping()
	            .setCorsConfigurations(corsConfigMap);
		endpoints.accessTokenConverter(accessTokenConverter(keyPair())).authenticationManager(authenticationManager);
	}


	
}
