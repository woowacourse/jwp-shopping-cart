package cart.common.config;

import cart.common.MemberArgumentResolver;
import cart.common.MemberInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {
    private final MemberArgumentResolver memberArgumentResolver;
    private final MemberInterceptor memberInterceptor;

    public WebMvcConfiguration(MemberArgumentResolver memberArgumentResolver, MemberInterceptor memberInterceptor) {
        this.memberArgumentResolver = memberArgumentResolver;
        this.memberInterceptor = memberInterceptor;
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(memberArgumentResolver);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(memberInterceptor).addPathPatterns("/carts/**");
    }
}
