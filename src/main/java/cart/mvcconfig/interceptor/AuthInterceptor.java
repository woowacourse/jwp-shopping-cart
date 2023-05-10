package cart.mvcconfig.interceptor;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cart.exception.AuthorizationException;
import cart.mvcconfig.util.BasicAuthDecoder;
import cart.service.MemberService;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    private static final String AUTH_HEADER_KEY = "Authorization";
    private static final String MEMBER_INFO_DELIMITER = ":";

    private final MemberService memberService;

    public AuthInterceptor(final MemberService memberService) {
        this.memberService = memberService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String authValue = request.getHeader(AUTH_HEADER_KEY);
        if (authValue == null) {
            throw new AuthorizationException("AUTH 헤더가 존재하지 않습니다.");
        }

        String decodedMemberInfo = BasicAuthDecoder.decode(authValue);
        List<String> memberInfo = List.of(decodedMemberInfo.split(MEMBER_INFO_DELIMITER));
        String email = memberInfo.get(0);
        String password = memberInfo.get(1);

        memberService.checkMemberExistByMemberInfo(email, password);

        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
}
