package cart.config;

import cart.auth.AuthArgumentResolver;
import cart.auth.AuthInterceptor;
import cart.auth.AuthMemberDao;
import cart.auth.BasicAuthorizationParser;
import cart.auth.CredentialThreadLocal;
import java.util.List;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfiguration implements WebMvcConfigurer {

    private final AuthMemberDao authMemberDao;
    private final BasicAuthorizationParser basicAuthorizationParser;
    private final CredentialThreadLocal credentialThreadLocal;

    public WebConfiguration(
            final AuthMemberDao authMemberDao,
            final BasicAuthorizationParser basicAuthorizationParser,
            final CredentialThreadLocal credentialThreadLocal
    ) {
        this.authMemberDao = authMemberDao;
        this.basicAuthorizationParser = basicAuthorizationParser;
        this.credentialThreadLocal = credentialThreadLocal;
    }

    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
        final AuthInterceptor authInterceptor = new AuthInterceptor(
                authMemberDao,
                basicAuthorizationParser,
                credentialThreadLocal
        );
        registry.addInterceptor(authInterceptor).addPathPatterns("/cart-products/**");
    }

    @Override
    public void addArgumentResolvers(final List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new AuthArgumentResolver(credentialThreadLocal));
    }
}
