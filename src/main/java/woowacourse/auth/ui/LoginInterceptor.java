package woowacourse.auth.ui;

import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import woowacourse.auth.exception.AuthorizationFailureException;
import woowacourse.auth.support.AuthorizationExtractor;
import woowacourse.auth.support.JwtTokenProvider;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class LoginInterceptor implements HandlerInterceptor {

    private final JwtTokenProvider jwtTokenProvider;

    public LoginInterceptor(final JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public boolean preHandle(final HttpServletRequest request,
                             final HttpServletResponse response,
                             final Object handler) {
        if (isExclude(request.getMethod())) {
            return true;
        }

        final String token = AuthorizationExtractor.extract(request);
        if (token == null || !jwtTokenProvider.validateToken(token)) {
            throw new AuthorizationFailureException();
        }

        return true;
    }

    private boolean isExclude(final String method) {
        return HttpMethod.POST.name().equals(method) || HttpMethod.OPTIONS.name().equals(method);
    }
}
