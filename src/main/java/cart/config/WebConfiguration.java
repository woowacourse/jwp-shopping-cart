package cart.config;

import cart.authentication.AuthenticateInterceptor;
import cart.authentication.BasicAuthorizationArgumentResolver;
import cart.dao.member.MemberDaoImpl;
import java.util.List;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfiguration implements WebMvcConfigurer {

    private final AuthenticateInterceptor authenticateInterceptor;

    public WebConfiguration(final AuthenticateInterceptor authenticateInterceptor) {
        this.authenticateInterceptor = authenticateInterceptor;
    }

    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
        registry.addInterceptor(authenticateInterceptor)
            .addPathPatterns("/cart-items/**");

    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new BasicAuthorizationArgumentResolver());
    }
}
