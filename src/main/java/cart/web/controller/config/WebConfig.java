package cart.web.controller.config;

import cart.web.controller.auth.LoginCheckInterceptor;
import cart.web.controller.auth.LoginUserArgumentResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final LoginUserArgumentResolver loginUserArgumentResolver;
    private final LoginCheckInterceptor loginCheckInterceptor;

    public WebConfig(final LoginUserArgumentResolver loginUserArgumentResolver, final LoginCheckInterceptor loginCheckInterceptor) {
        this.loginUserArgumentResolver = loginUserArgumentResolver;
        this.loginCheckInterceptor = loginCheckInterceptor;
    }

    @Override
    public void addArgumentResolvers(final List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(loginUserArgumentResolver);
    }

    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
        registry.addInterceptor(loginCheckInterceptor)
                .order(1)
                .addPathPatterns("/cart/*");
    }
}
