package woowacourse.auth.config;

import java.util.List;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import woowacourse.auth.application.AuthService;
import woowacourse.auth.support.AuthorizationExtractor;
import woowacourse.auth.ui.AuthArgumentResolver;

@Configuration
public class AuthenticationConfig implements WebMvcConfigurer {

    private final AuthService authService;
    private final AuthorizationExtractor authExtractor;

    public AuthenticationConfig(AuthService authService, AuthorizationExtractor authExtractor) {
        this.authExtractor = authExtractor;
        this.authService = authService;
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(new AuthArgumentResolver(authService, authExtractor));
    }
}
