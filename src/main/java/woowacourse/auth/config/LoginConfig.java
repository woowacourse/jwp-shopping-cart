package woowacourse.auth.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import woowacourse.auth.ui.LoginInterceptor;

@Configuration
public class LoginConfig implements WebMvcConfigurer {

    private final LoginInterceptor loginInterceptor;

    public LoginConfig(final LoginInterceptor loginInterceptor) {
        this.loginInterceptor = loginInterceptor;
    }

    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/api/customers")
                .addPathPatterns("/api/cartItems/**")
                .addPathPatterns("/api/orders/**")
                .addPathPatterns("/api/customers/password")
                .excludePathPatterns("/api/customers/signup", "/api/customers/login");
    }
}
