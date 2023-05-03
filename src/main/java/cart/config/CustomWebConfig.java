package cart.config;

import cart.auth.CustomAuthResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class CustomWebConfig implements WebMvcConfigurer {
    private final CustomAuthResolver customArgumentResolver;

    public CustomWebConfig(CustomAuthResolver customArgumentResolver) {
        this.customArgumentResolver = customArgumentResolver;
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/cart")
                .setViewName("cart");
    }

    @Override
    public void addArgumentResolvers(final List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(customArgumentResolver);
    }
}
