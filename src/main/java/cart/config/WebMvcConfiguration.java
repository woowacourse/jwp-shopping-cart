package cart.config;


import cart.auth.AuthenticationArgumentResolver;
import cart.auth.AuthenticationInterceptor;
import cart.auth.BasicAuthorizationExtractor;
import cart.auth.credential.CredentialDao;
import java.util.List;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {

    private final BasicAuthorizationExtractor basicAuthorizationExtractor;
    private final CredentialDao credentialDao;

    public WebMvcConfiguration(BasicAuthorizationExtractor basicAuthorizationExtractor,
            CredentialDao credentialDao) {
        this.basicAuthorizationExtractor = basicAuthorizationExtractor;
        this.credentialDao = credentialDao;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AuthenticationInterceptor(basicAuthorizationExtractor, credentialDao))
                .addPathPatterns("/cart-product/**");
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new AuthenticationArgumentResolver());
    }
}
