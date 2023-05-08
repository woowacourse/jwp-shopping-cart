package cart.config;

import cart.ui.CartAuthenticationArgumentResolver;
import cart.ui.CartInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebConfiguration implements WebMvcConfigurer {

    private final CartAuthenticationArgumentResolver cartAuthenticationArgumentResolver;
    private final CartInterceptor cartInterceptor;

    public WebConfiguration(CartAuthenticationArgumentResolver cartAuthenticationArgumentResolver, CartInterceptor cartInterceptor) {
        this.cartAuthenticationArgumentResolver = cartAuthenticationArgumentResolver;
        this.cartInterceptor = cartInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(cartInterceptor).addPathPatterns("/carts/**");
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(cartAuthenticationArgumentResolver);
    }
}
