package ru.naumenJavaCourse.WebProject.Diswork.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder getPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    protected SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http
                .authorizeHttpRequests(authorizeHttpRequests -> authorizeHttpRequests.requestMatchers("/admin/*").hasRole("ADMIN").requestMatchers("/auth/login","/auth/registration","/index","/error", "/image/**").permitAll().anyRequest().authenticated())
                .formLogin(formLogin -> formLogin.loginPage("/auth/login").loginProcessingUrl("/process_login").defaultSuccessUrl("/auth/default",true).failureUrl("/auth/login?error"))
                .logout(log -> log.logoutUrl("/logout").logoutSuccessUrl("/index"));
        return http.build();
    }
}
