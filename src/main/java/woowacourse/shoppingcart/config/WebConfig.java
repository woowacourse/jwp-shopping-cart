package woowacourse.shoppingcart.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

	private static final String ALLOWED_METHOD_NAMES = "GET,HEAD,POST,PUT,DELETE,TRACE,OPTIONS,PATCH";

	@Override
	public void addCorsMappings(final CorsRegistry registry) {
		registry.addMapping("/**")
			.allowedOrigins("http://localhost:8080", "http://localhost:3000")
			.allowedMethods(ALLOWED_METHOD_NAMES.split(","))
			.exposedHeaders(HttpHeaders.LOCATION);
	}
}
