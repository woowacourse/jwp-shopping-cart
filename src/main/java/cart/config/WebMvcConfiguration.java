package cart.config;

import cart.config.auth.SignInInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {

    private final SignInInterceptor signInInterceptor;

    public WebMvcConfiguration(SignInInterceptor signInInterceptor) {
        this.signInInterceptor = signInInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(signInInterceptor)
                .addPathPatterns("/products/**");
    }
}
