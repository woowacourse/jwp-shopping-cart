package cart.web.argumentResolver;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import cart.domain.member.AuthService;
import cart.web.cart.dto.AuthInfo;

@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {

    private final AuthorizationExtractor<AuthInfo> authorizationExtractor;
    private final AuthService authService;

    public WebMvcConfiguration(final AuthorizationExtractor<AuthInfo> authorizationExtractor,
        final AuthService authService) {
        this.authorizationExtractor = authorizationExtractor;
        this.authService = authService;
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new AuthenticationArgumentResolver(authorizationExtractor, authService));
    }
}
