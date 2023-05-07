package cart.config;

import cart.presentation.adapter.AuthInterceptor;
import cart.presentation.adapter.HeaderMemberIdResolver;
import cart.presentation.adapter.LoggerInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {

    private final LoggerInterceptor loggerInterceptor;
    private final AuthInterceptor authInterceptor;

    public WebMvcConfiguration(LoggerInterceptor loggerInterceptor, AuthInterceptor authInterceptor) {
        this.loggerInterceptor = loggerInterceptor;
        this.authInterceptor = authInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loggerInterceptor)
                .addPathPatterns("/**");

        registry.addInterceptor(authInterceptor)
                .addPathPatterns("/carts");
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(new HeaderMemberIdResolver());
    }
}
