package com.adarshgs.auth_workshop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import jakarta.servlet.http.HttpServletResponse;


@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
            // .exceptionHandling(exceptionHandling -> exceptionHandling.authenticationEntryPoint(
            //     (request, response, exception) -> {
            //         response.sendError(HttpServletResponse.SC_UNAUTHORIZED, exception.getMessage());
            //     }
            // ))
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/hello/public/**").permitAll()
                .requestMatchers("/hello/private/**").hasAnyRole("PRO_USER", "ADMIN")
                .requestMatchers("/hello/admin/**").hasRole("ADMIN")
                .requestMatchers("/login", "/login/**").permitAll()
                .anyRequest().authenticated()
            )
            .formLogin(Customizer.withDefaults());
            // .httpBasic(Customizer.withDefaults());
        
        return httpSecurity.build();
    }

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {

        UserDetails user = User.builder()
            .username("adarshgs")
            .roles("USER")
            .password(passwordEncoder().encode("adarshgs"))
            .build();

        UserDetails prouser = User.builder()
            .username("prouser")
            .roles("PRO_USER")
            .password(passwordEncoder().encode("prouser123"))
            .build();

        UserDetails admin = User.builder()
            .username("admin")
            .roles("ADMIN")
            .password(passwordEncoder().encode("admin123"))
            .build();

        return new InMemoryUserDetailsManager(user, prouser, admin);
    }
}