package cart.config;

import cart.auth.AuthenticateService;
import cart.auth.AuthenticateUserArgumentResolver;
import cart.service.UserService;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {

    private final UserService userService;
    private final AuthenticateService authenticateService;

    public WebMvcConfiguration(UserService userService, AuthenticateService authenticateService) {
        this.userService = userService;
        this.authenticateService = authenticateService;
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new AuthenticateUserArgumentResolver(authenticateService, userService));
    }
}
