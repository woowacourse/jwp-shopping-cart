package cart.config;

import cart.annotation.Login;
import cart.dto.request.LoginRequest;
import cart.exception.custom.UnauthorizedException;
import cart.service.MemberService;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class LoginArgumentResolver implements HandlerMethodArgumentResolver {

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String AUTHORIZATION_HEADER_PREFIX = "Basic ";

    private final MemberService memberService;

    public LoginArgumentResolver(MemberService memberService) {
        this.memberService = memberService;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(Login.class);
    }

    //TODO 예외처리
    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        String authorization = webRequest.getHeader(AUTHORIZATION_HEADER);

        if (authorization == null) {
            throw new UnauthorizedException("Authorization Header가 존재하지 않습니다.");
        }

        if (!authorization.startsWith(AUTHORIZATION_HEADER_PREFIX)) {
            throw new UnauthorizedException("Authorization Header는 'BASIC ****'과 같은 값으로 전달되어야 합니다.");
        }

        String value = authorization.replaceFirst(AUTHORIZATION_HEADER_PREFIX, "");
        String[] authInfo = new String(Base64Utils.decodeFromString(value)).split(":");

        //TODO 이 객체가 Service를 의존해야하나?, 아니라면 어디서 수행해야 할까?
        String email = authInfo[0];
        String password = authInfo[1];
        memberService.isMatch(email, password);

        //TODO: EMAIL과 PASSWORD가 모두 필요한가? (현재 로직상에는 EMAI만 사용)
        return new LoginRequest(email, password);
    }
}
