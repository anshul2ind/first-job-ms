package com.project.companyms.security;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class SecurityUtils {
    public static String currentSubject() {
       var securityContext =  SecurityContextHolder
                .getContext();
        Jwt jwt = (Jwt) Objects.requireNonNull(securityContext
                        .getAuthentication())
                .getPrincipal();

        if(jwt == null) {
            return null;
        }
        return jwt.getSubject();

    }

    public static boolean hasAuthority(String authority){

        return Objects.requireNonNull(SecurityContextHolder
                        .getContext()
                        .getAuthentication())
                .getAuthorities()
                .stream()
                .anyMatch(a ->
                        Objects.equals(a.getAuthority(), authority)
                );
    }
}
