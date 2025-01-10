package com.study.Boardify.config.security;

import com.study.Boardify.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserRepository userRepository;

    private static final String[] ANONYMOUS_USER_URLS = {"/users/login", "/users/join"};

    private static final String[] AUTHENTICATED_USER_URLS = {"/board/**/**/edit", "/board/**/**/delete", "/likes/**", "/users/myPage/**", "/users/edit", "/users/delete"};

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // CSRF 보호 비활성화
                .cors(withDefaults())        // CORS 기본 설정
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(ANONYMOUS_USER_URLS).anonymous()
                        .requestMatchers(AUTHENTICATED_USER_URLS).authenticated()
                        .requestMatchers("/boards/greeting/write").hasAnyAuthority("BRONZE", "ADMIN")
                        .requestMatchers(HttpMethod.POST, "/boards/greeting").hasAnyAuthority("BRONZE", "ADMIN")
                        .requestMatchers("/boards/free/write").hasAnyAuthority("SILVER", "GOLD", "ADMIN")
                        .requestMatchers(HttpMethod.POST, "/boards/free").hasAnyAuthority("SILVER", "GOLD", "ADMIN")
                        .requestMatchers("/boards/gold/**").hasAnyAuthority("GOLD", "ADMIN")
                        .requestMatchers("/users/admin/**").hasAuthority("ADMIN")
                        .requestMatchers("/comments/**").hasAnyAuthority("BRONZE", "SILVER", "GOLD", "ADMIN")
                        .anyRequest().permitAll()
                )
                .exceptionHandling(exception -> exception
                        .accessDeniedHandler(new MyAccessDeniedHandler(userRepository))
                        .authenticationEntryPoint(new MyAuthenticationEntryPoint())
                )
                .formLogin(login -> login
                        .loginPage("/users/login")
                        .usernameParameter("loginId")
                        .passwordParameter("password")
                        .failureUrl("/users/login?fail")
                        .successHandler(new MyLoginSuccessHandler(userRepository))
                )
                .logout(logout -> logout
                        .logoutUrl("/users/logout")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .logoutSuccessHandler(new MyLogoutSuccessHandler())
                );

        return http.build();
    }
}
