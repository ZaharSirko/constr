package com.example.constr.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.beans.factory.annotation.Autowired;



@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    // private final AuthenticationManager authenticationManager;
    // public SecurityConfig(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder,AuthenticationManager authenticationManager) {
    //     this.userDetailsService = userDetailsService;
    //     this.passwordEncoder = passwordEncoder;
    //     this.authenticationManager = authenticationManager;
    // }
    public SecurityConfig(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
        
    }
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests((authz) -> authz
                .requestMatchers("/**").permitAll()
                .anyRequest().authenticated()
            )
            .formLogin((form) -> form
                .loginPage("/log-in")
                .defaultSuccessUrl("/tour", true)
                .permitAll()
            )
            .logout((logout) -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .permitAll()
            )
            .httpBasic(withDefaults());


        return http.build();
    }
    // @Autowired
    // public void configureGlobal(AuthenticationManagerBuilder auth) 
    //   throws Exception {
    //     auth
    //       .inMemoryAuthentication()
    //       .withUser("user").password(passwordEncoder.encode("password")).roles("USER");
    //     //   .and()
    //     //   .withUser("admin").password(passwordEncoder.encode("admin")).roles("ADMIN");
    // }


}