package woowacourse.auth.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import woowacourse.auth.ui.AuthenticationPrincipalArgumentResolver;
import woowacourse.auth.ui.LoginInterceptor;
import java.util.List;

@Configuration
public class AuthenticationPrincipalConfig implements WebMvcConfigurer {

    private final LoginInterceptor loginInterceptor;
    private final AuthenticationPrincipalArgumentResolver argumentResolver;

    public AuthenticationPrincipalConfig(final LoginInterceptor loginInterceptor,
                                         final AuthenticationPrincipalArgumentResolver argumentResolver) {
        this.loginInterceptor = loginInterceptor;
        this.argumentResolver = argumentResolver;
    }

    @Override
    public void addArgumentResolvers(final List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(argumentResolver);
    }

    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor).addPathPatterns("/api/customer/**");
    }
}
