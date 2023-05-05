package cart.config;

import cart.util.BasicAuthArgumentResolver;
import java.util.List;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {

    private final BasicAuthArgumentResolver basicAuthArgumentResolver;

    public WebMvcConfiguration(final BasicAuthArgumentResolver basicAuthArgumentResolver) {
        this.basicAuthArgumentResolver = basicAuthArgumentResolver;
    }

    @Override
    public void addViewControllers(final ViewControllerRegistry registry) {
        registry.addViewController("/cart")
                .setViewName("cart");
    }

    @Override
    public void addArgumentResolvers(final List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(basicAuthArgumentResolver);
    }
}
