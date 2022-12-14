package com.nhnacademy.edu.minidooraygateway.config;


import com.nhnacademy.edu.minidooraygateway.service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.oauth2.client.CommonOAuth2Provider;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.InMemoryOAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity(debug = true)
@EnableConfigurationProperties(SecurityProperties.class)
@RequiredArgsConstructor
@Configuration
public class SecurityConfig {

    private final SecurityProperties securityProperties;

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests()
                .antMatchers("/redirect-index").authenticated()
                .anyRequest().permitAll();

        http.requiresChannel()
                .anyRequest().requiresInsecure();

        http.oauth2Login()
                .clientRegistrationRepository(clientRegistrationRepository())
                .authorizedClientService(authorizedClientService());

        http.formLogin()
                .usernameParameter("id")
                .passwordParameter("pwd")
                .loginProcessingUrl("/login")
                .loginPage("/auth/login");

        http.logout()
                .invalidateHttpSession(true)
                .deleteCookies("SESSION");

        http.csrf().disable();

        http.sessionManagement()
                .sessionFixation()
                .none();

        http.exceptionHandling()
                .accessDeniedPage("/error/403");
        return http.build();
    }


    @Bean
    public AuthenticationProvider authenticationProvider(CustomUserDetailsService customUserDetailsService) {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(customUserDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public ClientRegistrationRepository clientRegistrationRepository() {
        return new InMemoryClientRegistrationRepository(github());
    }

    @Bean
    public OAuth2AuthorizedClientService authorizedClientService() {
        return new InMemoryOAuth2AuthorizedClientService(clientRegistrationRepository());
    }

    private ClientRegistration github() {
        return CommonOAuth2Provider.GITHUB.getBuilder("github")
                .userNameAttributeName("email")
                .clientId(securityProperties.getClientId())
                .clientSecret(securityProperties.getClientSecret())
                .redirectUri("{baseUrl}/login/oauth2/code/github")
                .scope(securityProperties.getScope())
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .build();
    }

}
