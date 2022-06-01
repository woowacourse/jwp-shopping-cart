package woowacourse.auth.config;

import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import woowacourse.auth.ui.AuthInterceptor;
import woowacourse.auth.application.AuthService;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class AuthenticationPrincipalConfig implements WebMvcConfigurer {

    private final AuthService authService;

    public AuthenticationPrincipalConfig(final AuthService authService) {
        this.authService = authService;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AuthInterceptor(authService))
                .addPathPatterns("/api/customers/me");
    }
}
