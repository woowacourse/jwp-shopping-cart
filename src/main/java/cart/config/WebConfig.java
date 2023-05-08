package cart.config;

import cart.auth.AuthSubjectArgumentResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    private final AuthSubjectArgumentResolver argumentResolver;
    
    public WebConfig(final AuthSubjectArgumentResolver argumentResolver) {
        this.argumentResolver = argumentResolver;
    }
    
    @Override
    public void addArgumentResolvers(final List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(argumentResolver);
    }
}
