package cart.mvcconfig;

import cart.infrastructure.AuthInfo;
import cart.infrastructure.AuthorizationExtractor;
import cart.infrastructure.BasicAuthorizationExtractor;
import cart.infrastructure.UnauthorizedMemberException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        final AuthorizationExtractor<AuthInfo> authorizationExtractor = new BasicAuthorizationExtractor();
        final AuthInfo authInfo = authorizationExtractor.extract(request);

        if (authInfo == null) {
            throw new UnauthorizedMemberException("확인되지 않은 사용자입니다");
        }

        log.info("authInfo = {}", authInfo);
        request.setAttribute("authInfo", authInfo);
        return true;
    }
}
