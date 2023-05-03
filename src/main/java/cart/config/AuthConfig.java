package cart.config;

import cart.controller.auth.BasicAuthenticationsArgumentResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class AuthConfig implements WebMvcConfigurer {

    private final BasicAuthenticationsArgumentResolver basicAuthenticationsArgumentResolver;

    public AuthConfig(final BasicAuthenticationsArgumentResolver basicAuthenticationsArgumentResolver) {
        this.basicAuthenticationsArgumentResolver = basicAuthenticationsArgumentResolver;
    }

    @Override
    public void addArgumentResolvers(final List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(basicAuthenticationsArgumentResolver);
    }
}
