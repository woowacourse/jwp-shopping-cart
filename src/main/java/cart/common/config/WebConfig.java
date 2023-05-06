package cart.common.config;

import cart.common.argumentresolver.MemberArgumentResolver;
import cart.common.interceptor.BasicAuthInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final MemberArgumentResolver memberArgumentResolver;
    private final BasicAuthInterceptor basicAuthInterceptor;

    public WebConfig(MemberArgumentResolver memberArgumentResolver, BasicAuthInterceptor basicAuthInterceptor) {
        this.memberArgumentResolver = memberArgumentResolver;
        this.basicAuthInterceptor = basicAuthInterceptor;
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(memberArgumentResolver);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(basicAuthInterceptor)
                .addPathPatterns("/carts/**");
    }
}
