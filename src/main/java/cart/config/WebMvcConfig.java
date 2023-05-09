package cart.config;

import cart.auth.LoginArgumentResolver;
import java.util.List;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private final LoginArgumentResolver loginArgumentResolver;

    public WebMvcConfig(final LoginArgumentResolver loginArgumentResolver) {
        this.loginArgumentResolver = loginArgumentResolver;
    }

    @Override
    public void addArgumentResolvers(final List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(loginArgumentResolver);
    }
}
