package com.mambo.otto.ottoserver.util;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.mambo.otto.ottoserver.domain.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * AUTH : SW
 * FUNCTION : 컨트롤러에 [ login / s ] 키워드가 붙은 매핑을 필터링하여 JWT토큰 검증 하도록 함
 * DATE : 2023.05.02
 * UPDATE( AUTH ) : -
 * 
 * <pre>
 * 런타임 핸들링
 * </pre>
 * 
 * @/login : PostMapping method with the login values Flitering
 * @/s : Flitering with that method values on any Controllers
 * @Profile : ("dev"), follow the application.yml file
 */

@Slf4j
@RequiredArgsConstructor
@Configuration
public class FilterConfig {

    private final UserRepository userRepository; // DI (스프링 IoC 컨테이너에서 옴)

    // IoC등록 (서버 실행시)
    @Bean
    public FilterRegistrationBean<JwtAuthenticationFilter> jwtAuthenticationFilterRegister() {
        log.debug("디버그 : 인증 필터 등록");
        FilterRegistrationBean<JwtAuthenticationFilter> bean = new FilterRegistrationBean<>(
                new JwtAuthenticationFilter(userRepository));
        bean.addUrlPatterns("/login");
        bean.setOrder(1); // 낮은 순서대로 실행
        return bean;
    }

    @Profile("dev")
    @Bean
    public FilterRegistrationBean<JwtAuthorizationFilter> jwtAuthorizationFilterRegister() {
        log.debug("디버그 : 인가 필터 등록");
        FilterRegistrationBean<JwtAuthorizationFilter> bean = new FilterRegistrationBean<>(
                new JwtAuthorizationFilter());
        bean.addUrlPatterns("/s/*");
        bean.setOrder(2);
        return bean;
    }

}
