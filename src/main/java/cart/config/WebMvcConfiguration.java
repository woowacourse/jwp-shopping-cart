package cart.config;

import java.util.List;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {

    private final MemberInfoArgumentResolver memberInfoArgumentResolver;
    private final LoginInterceptor basicAuthLoginInterceptor;

    public WebMvcConfiguration(final MemberInfoArgumentResolver memberInfoArgumentResolver,
                               final LoginInterceptor loginInterceptor) {
        this.memberInfoArgumentResolver = memberInfoArgumentResolver;
        this.basicAuthLoginInterceptor = loginInterceptor;
    }

    @Override
    public void addArgumentResolvers(final List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(memberInfoArgumentResolver);
    }

    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
        registry.addInterceptor(basicAuthLoginInterceptor)
                .addPathPatterns("/cart/products/**");
    }
}
