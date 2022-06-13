package woowacourse;

import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import woowacourse.auth.controller.CustomerIdArgumentResolver;
import woowacourse.auth.controller.TokenArgumentResolver;
import woowacourse.auth.support.JwtTokenProvider;
import woowacourse.shoppingcart.controller.AuthInterceptor;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    public static final String ALLOWED_METHOD_NAMES = "GET,HEAD,POST,PUT,DELETE,TRACE,OPTIONS,PATCH";

    private final AuthInterceptor authInterceptor;
    private final JwtTokenProvider jwtTokenProvider;

    public WebConfig(AuthInterceptor authInterceptor, JwtTokenProvider jwtTokenProvider) {
        this.authInterceptor = authInterceptor;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public void addCorsMappings(final CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedMethods(ALLOWED_METHOD_NAMES.split(","))
                .exposedHeaders(HttpHeaders.LOCATION);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authInterceptor)
                .addPathPatterns("/api/customers/**")
                .excludePathPatterns("/api/customers");
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new TokenArgumentResolver());
        resolvers.add(userIdArgumentResolver());
    }

    @Bean
    public CustomerIdArgumentResolver userIdArgumentResolver() {
        return new CustomerIdArgumentResolver(jwtTokenProvider);
    }
}
