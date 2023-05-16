package cart.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import cart.dto.AuthInfo;
import cart.exception.DomainException;
import cart.exception.ExceptionCode;
import cart.infra.AuthorizationExtractor;

@Component
public class AuthInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws
        Exception {
        AuthInfo authInfo = AuthorizationExtractor.extract(request);
        if (authInfo == null) {
            throw new DomainException(ExceptionCode.NO_AUTHORIZATION_HEADER);
        }
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
}
