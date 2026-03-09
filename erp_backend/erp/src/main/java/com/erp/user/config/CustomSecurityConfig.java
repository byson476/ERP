package com.erp.user.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.erp.user.security.CustomUserDetailsService;
import com.erp.user.security.filter.JWTCheckFilter;
import com.erp.user.security.handler.APILoginFailHandler;
import com.erp.user.security.handler.APILoginSuccessHandler;
import com.erp.user.security.handler.CustomAccessDeniedHandler;
import com.erp.user.security.handler.CustomAuthenticationEntryPoint;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Configuration
@Log4j2
@RequiredArgsConstructor
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class CustomSecurityConfig {
  @Autowired
  private CustomUserDetailsService userDetailsService;

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

    log.info("---------------------security config---------------------------");
    http.httpBasic((httpBasic) -> {
      httpBasic.disable();
    });
    http.sessionManagement(sessionConfig -> sessionConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
    http.csrf(csrf -> csrf.disable());

    http.authorizeHttpRequests((authorize) -> {
      authorize.requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
          .requestMatchers("/user/login", "/user/context", "/api/member/**", "/error").permitAll()
          // 회원가입 POST 허용
          .requestMatchers(HttpMethod.POST, "/user").permitAll()
          .anyRequest().authenticated();
    });
    http.formLogin(config -> {
      config.loginProcessingUrl("/user/login");
      config.successHandler(new APILoginSuccessHandler());
      config.failureHandler(new APILoginFailHandler());
      config.usernameParameter("userId");
      config.passwordParameter("password");
      config.permitAll();
    });

    http.addFilterBefore(new JWTCheckFilter(),
        UsernamePasswordAuthenticationFilter.class); // JWT체크

    http.exceptionHandling(config -> {
      config.accessDeniedHandler(new CustomAccessDeniedHandler());
      //config.authenticationEntryPoint(new CustomAuthenticationEntryPoint());
    });
    http.userDetailsService(userDetailsService);
    http.cors(cors -> cors.configurationSource(corsConfigurationSource()));
    return http.build();
  }

  @Bean
  public CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();
    // 또는 패턴으로 하고 싶으면 (Spring 6 이상)
    // 🔴 '*' + allowCredentials(true) 는 안 됨!
    configuration.setAllowedOriginPatterns(List.of("*"));
    configuration.setAllowCredentials(true); // credentials: 'include' 쓸 거면 true

    configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
    configuration.setAllowedHeaders(List.of("*"));
    configuration.setExposedHeaders(List.of("Authorization"));
    
    // 🔹 여기서 UrlBasedCorsConfigurationSource 사용
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    // 모든 경로에 대해 위 설정 적용
    source.registerCorsConfiguration("/**", configuration);

    return source;
  }

  @Bean
  public AuthenticationProvider authenticationProvider(PasswordEncoder passwordEncoder) {
    DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
    provider.setUserDetailsService(userDetailsService);
    provider.setPasswordEncoder(passwordEncoder);
    provider.setHideUserNotFoundExceptions(false); // UsernameNotFoundException 노출

    return provider;
  }

  /**
   * Swagger 페이지 접근에 대한 예외 처리
   */
  @Bean
  public WebSecurityCustomizer webSecurityCustomizer() {
    return (webSecurity) -> {
      webSecurity.ignoring().requestMatchers("/swagger-ui/**",
          "/v3/api-docs/**",
          "/swagger-ui.html");
      webSecurity.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    };
  }
}
