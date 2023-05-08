package cart.configure;

import cart.authorization.EmailPasswordArgumentResolver;
import cart.authorization.EmailPasswordAuthInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebConfigurer implements WebMvcConfigurer {
    private final EmailPasswordAuthInterceptor emailPasswordAuthInterceptor;

    public WebConfigurer(EmailPasswordAuthInterceptor emailPasswordAuthInterceptor) {
        this.emailPasswordAuthInterceptor = emailPasswordAuthInterceptor;
    }

    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
        registry.addInterceptor(emailPasswordAuthInterceptor)
                .addPathPatterns("/carts/**");
    }

    @Override
    public void addArgumentResolvers(final List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new EmailPasswordArgumentResolver());
    }
}
