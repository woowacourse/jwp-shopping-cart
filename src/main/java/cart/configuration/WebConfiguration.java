package cart.configuration;

import cart.authentication.AuthenticationInterceptor;
import cart.authentication.AuthenticationPrincipalArgumentResolver;
import cart.authentication.AuthenticationValidator;
import cart.authentication.BasicAuthorizationExtractor;
import cart.repository.UserRepository;
import java.util.List;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfiguration implements WebMvcConfigurer {

    private final UserRepository userRepository;

    public WebConfiguration(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new AuthenticationPrincipalArgumentResolver());
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        BasicAuthorizationExtractor extractor = new BasicAuthorizationExtractor();
        AuthenticationValidator authValidator = new AuthenticationValidator(userRepository);

        registry.addInterceptor(new AuthenticationInterceptor(extractor, authValidator))
                .addPathPatterns("/carts/**");
    }
}
