package woowacourse.auth.ui;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import woowacourse.auth.support.AuthorizationExtractor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class AuthenticationInterceptor implements HandlerInterceptor {

    private static final String ACCESS_TOKEN_ATTRIBUTE_NAME = "Access-Token";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        final String accessToken = AuthorizationExtractor.extract(request);
        request.setAttribute(ACCESS_TOKEN_ATTRIBUTE_NAME, accessToken);
        return true;
    }
}
