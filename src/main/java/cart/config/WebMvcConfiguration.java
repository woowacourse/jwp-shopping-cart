package cart.config;

import cart.auth.AuthenticationPrincipalArgumentResolver;
import cart.auth.BasicAuthInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {

    private final AuthenticationPrincipalArgumentResolver authenticationPrincipalArgumentResolver;
    private final BasicAuthInterceptor basicAuthInterceptor;

    public WebMvcConfiguration(
            AuthenticationPrincipalArgumentResolver authenticationPrincipalArgumentResolver,
            BasicAuthInterceptor basicAuthInterceptor
    ) {
        this.authenticationPrincipalArgumentResolver = authenticationPrincipalArgumentResolver;
        this.basicAuthInterceptor = basicAuthInterceptor;
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/cart").setViewName("cart");
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(authenticationPrincipalArgumentResolver);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(basicAuthInterceptor)
                .addPathPatterns("/cart-items")
                .addPathPatterns("/cart-items/*");
    }
}
