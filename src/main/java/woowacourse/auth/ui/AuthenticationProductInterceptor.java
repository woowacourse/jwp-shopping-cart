package woowacourse.auth.ui;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import woowacourse.auth.exception.InvalidTokenException;
import woowacourse.auth.support.AuthorizationExtractor;
import woowacourse.auth.support.JwtTokenProvider;

public class AuthenticationProductInterceptor implements HandlerInterceptor {

    private final JwtTokenProvider jwtTokenProvider;

    public AuthenticationProductInterceptor(final JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response,
                             final Object handler) throws IOException {
        final String method = request.getMethod();
        if (!HttpMethod.GET.matches(method)) {
            return true;
        }
        if (request.getHeader("Authorization") != null) {
            validateToken(request);
            response.sendRedirect(getRedirectURI(request));
            return false;
        }
        return true;
    }

    private void validateToken(final HttpServletRequest request) {
        final String token = AuthorizationExtractor.extract(request);
        if (!jwtTokenProvider.validateToken(token)) {
            throw new InvalidTokenException();
        }
    }

    private String getRedirectURI(final HttpServletRequest request) {
        if (request.getRequestURI().contains("pageable")) {
            return request.getRequestURI() + "/me" + "?size=" + request.getParameter("size") + "&page="
                    + request.getParameter("page");
        }
        return request.getRequestURI() + "/me";
    }
}
