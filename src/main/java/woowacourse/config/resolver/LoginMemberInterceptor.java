package woowacourse.config.resolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import woowacourse.auth.support.AuthHeaderExtractor;
import woowacourse.auth.support.TokenProvider;

@Component
public class LoginMemberInterceptor implements HandlerInterceptor {
    private final TokenProvider tokenProvider;
    private final AuthHeaderExtractor authHeaderExtractor;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        if (isSigningUp(request)) {
            return true;
        }

        setTokenToRequest(request);
        return true;
    }

    private void setTokenToRequest(HttpServletRequest request) {
        final String token = authHeaderExtractor.extract(request);
        final long id = tokenProvider.getPayload(token);
        request.setAttribute("id", id);
    }

    private boolean isSigningUp(HttpServletRequest request) {
        return HttpMethod.POST.matches(request.getMethod());
    }
}
