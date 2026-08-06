package org.odk.tooth_office.security;

import lombok.RequiredArgsConstructor;
import org.odk.tooth_office.config.CorsConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.config.Customizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.http.HttpMethod;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final CustomUserDetailsService customUserDetailsService;
    private final SecurityExceptionHandler securityExceptionHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .cors(Customizer.withDefaults())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(securityExceptionHandler)
                        .accessDeniedHandler(securityExceptionHandler)
                )
                .authorizeHttpRequests(
                        auth->auth.requestMatchers(
                                "/api/**"
                        ).permitAll()

                        .requestMatchers(HttpMethod.GET, "/api/cabinets", "/api/cabinets/**").permitAll()
                        .requestMatchers("/api/admins/**", "/api/utilisateurs/**").hasRole("ADMIN_SYSTEM")
                        .requestMatchers("/api/chefs-cabinet/**").hasAnyRole("ADMIN_SYSTEM", "CHEF_CABINET")
                        .requestMatchers("/api/dentistes/**", "/api/secretaires/**").hasAnyRole("ADMIN_SYSTEM", "CHEF_CABINET")
                        .requestMatchers("/api/patients/**").hasAnyRole("ADMIN_SYSTEM", "CHEF_CABINET", "SECRETAIRE", "DENTISTE", "PATIENT")
                        .requestMatchers(
                                "/api/dossiers-medicaux/**"
                        ).hasAnyRole("ADMIN_SYSTEM", "CHEF_CABINET", "DENTISTE", "PATIENT", "SECRETAIRE")
                        .requestMatchers(
                                "/api/consultation/**",
                                "/api/consultations/**",
                                "/traitements/**"
                        ).hasAnyRole("ADMIN_SYSTEM", "CHEF_CABINET", "DENTISTE")
                        .requestMatchers("/api/abonnements/**", "/api/plans-abonnement/**").hasAnyRole("ADMIN_SYSTEM", "CHEF_CABINET")
                        .anyRequest().authenticated()

                )
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider(customUserDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
}
