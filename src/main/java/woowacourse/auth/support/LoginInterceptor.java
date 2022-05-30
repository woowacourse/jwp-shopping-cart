package woowacourse.auth.support;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.HandlerInterceptor;

public class LoginInterceptor implements HandlerInterceptor {

    private static final String MEMBERS_RESOURCE = "/api/members";

    private final JwtTokenProvider jwtTokenProvider;

    public LoginInterceptor(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        if (isRegisterExclude(request.getRequestURI(), request.getMethod())) {
            return true;
        }
        String token = AuthorizationExtractor.extract(request);
        return jwtTokenProvider.validateToken(token);
    }

    private boolean isRegisterExclude(final String uri, final String method) {
        return uri.contains(MEMBERS_RESOURCE) && HttpMethod.POST.matches(method);
    }
}
