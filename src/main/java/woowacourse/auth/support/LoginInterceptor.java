package woowacourse.auth.support;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import woowacourse.auth.exception.WrongTokenException;

public class LoginInterceptor implements HandlerInterceptor {

    private static final String MEMBERS_RESOURCE = "/api/members";

    private final JwtTokenProvider jwtTokenProvider;
    private final Logger logger;

    public LoginInterceptor(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
        logger = LoggerFactory.getLogger(this.getClass());
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (isRegisterExclude(request.getRequestURI(), request.getMethod())) {
            return true;
        }
        logger.info("request uri = {}, request method = {} ", request.getRequestURI(), request.getMethod());
        String token = AuthorizationExtractor.extract(request);
        if (jwtTokenProvider.validateToken(token)) {
            return true;
        }
        throw new WrongTokenException();
    }

    private boolean isRegisterExclude(final String uri, final String method) {
        return uri.contains(MEMBERS_RESOURCE) && HttpMethod.POST.matches(method);
    }
}
