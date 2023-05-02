package cart.configuration;

import cart.argumentresolver.basicauthorization.BasicAuthorizationArgumentResolver;
import java.util.List;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final BasicAuthorizationArgumentResolver basicAuthorizationArgumentResolver;

    public WebConfig(final BasicAuthorizationArgumentResolver basicAuthorizationArgumentResolver) {
        this.basicAuthorizationArgumentResolver = basicAuthorizationArgumentResolver;
    }

    @Override
    public void addArgumentResolvers(final List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(basicAuthorizationArgumentResolver);
    }
}
