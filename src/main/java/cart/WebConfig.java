package cart;

import cart.auth.BasicAuthArgumentResolver;
import cart.dao.member.MemeberDao;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final MemeberDao memeberDao;

    public WebConfig(MemeberDao memeberDao) {
        this.memeberDao = memeberDao;
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new BasicAuthArgumentResolver(memeberDao));
    }
}