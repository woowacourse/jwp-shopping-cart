package cart.config;

import cart.authorization.AuthorizationExtractor;
import cart.authorization.AuthorizationInformation;
import cart.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private MemberService memberService;

    @Autowired
    private AuthorizationExtractor<AuthorizationInformation> authorizationExtractor;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        AuthorizationInformation authorizationInformation = authorizationExtractor.extract(request);
        return memberService.isValidMember(authorizationInformation);
    }
}
