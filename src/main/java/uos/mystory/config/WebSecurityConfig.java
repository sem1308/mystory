package uos.mystory.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import uos.mystory.utils.jwt.JwtAccessDeniedHandler;
import uos.mystory.utils.jwt.JwtAuthenticationEntryPoint;
import uos.mystory.utils.jwt.JwtFilter;
import uos.mystory.utils.jwt.JwtTokenProvider;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final JwtTokenProvider tokenProvider;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

    // 암호화에 필요한 PasswordEncoder 를 Bean 등록합니다.
    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return  http.csrf(AbstractHttpConfigurer::disable)
                // JWT 토큰 exception handler 적용
                .exceptionHandling(httpSecurityExceptionHandlingConfigurer ->
                    httpSecurityExceptionHandlingConfigurer
                            .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                            .accessDeniedHandler(jwtAccessDeniedHandler)
                )
                // 세션을 사용하지 않기 때문에 STATELESS로 설정
                .sessionManagement(httpSecuritySessionManagementConfigurer ->
                        httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                // 서버 접근 권한 설정
                .authorizeHttpRequests(authorize -> authorize
//                        .requestMatchers("/user").hasRole("USER")
                        .anyRequest().permitAll())
                // 기본 spring security 로그인 페이지 접근 권한 허용
                .formLogin(form -> form
                        .loginPage("/server/login").permitAll()
                        .defaultSuccessUrl("/index"))
                .logout(logout -> logout
                        .logoutUrl("/server/logout"))
                // security 필터 적용 전 JWT 필터 적용
                .addFilterBefore(new JwtFilter(tokenProvider),
                        UsernamePasswordAuthenticationFilter.class
                )
                .build();
    }
}