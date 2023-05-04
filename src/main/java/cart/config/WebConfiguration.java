package cart.config;

import cart.auth.AuthArgumentResolver;
import cart.auth.AuthMemberDao;
import cart.auth.BasicAuthorizationParser;
import java.util.List;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfiguration implements WebMvcConfigurer {

    private final AuthMemberDao authMemberDao;
    private final BasicAuthorizationParser basicAuthorizationParser;

    public WebConfiguration(
            final AuthMemberDao authMemberDao,
            final BasicAuthorizationParser basicAuthorizationParser
    ) {
        this.authMemberDao = authMemberDao;
        this.basicAuthorizationParser = basicAuthorizationParser;
    }

    @Override
    public void addArgumentResolvers(final List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new AuthArgumentResolver(authMemberDao, basicAuthorizationParser));
    }
}
