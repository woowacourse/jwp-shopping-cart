package cart.common.config;

import cart.common.auth.AdminAuthInterceptor;
import cart.common.auth.MemberEmailArgumentResolver;
import java.util.List;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private final AdminAuthInterceptor adminAuthInterceptor;
    private final MemberEmailArgumentResolver memberEmailArgumentResolver;

    public WebMvcConfig(AdminAuthInterceptor adminAuthInterceptor,
                        MemberEmailArgumentResolver memberEmailArgumentResolver) {
        this.adminAuthInterceptor = adminAuthInterceptor;
        this.memberEmailArgumentResolver = memberEmailArgumentResolver;
    }

    @Override
    public void addArgumentResolvers(final List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(memberEmailArgumentResolver);
    }

    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
        registry.addInterceptor(adminAuthInterceptor)
            .excludePathPatterns("/admin")
            .addPathPatterns("/admin/**");
    }
}
