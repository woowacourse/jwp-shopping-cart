package woowacourse.auth.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import woowacourse.auth.application.AuthService;
import woowacourse.auth.support.AuthenticationArgumentResolver;
import woowacourse.auth.ui.AuthInterceptor;

import java.util.List;

@Configuration
public class AuthenticationPrincipalConfig implements WebMvcConfigurer {

    private final AuthService authService;
    private final AuthenticationArgumentResolver authenticationArgumentResolver;

    public AuthenticationPrincipalConfig(AuthService authService, AuthenticationArgumentResolver authenticationArgumentResolver) {
        this.authenticationArgumentResolver = authenticationArgumentResolver;
        this.authService = authService;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AuthInterceptor(authService))
                .addPathPatterns("/api/customers/me")
                .excludePathPatterns("/api/login/token");
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(authenticationArgumentResolver);
    }
}
