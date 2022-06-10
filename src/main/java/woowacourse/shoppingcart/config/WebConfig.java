package woowacourse.shoppingcart.config;

import java.util.Arrays;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    public static final String ALLOW_ORIGINS = "http://localhost:3000";

    @Override
    public void addCorsMappings(final CorsRegistry registry) {
        registry.addMapping("/api/**")
            .allowedMethods(getAllowedMethods())
            .allowedOrigins(ALLOW_ORIGINS)
            .exposedHeaders(HttpHeaders.LOCATION)
            .maxAge(60L * 60L * 12L);
    }

    private String[] getAllowedMethods() {
        return Arrays.stream(HttpMethod.values())
            .map(Enum::name)
            .toArray(String[]::new);
    }
}
