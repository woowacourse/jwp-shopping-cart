package cart.config;

import cart.auth.AuthenticationArgumentResolver;
import cart.auth.AuthenticationExtractor;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new AuthenticationArgumentResolver(authenticationExtractor()));
    }

    @Bean
    public AuthenticationExtractor authenticationExtractor() {
        return new AuthenticationExtractor();
    }
}
