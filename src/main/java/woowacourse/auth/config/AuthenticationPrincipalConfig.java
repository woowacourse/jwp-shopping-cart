package woowacourse.auth.config;

import java.util.List;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import woowacourse.auth.controller.AuthInterceptor;
import woowacourse.auth.controller.OptionalUserNameResolver;
import woowacourse.auth.controller.UserNameResolver;
import woowacourse.auth.service.AuthService;

@Configuration
public class AuthenticationPrincipalConfig implements WebMvcConfigurer {

    private final AuthService authService;

    public AuthenticationPrincipalConfig(final AuthService authService) {
        this.authService = authService;
    }

    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
        registry.addInterceptor(new AuthInterceptor(authService))
                .addPathPatterns("/api/customers/me/**");
    }

    @Override
    public void addArgumentResolvers(final List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new UserNameResolver());
        resolvers.add(new OptionalUserNameResolver(authService));
    }
}
