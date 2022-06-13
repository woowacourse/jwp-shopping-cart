package woowacourse.auth.config;

import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import woowacourse.auth.application.AuthService;
import woowacourse.auth.support.JwtTokenProvider;
import woowacourse.auth.ui.AuthenticationInterceptor;
import woowacourse.auth.ui.AuthenticationPrincipalArgumentResolver;
import woowacourse.auth.ui.AuthenticationProductInterceptor;

@Configuration
public class AuthenticationPrincipalConfig implements WebMvcConfigurer {

    private final AuthService authService;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthenticationPrincipalConfig(final AuthService authService, final JwtTokenProvider jwtTokenProvider) {
        this.authService = authService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
        registry.addInterceptor(new AuthenticationInterceptor(jwtTokenProvider))
                .addPathPatterns("/api/customers/me");
        registry.addInterceptor(new AuthenticationProductInterceptor(jwtTokenProvider))
                .addPathPatterns("/api/products")
                .addPathPatterns("/api/products/pageable");
    }

    @Override
    public void addArgumentResolvers(final List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(createAuthenticationPrincipalArgumentResolver());
    }

    @Bean
    public AuthenticationPrincipalArgumentResolver createAuthenticationPrincipalArgumentResolver() {
        return new AuthenticationPrincipalArgumentResolver(authService);
    }
}
