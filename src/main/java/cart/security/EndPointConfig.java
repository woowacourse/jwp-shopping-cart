package cart.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class EndPointConfig implements WebMvcConfigurer {

    private final CustomHandlerMethodArgumentResolver customHandlerMethodArgumentResolver;

    public EndPointConfig(CustomHandlerMethodArgumentResolver customHandlerMethodArgumentResolver) {
        this.customHandlerMethodArgumentResolver = customHandlerMethodArgumentResolver;
    }

    @Override
    public void addArgumentResolvers(final List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(customHandlerMethodArgumentResolver);
    }

}
