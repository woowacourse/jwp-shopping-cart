package woowacourse.shoppingcart.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import woowacourse.auth.ui.LoginInterceptor;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    public static final String ALLOWED_METHOD_NAMES = "GET,HEAD,POST,PUT,DELETE,TRACE,OPTIONS,PATCH";

    private final LoginInterceptor loginInterceptor;

    public WebConfig(final LoginInterceptor loginInterceptor) {
        this.loginInterceptor = loginInterceptor;
    }

    @Override
    public void addCorsMappings(final CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedMethods(ALLOWED_METHOD_NAMES.split(","))
                .allowedOrigins("*")
                .exposedHeaders(HttpHeaders.LOCATION);
    }

    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/api/customers/{id}")
                .addPathPatterns("/api/customers/{customerId}/carts");
    }
}
