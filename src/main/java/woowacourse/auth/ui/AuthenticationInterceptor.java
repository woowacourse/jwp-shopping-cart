package woowacourse.auth.ui;

import org.springframework.web.servlet.HandlerInterceptor;
import woowacourse.auth.support.AuthorizationExtractor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AuthenticationInterceptor implements HandlerInterceptor {

    private static final String ACCESS_TOKEN = "ACCESS_TOKEN";

    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, Object handler) {
        final String accessToken = AuthorizationExtractor.extract(request);
        request.setAttribute(ACCESS_TOKEN, accessToken);
        return true;
    }
}
