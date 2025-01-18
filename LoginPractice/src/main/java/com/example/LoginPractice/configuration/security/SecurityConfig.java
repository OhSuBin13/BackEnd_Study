package com.example.LoginPractice.configuration.security;

import com.example.LoginPractice.domain.UserRole;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.SecurityBuilder;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig{

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(m -> {
                    m.requestMatchers("/security-login/info").authenticated();
                    m.requestMatchers("/security-login/admin/**").hasAuthority(UserRole.ADMIN.name());
                    m.anyRequest().permitAll();
                })
                .formLogin(login -> {
                    login.usernameParameter("loginId");
                    login.passwordParameter("password");
                    login.loginPage("/security-login/login");
                    login.defaultSuccessUrl("/security-login");
                    login.failureUrl("/security-login/login");
                })
                .logout(logout -> {
                    logout.logoutUrl("/security-login/logout");
                    logout.invalidateHttpSession(true).deleteCookies("JSESSIONID");
                }).build();
    }
}
