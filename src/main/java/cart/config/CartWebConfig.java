package cart.config;

import cart.auth.AuthInfo;
import cart.auth.AuthenticationResolver;
import cart.auth.AuthorizationParser;
import cart.dao.MemberJdbcDao;
import java.util.List;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CartWebConfig implements WebMvcConfigurer {

    private final AuthorizationParser<AuthInfo> authorizationParser;

    private final MemberJdbcDao memberDao;

    public CartWebConfig(
            final AuthorizationParser<AuthInfo> authorizationParser,
            final MemberJdbcDao memberDao
    ) {
        this.authorizationParser = authorizationParser;
        this.memberDao = memberDao;
    }

    @Override
    public void addArgumentResolvers(final List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(new AuthenticationResolver(
                authorizationParser,
                memberDao
        ));
    }
}
