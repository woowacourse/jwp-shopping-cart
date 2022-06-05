package woowacourse.auth.ui.config;

import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import woowacourse.shoppingcart.application.CustomerService;
import woowacourse.auth.support.JwtTokenProvider;

@Configuration
public class AuthenticationPrincipalConfig implements WebMvcConfigurer {

    public static final String ALLOWED_METHOD_NAMES = "GET,HEAD,POST,PUT,DELETE,TRACE,OPTIONS,PATCH";

    private final JwtTokenProvider jwtTokenProvider;
    private final CustomerService customerService;

    public AuthenticationPrincipalConfig(JwtTokenProvider jwtTokenProvider, CustomerService customerService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.customerService = customerService;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor())
                .excludePathPatterns("/auth/login");
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(authenticationPrincipalArgumentResolver());
    }

    @Bean
    public LoginInterceptor loginInterceptor() {
        return new LoginInterceptor(jwtTokenProvider);
    }

    @Bean
    public AuthenticationPrincipalArgumentResolver authenticationPrincipalArgumentResolver() {
        return new AuthenticationPrincipalArgumentResolver(jwtTokenProvider, customerService);
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/customers/**")
                .allowedMethods(ALLOWED_METHOD_NAMES.split(","))
                .exposedHeaders(HttpHeaders.LOCATION);

        registry.addMapping("/auth/**")
                .allowedMethods(ALLOWED_METHOD_NAMES.split(","))
                .exposedHeaders(HttpHeaders.LOCATION);
    }
}
