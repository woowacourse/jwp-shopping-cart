package woowacourse.auth.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import woowacourse.auth.application.AuthService;
import woowacourse.auth.support.JwtTokenProvider;
import woowacourse.auth.ui.LoginInterceptor;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    private final AuthService authService;

    private final JwtTokenProvider jwtTokenProvider;

    public InterceptorConfig(JwtTokenProvider jwtTokenProvider, AuthService authService) {
        this.authService = authService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        WebMvcConfigurer.super.addInterceptors(registry);
        registry.addInterceptor(new LoginInterceptor(authService, jwtTokenProvider))
                .excludePathPatterns("/login/**")
                .addPathPatterns("/users/**")
                .excludePathPatterns("/users")
                .addPathPatterns("/cart")
                .addPathPatterns("/token/refresh");
    }
}
