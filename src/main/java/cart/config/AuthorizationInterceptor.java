package cart.config;

import cart.authorization.AuthorizationExtractor;
import cart.dto.AuthorizationInformation;
import cart.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AuthorizationInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private MemberService memberService;

    @Autowired
    private AuthorizationExtractor<AuthorizationInformation> authorizationExtractor;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler){
        AuthorizationInformation authorizationInformation = authorizationExtractor.extract(request);
        return memberService.isValidMember(authorizationInformation);
    }
}
