package cart.controller.support;

import cart.controller.support.BasicAuthResolver;
import cart.service.AuthService;
import java.util.List;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final BasicAuthResolver basicAuthResolver;

    public WebConfig(BasicAuthResolver basicAuthResolver) {
        this.basicAuthResolver = basicAuthResolver;
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(basicAuthResolver);
    }
}