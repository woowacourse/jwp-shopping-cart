package woowacourse.auth.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import woowacourse.auth.application.AuthService;
import woowacourse.auth.support.JwtTokenProvider;
import woowacourse.auth.support.LoginInterceptor;
import woowacourse.auth.ui.AuthenticationPrincipalArgumentResolver;

@Configuration
public class AuthenticationPrincipalConfig implements WebMvcConfigurer {

    private final AuthService authService;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthenticationPrincipalConfig(AuthService authService,
        JwtTokenProvider jwtTokenProvider) {
        this.authService = authService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(createAuthenticationPrincipalArgumentResolver());
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor(jwtTokenProvider))
            .addPathPatterns("/api/customers/**");

    }

    @Bean
    public AuthenticationPrincipalArgumentResolver createAuthenticationPrincipalArgumentResolver() {
        return new AuthenticationPrincipalArgumentResolver(authService);
    }
}
