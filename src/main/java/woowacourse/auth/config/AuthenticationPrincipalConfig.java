package woowacourse.auth.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import woowacourse.auth.ui.AuthInterceptor;
import woowacourse.auth.ui.AuthenticationPrincipalArgumentResolver;

import java.util.List;

@Configuration
public class AuthenticationPrincipalConfig implements WebMvcConfigurer {
    private final AuthInterceptor authInterceptor;
    private final AuthenticationPrincipalArgumentResolver authenticationPrincipalArgumentResolver;

    public AuthenticationPrincipalConfig(
            AuthInterceptor authInterceptor,
            AuthenticationPrincipalArgumentResolver authenticationPrincipalArgumentResolver) {
        this.authInterceptor = authInterceptor;
        this.authenticationPrincipalArgumentResolver = authenticationPrincipalArgumentResolver;
    }

    @Override
    public void addArgumentResolvers(List argumentResolvers) {
        argumentResolvers.add(authenticationPrincipalArgumentResolver);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authInterceptor)
                .addPathPatterns("/users/me")
                .addPathPatterns("/users/me/carts/**")
                .addPathPatterns("/users/me/carts");
    }
}
