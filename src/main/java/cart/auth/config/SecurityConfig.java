package cart.auth.config;

import cart.auth.resolver.AuthenticationResolver;
import cart.repository.user.UserRepository;
import java.util.List;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SecurityConfig implements WebMvcConfigurer {

    private final UserRepository userRepository;

    public SecurityConfig(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

//    @Bean
//    public FilterRegistrationBean<AuthenticationFilter> filterRegistrationBean() {
//        final FilterRegistrationBean<AuthenticationFilter> registrationBean = new FilterRegistrationBean<>();
//        registrationBean.setFilter(new AuthenticationFilter(userRepository));
//        registrationBean.addUrlPatterns("/*");
//        return registrationBean;
//    }

    @Override
    public void addArgumentResolvers(final List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(new AuthenticationResolver());
    }
}
