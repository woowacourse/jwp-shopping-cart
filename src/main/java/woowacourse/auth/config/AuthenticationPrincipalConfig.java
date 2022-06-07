package woowacourse.auth.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import woowacourse.auth.support.AuthenticationArgumentResolver;
import woowacourse.auth.support.JwtTokenProvider;
import woowacourse.auth.support.TokenProvider;
import woowacourse.auth.ui.AuthInterceptor;

import java.util.List;

@Configuration
public class AuthenticationPrincipalConfig implements WebMvcConfigurer {

    private final TokenProvider tokenProvider;
    private final AuthenticationArgumentResolver authenticationArgumentResolver;

    public AuthenticationPrincipalConfig(TokenProvider tokenProvider, AuthenticationArgumentResolver authenticationArgumentResolver) {
        this.authenticationArgumentResolver = authenticationArgumentResolver;
        this.tokenProvider = tokenProvider;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AuthInterceptor(tokenProvider))
                .addPathPatterns("/api/customers/me")
                .excludePathPatterns("/api/login/token");
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(authenticationArgumentResolver);
    }
}
