package com.kaan.gradesystem.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;

import com.kaan.gradesystem.security.JwtAuthenticationFilter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration //Bu bir ayar sınıfı
@EnableMethodSecurity //@PreAuthorize gibi method güvenliklerini aktif et demek.
public class SecurityConfig {


        @Bean //Spring'in oluşturup yönettiği Java nesnesi.
        public PasswordEncoder passwordEncoder(){ //şifreyi düz metin olarak Spring Security'ye vermek yerine BCrypt ile işliyor.
        return new BCryptPasswordEncoder(); //“Bu metodun döndürdüğü nesneyi sen oluştur, sakla ve 
        }                                  //uygulamanın ihtiyaç duyduğu yerlerde kullan.”

        @Bean 
        UserDetailsService userDetailsService (PasswordEncoder passwordEncoder){
        UserDetails admin = User.builder()
        .username("admin")
        .password(passwordEncoder.encode("1234"))
        .roles("ADMIN")
        .build();

        UserDetails user = User.builder()
        .username("user")
        .password(passwordEncoder.encode("5678"))
        .roles("USER")
        .build();

        return new InMemoryUserDetailsManager(admin ,user); //kullanıcıları şimdilik veritabanında değil,
                                                                //uygulamanın belleğinde tutuyor.
        }

        @Bean
        public SecurityFilterChain securityFilterChain(
                HttpSecurity http,
                JwtAuthenticationFilter jwtAuthenticationFilter) throws Exception {

                http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/login").permitAll()
                        .requestMatchers("/h2-console/**").permitAll()
                        .requestMatchers(
                                "/swagger-ui/**",
                                "/swagger-ui.html",
                                "/v3/api-docs/**"
                ).permitAll()
                .anyRequest().authenticated()
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .headers(headers -> headers
                        .frameOptions(frame -> frame.sameOrigin())
                )
                .addFilterBefore(
                        jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class
                );

        return http.build();
        }

        @Bean
        public AuthenticationManager authenticationManager(
                AuthenticationConfiguration configuration) throws Exception{
                        return configuration.getAuthenticationManager();
                }


        }