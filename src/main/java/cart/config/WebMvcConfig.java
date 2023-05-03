package cart.config;

import cart.authentication.AuthArgumentResolver;
import cart.authentication.BasicLoginInterceptor;
import cart.dao.MemberDao;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private final MemberDao memberDao;

    public WebMvcConfig(MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new BasicLoginInterceptor(memberDao))
                .addPathPatterns("/admin/**")
                .addPathPatterns("/carts/**");
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new AuthArgumentResolver());
    }


}
