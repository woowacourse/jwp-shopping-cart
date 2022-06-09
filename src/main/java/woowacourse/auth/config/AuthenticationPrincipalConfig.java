package woowacourse.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import woowacourse.auth.application.AuthService;
import woowacourse.auth.support.AuthenticationPrincipalArgumentResolver;
import woowacourse.auth.support.JwtTokenInterceptor;
import woowacourse.auth.support.JwtTokenProvider;
import woowacourse.shoppingcart.CustomerArgumentResolver;

import java.util.List;

@Configuration
public class AuthenticationPrincipalConfig implements WebMvcConfigurer {
    private final AuthService authService;
    private final JwtTokenProvider jwtTokenProvider;
    private final JwtTokenInterceptor jwtTokenInterceptor;

    public AuthenticationPrincipalConfig(final AuthService authService, final JwtTokenProvider jwtTokenProvider, final JwtTokenInterceptor jwtTokenInterceptor) {
        this.authService = authService;
        this.jwtTokenProvider = jwtTokenProvider;
        this.jwtTokenInterceptor = jwtTokenInterceptor;
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(createAuthenticationPrincipalArgumentResolver());
        argumentResolvers.add(customerArgumentResolver());
    }

    @Bean
    public AuthenticationPrincipalArgumentResolver createAuthenticationPrincipalArgumentResolver() {
        return new AuthenticationPrincipalArgumentResolver(authService);
    }

    @Bean
    public CustomerArgumentResolver customerArgumentResolver() {
        return new CustomerArgumentResolver(jwtTokenProvider);
    }

    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
        registry.addInterceptor(jwtTokenInterceptor)
                .addPathPatterns("/api/**")
                .excludePathPatterns("/api/login", "/api/products/**",
                        "/api/customers", "/api/customers/exists");
    }
}
