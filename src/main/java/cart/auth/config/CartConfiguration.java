package cart.auth.config;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import cart.auth.application.AuthService;
import cart.auth.infrastructure.AuthenticationArgumentResolver;
import cart.auth.infrastructure.LoginInterceptor;

@Configuration
public class CartConfiguration implements WebMvcConfigurer {
	@Override
	public void addViewControllers(final ViewControllerRegistry registry) {
		registry.addViewController("/cart")
			.setViewName("cart");
	}

	@Override
	public void addInterceptors(final InterceptorRegistry registry) {
		registry.addInterceptor(new LoginInterceptor())
			.addPathPatterns("/cart/**")
			.excludePathPatterns("/cart");
	}

	@Override
	public void addArgumentResolvers(final List<HandlerMethodArgumentResolver> resolvers) {
		resolvers.add(new AuthenticationPrincipalArgumentResolver());
	}
}
