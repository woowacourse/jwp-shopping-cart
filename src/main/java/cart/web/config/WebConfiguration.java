package cart.web.config;

import cart.web.config.auth.BasicAuthorizedUserArgumentResolver;
import java.util.List;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfiguration implements WebMvcConfigurer {

    private final BasicAuthorizedUserArgumentResolver basicAuthorizedUserArgumentResolver;

    public WebConfiguration(final BasicAuthorizedUserArgumentResolver basicAuthorizedUserArgumentResolver) {
        this.basicAuthorizedUserArgumentResolver = basicAuthorizedUserArgumentResolver;
    }

    @Override
    public void addArgumentResolvers(final List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(basicAuthorizedUserArgumentResolver);
    }
}
