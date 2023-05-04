package cart.config;

import java.util.List;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {

    private final MemberInfoArgumentResolver memberInfoArgumentResolver;

    public WebMvcConfiguration(final MemberInfoArgumentResolver memberInfoArgumentResolver) {
        this.memberInfoArgumentResolver = memberInfoArgumentResolver;
    }

    @Override
    public void addArgumentResolvers(final List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(memberInfoArgumentResolver);
    }
}
