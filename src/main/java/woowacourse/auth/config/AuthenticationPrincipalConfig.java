package woowacourse.auth.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import woowacourse.auth.support.JwtTokenProvider;
import woowacourse.auth.ui.AuthenticationPrincipalArgumentResolver;
import woowacourse.auth.ui.LoginInterceptor;

import java.util.List;

@Configuration
public class AuthenticationPrincipalConfig implements WebMvcConfigurer {

    private final JwtTokenProvider jwtTokenProvider;

    public AuthenticationPrincipalConfig(final JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor(jwtTokenProvider))
                .addPathPatterns("/api/customers/**")
                .addPathPatterns("/api/carts/**")
                .excludePathPatterns("/api/login")
                .excludePathPatterns("/api/products/**");
    }

    @Override
    public void addArgumentResolvers(final List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(new AuthenticationPrincipalArgumentResolver(jwtTokenProvider));
    }
}
