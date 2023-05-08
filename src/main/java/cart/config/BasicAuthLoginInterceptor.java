package cart.config;

import cart.service.BasicAuthService;
import cart.service.dto.MemberInfo;
import cart.config.util.BasicAuthExtractor;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

@Component
public class BasicAuthLoginInterceptor extends LoginInterceptor {

    private final BasicAuthExtractor basicAuthExtractor;
    private final BasicAuthService authService;

    public BasicAuthLoginInterceptor(final BasicAuthExtractor basicAuthExtractor, final BasicAuthService authService) {
        this.basicAuthExtractor = basicAuthExtractor;
        this.authService = authService;
    }

    @Override
    void authorize(final HttpServletRequest request) {
        final MemberInfo memberInfo = basicAuthExtractor.extract(request);
        authService.authorize(memberInfo);
    }
}
