package woowacourse.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import woowacourse.auth.application.AuthService;
import woowacourse.auth.support.AuthenticationPrincipalArgumentResolver;
import woowacourse.auth.support.AuthenticationProductInterceptor;
import woowacourse.auth.support.JwtTokenInterceptor;

import java.util.List;

@Configuration
public class AuthenticationPrincipalConfig implements WebMvcConfigurer {
    private final AuthService authService;
    private final JwtTokenInterceptor jwtTokenInterceptor;
    private final AuthenticationProductInterceptor authenticationProductInterceptor;

    public AuthenticationPrincipalConfig(final AuthService authService, final JwtTokenInterceptor jwtTokenInterceptor, final AuthenticationProductInterceptor authenticationProductInterceptor) {
        this.authService = authService;
        this.jwtTokenInterceptor = jwtTokenInterceptor;
        this.authenticationProductInterceptor = authenticationProductInterceptor;
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(createAuthenticationPrincipalArgumentResolver());
    }

    @Bean
    public AuthenticationPrincipalArgumentResolver createAuthenticationPrincipalArgumentResolver() {
        return new AuthenticationPrincipalArgumentResolver(authService);
    }

    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
        registry.addInterceptor(jwtTokenInterceptor)
                .addPathPatterns("/api/**")
                .excludePathPatterns("/api/login", "/api/products/**",
                        "/api/customers", "/api/customers/exists");

        registry.addInterceptor(authenticationProductInterceptor)
                .addPathPatterns("/api/products");
    }
}
