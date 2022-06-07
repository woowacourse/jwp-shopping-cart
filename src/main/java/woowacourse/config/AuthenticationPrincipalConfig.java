package woowacourse.config;

import java.util.List;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class AuthenticationPrincipalConfig implements WebMvcConfigurer {

    private final HandlerInterceptor interceptor;
    private final HandlerMethodArgumentResolver argumentResolver;

    public AuthenticationPrincipalConfig(HandlerInterceptor interceptor, HandlerMethodArgumentResolver argumentResolver) {
        this.interceptor = interceptor;
        this.argumentResolver = argumentResolver;
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(argumentResolver);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(interceptor)
                .addPathPatterns("/api/members/**")
                .excludePathPatterns("/api/members/email-check")
                .excludePathPatterns("/api/members")
                .addPathPatterns("/api/carts/**");
    }
}
