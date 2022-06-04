package woowacourse.auth.ui;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;
import woowacourse.auth.support.HttpHeaderUtil;
import woowacourse.common.exception.ForbiddenException;

public class AuthorizationInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String accessToken = request.getHeader(HttpHeaderUtil.AUTHORIZATION);
        if (accessToken == null || !accessToken.toLowerCase().startsWith(HttpHeaderUtil.BEARER_TYPE)) {
            throw new ForbiddenException();
        }
        return true;
    }
}
