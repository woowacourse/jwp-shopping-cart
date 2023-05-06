package cart.config;

import cart.auth.AuthArgumentResolver;
import cart.auth.AuthInterceptor;
import cart.auth.BasicAuthParser;
import cart.auth.CredentialThreadLocal;
import cart.service.AuthService;
import java.util.List;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final CredentialThreadLocal credentialThreadLocal;
    private final AuthService authService;
    private final BasicAuthParser basicAuthParser;

    public WebConfig(CredentialThreadLocal credentialThreadLocal, AuthService authService,
                     BasicAuthParser basicAuthParser) {
        this.credentialThreadLocal = credentialThreadLocal;
        this.authService = authService;
        this.basicAuthParser = basicAuthParser;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AuthInterceptor(authService, credentialThreadLocal, basicAuthParser))
                .addPathPatterns("/cart-products/**");
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new AuthArgumentResolver(credentialThreadLocal));
    }
}
