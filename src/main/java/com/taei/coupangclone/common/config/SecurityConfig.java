package com.taei.coupangclone.common.config;

import com.taei.coupangclone.common.jwt.JwtAuthFilter;
import com.taei.coupangclone.common.jwt.JwtUtil;
import com.taei.coupangclone.common.security.exception.CustomAccessDeniedHandler;
import com.taei.coupangclone.common.security.exception.CustomAuthenticationEntryPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    private final JwtUtil jwtUtil;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .httpBasic().disable() // Http basic Auth  기반으로 로그인 인증창이 뜸.  disable 시에 인증창 뜨지 않음.
            .csrf().disable() // rest api이므로 csrf 보안이 필요없으므로 disable처리. -> restapi는 왜 csrf가 필요 없는가?
            .formLogin().disable() // 기본으로 제공하는 로그인 창 뜨지 않게 설정
            .sessionManagement().sessionCreationPolicy(
                SessionCreationPolicy.STATELESS) // 스프링 시큐리티 세션정책 -> 스프링 시큐리티가 생성하지도 않고 기존것을 사용하지도 않음. jwt 방식 사용 예정
            .and()
            .exceptionHandling().authenticationEntryPoint(new CustomAuthenticationEntryPoint())
            .and()
            .exceptionHandling().accessDeniedHandler(new CustomAccessDeniedHandler())
            .and()
            .addFilterBefore(new JwtAuthFilter(jwtUtil),
                UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
