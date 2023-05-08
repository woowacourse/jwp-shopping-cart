package cart.config;

import cart.auth.AuthArgumentResolver;
import cart.auth.AuthDao;
import cart.auth.AuthInterceptor;
import cart.auth.BasicCredentialExtractor;
import cart.auth.CredentialThreadLocal;
import java.util.List;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final BasicCredentialExtractor basicCredentialExtractor;
    private final AuthDao authDao;
    private final CredentialThreadLocal credentialThreadLocal;

    public WebConfig(final BasicCredentialExtractor basicCredentialExtractor, final AuthDao authDao,
                     final CredentialThreadLocal credentialThreadLocal) {
        this.basicCredentialExtractor = basicCredentialExtractor;
        this.authDao = authDao;
        this.credentialThreadLocal = credentialThreadLocal;
    }

    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
        registry.addInterceptor(new AuthInterceptor(
                        basicCredentialExtractor,
                        authDao,
                        credentialThreadLocal
                ))
                .addPathPatterns("/carts/**");
    }

    @Override
    public void addArgumentResolvers(final List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new AuthArgumentResolver(credentialThreadLocal));
    }
}
