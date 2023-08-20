package com.example.myfinances.securityConfigs;

import com.example.myfinances.models.dto.IdDto;
import com.example.myfinances.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Request;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@RequiredArgsConstructor
//@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
@Configuration
@EnableWebSecurity
class SecurityConfig {
    final UserService userService;
    final Encoders encoders;

    @Bean
    public UserDetailsService userDetailsService() {
        return email -> userService.findByEmail(email)
                .map(user -> User.withUsername(user.getEmail())
                        .password(user.getPassword()) // password
                        .build())
                .orElseThrow(() -> new UsernameNotFoundException(""));
    }

    @Bean
    public DaoAuthenticationProvider authProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(encoders.passwordEncoder());
        return authProvider;
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        System.out.println(encoders.passwordEncoder().encode("password"));
        var configuration = new CorsConfiguration();
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowedOrigins(List.of("http://localhost:4200"));
        configuration.setAllowedMethods(List.of("DELETE", "GET", "POST", "PUT", "OPTION"));
        configuration.setAllowCredentials(true);
        var source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .authorizeHttpRequests(request -> request
                        .requestMatchers("/login").permitAll()
                        .requestMatchers(HttpMethod.GET ,"/api/user/checkAuth").permitAll()
//                        .anyRequest().permitAll()) // FIXME: this line have to be removed
                        .anyRequest().authenticated())
                .formLogin(loginRequest -> loginRequest
                        .successHandler((request, response, authentication) -> {
                                var requestEmail = request.getParameter("username");
                                var optionalUser = userService.findByEmail(requestEmail);

                                if(optionalUser.isPresent()) {
                                    var objectMapper = new ObjectMapper();
                                    var userId = IdDto.builder().id(optionalUser.get().getUserId()).build();
                                    var json = objectMapper.writeValueAsString(userId);
                                    response.setContentType("application/json");
                                    response.getWriter().write(json);
                                } else {
                                    response.setStatus(HttpStatus.NOT_FOUND.value());
                                }
                        })
                        .failureHandler((request, response, exception) ->
                                response.setStatus(HttpStatus.BAD_REQUEST.value())))
                .logout(logout -> logout
                        .logoutSuccessHandler((request, response, authentication) ->
                                response.setStatus(HttpStatus.OK.value())))
                .exceptionHandling(test -> test
                        .authenticationEntryPoint((request, response, authException) ->
                                response.setStatus(HttpStatus.UNAUTHORIZED.value())
                        ))
                .rememberMe(remember -> remember.rememberMeParameter("rememberMe"))
                .build();
    }
}



















