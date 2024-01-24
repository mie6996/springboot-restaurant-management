package com.restaurant.config;

import com.restaurant.security.CustomUnauthorizedEntryPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
  private final JwtAuthenticationFilter jwtAuthFilter;
  private final AuthenticationProvider authenticationProvider;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

    http
      .csrf()
      .disable()
      .cors(cors -> cors.configurationSource(corsConfigurationSource()))
      .authorizeHttpRequests()
      .shouldFilterAllDispatcherTypes(false) // Disable filter for all dispatcher types
      .requestMatchers("/api/v1/auth/**")
        .permitAll()
      .anyRequest()
        .authenticated()
      .and()
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
      .and()
      .authenticationProvider(authenticationProvider)
      .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
      .exceptionHandling(exceptionHandling -> exceptionHandling
                      .authenticationEntryPoint(unauthorizedHandler())
                      .accessDeniedHandler(accessDeniedHandler())
                );
    return http.build();

  }

   @Bean
  public AuthenticationEntryPoint unauthorizedHandler() {
      return new CustomUnauthorizedEntryPoint();
  }

  @Bean
  public AccessDeniedHandler accessDeniedHandler() {
      return new AccessDeniedHandlerImpl();
  }

  @Bean
  public CorsConfigurationSource corsConfigurationSource() {
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

    CorsConfiguration config = new CorsConfiguration();
    config.setAllowedOrigins(List.of("*"));
    config.setAllowedOriginPatterns(List.of("*"));
    config.setAllowedHeaders(List.of("Content-Type", "X-Frame-Options", "X-XSS-Protection",
            "X-Content-Type-Options", "Strict-Transport-Security", "Authorization"));
    config.setAllowedMethods(List.of("OPTIONS", "GET", "POST", "PUT", "DELETE"));
    config.setExposedHeaders(List.of("ERROR_CODE"));
    config.setAllowCredentials(false);
    config.setMaxAge(3600L);
    source.registerCorsConfiguration("/**", config);
    return source;
  }

}
