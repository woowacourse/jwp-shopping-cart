package woowacourse.auth.config;

import java.util.List;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import woowacourse.auth.ui.AuthenticationPrincipalArgumentResolver;
import woowacourse.auth.ui.LoginInterceptor;

@Configuration
public class AuthConfig implements WebMvcConfigurer {

    private final LoginInterceptor loginInterceptor;
    private final AuthenticationPrincipalArgumentResolver authenticationPrincipalArgumentResolver;

    public AuthConfig(final LoginInterceptor loginInterceptor,
                      final AuthenticationPrincipalArgumentResolver authenticationPrincipalArgumentResolver) {
        this.loginInterceptor = loginInterceptor;
        this.authenticationPrincipalArgumentResolver = authenticationPrincipalArgumentResolver;
    }

    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/api/customers")
                .addPathPatterns("/api/cartItems")
                .addPathPatterns("/api/cartItems/**")
                .addPathPatterns("/api/orders")
                .addPathPatterns("/api/orders/**")
                .addPathPatterns("/api/customers/password")
                .excludePathPatterns("/api/customers/signup", "/api/customers/login");
    }

    @Override
    public void addArgumentResolvers(final List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(authenticationPrincipalArgumentResolver);
    }
}
