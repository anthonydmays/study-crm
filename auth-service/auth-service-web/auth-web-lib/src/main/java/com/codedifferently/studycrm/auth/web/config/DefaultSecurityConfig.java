package com.codedifferently.studycrm.auth.web.config;

import com.codedifferently.studycrm.auth.domain.User;
import com.codedifferently.studycrm.auth.domain.UserAuthority;
import com.codedifferently.studycrm.auth.domain.UserRepository;
import com.codedifferently.studycrm.auth.web.security.FederatedIdentityConfigurer;
import com.codedifferently.studycrm.auth.web.security.RepositoryUserDetailsService;

import java.util.HashMap;
import java.util.Arrays;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.session.HttpSessionEventPublisher;

/**
 * @author Steve Riesenberg
 * @since 0.2.3
 */
@EnableWebSecurity
@Configuration(proxyBeanMethods = false)
public class DefaultSecurityConfig {

	// @formatter:off
	@Bean
	public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
		FederatedIdentityConfigurer federatedIdentityConfigurer = new FederatedIdentityConfigurer();
		http
			.authorizeHttpRequests(authorize ->
				authorize
					.requestMatchers("/v3/api-docs").permitAll()
					.requestMatchers("/actuator/**").permitAll()
					.requestMatchers("/assets/**").permitAll()
					.requestMatchers("/webjars/**").permitAll()
					.requestMatchers("/login").permitAll()
					.requestMatchers("/users").permitAll()
					.anyRequest().authenticated()
			)
			.formLogin(Customizer.withDefaults())
			.apply(federatedIdentityConfigurer);
		return http.build();
	} 

    @Bean
    public AuthenticationProvider authenticationProvider(RepositoryUserDetailsService repositoryUserDetailsService){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(repositoryUserDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;

    }

	@Bean
	public CommandLineRunner loadData(UserRepository repository) {
		return args -> {
			User user = repository.findByUsername("user");
			if (user != null) {
				return;
			}
			var newUser = User.builder()
				.username("user")
				.password(passwordEncoder().encode("password"))
				.email("root@localhost")
				.firstName("Root")
				.lastName("User")
				.build();
			// newUser.setAuthorities(Arrays.asList(
			// 	UserAuthority.builder()
			// 		.user(newUser)
			// 		.authority("USER")
			// 		.build()));

			repository.save(newUser);
		};
	}

  @Bean
  public PasswordEncoder passwordEncoder() {
	var defaultEncoder = new BCryptPasswordEncoder();
    var encoders = new HashMap<String, PasswordEncoder>();
    encoders.put("bcrypt", defaultEncoder);

    DelegatingPasswordEncoder passworEncoder = new DelegatingPasswordEncoder("bcrypt", encoders);
    passworEncoder.setDefaultPasswordEncoderForMatches(defaultEncoder);

    return passworEncoder;
  }

	@Bean
	public SessionRegistry sessionRegistry() {
		return new SessionRegistryImpl();
	}

	@Bean
	public HttpSessionEventPublisher httpSessionEventPublisher() {
		return new HttpSessionEventPublisher();
	}

}
