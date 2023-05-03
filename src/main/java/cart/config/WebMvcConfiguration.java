package cart.config;

import cart.business.service.MemberService;
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

    private final MemberService memberService;

    public WebMvcConfiguration(MemberService memberService) {
        this.memberService = memberService;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoggerInterceptor())
                .addPathPatterns("/**");

        registry.addInterceptor(new AuthInterceptor(memberService))
                .addPathPatterns("/carts");
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(new HeaderMemberIdResolver());
    }
}
