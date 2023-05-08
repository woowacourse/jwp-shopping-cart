package cart.config;

import cart.authentication.AuthenticateInterceptor;
import cart.authentication.AuthorityInterceptor;
import cart.authentication.BasicAuthorizationArgumentResolver;
import java.util.List;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfiguration implements WebMvcConfigurer {

    private final AuthenticateInterceptor authenticateInterceptor;
    private final AuthorityInterceptor authorityInterceptor;

    public WebConfiguration(final AuthenticateInterceptor authenticateInterceptor, final AuthorityInterceptor authorityInterceptor) {
        this.authenticateInterceptor = authenticateInterceptor;
        this.authorityInterceptor = authorityInterceptor;
    }

    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
        registry.addInterceptor(authenticateInterceptor)
            .addPathPatterns("/cart-items/**");

        registry.addInterceptor(authorityInterceptor)
            .addPathPatterns("/admin/**")
            .excludePathPatterns("/admin");

    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new BasicAuthorizationArgumentResolver());
    }
}
