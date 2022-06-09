package woowacourse.shoppingcart.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    public static final String ALLOW_ORIGINS = "http://localhost:3000";
    public static final String ALLOWED_METHOD_NAMES = "GET,HEAD,POST,PUT,DELETE,TRACE,OPTIONS,PATCH";

    @Override
    public void addCorsMappings(final CorsRegistry registry) {
        registry.addMapping("/api/**")
            .allowedMethods(ALLOWED_METHOD_NAMES.split(","))
            .allowedOrigins(ALLOW_ORIGINS)
            .exposedHeaders(HttpHeaders.LOCATION)
            .maxAge(60L * 60L * 12L);
    }
}
