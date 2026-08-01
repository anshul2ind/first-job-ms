package com.project.companyms.config;

import com.project.companyms.security.RealmRoleConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(
                        auth ->
                                auth.requestMatchers(HttpMethod.GET , "/companies/**")
                                        .permitAll()

                                        .requestMatchers(HttpMethod.POST, "/companies/**")
                                        .authenticated()

                                        .requestMatchers(HttpMethod.PUT, "/companies/**")
                                        .authenticated()

                                        .requestMatchers(HttpMethod.DELETE, "/companies/**")
                                        .authenticated()

                                        .anyRequest()
                                        .authenticated()

                ).oauth2ResourceServer(oauth ->
                        oauth.jwt( jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter()))
                ).build();

    }

    @Bean
    JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter converter = new JwtGrantedAuthoritiesConverter();
        converter.setAuthoritiesClaimName("realm_access.roles");
        converter.setAuthorityPrefix("");

        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(new RealmRoleConverter());

        return  jwtAuthenticationConverter;
    }
}


