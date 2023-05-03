package cart.auth;


import cart.dto.LoginDto;
import cart.service.MemberService;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.Base64;

@Component
public class AuthenticationPrincipalArgumentResolver implements HandlerMethodArgumentResolver {

    private final MemberService memberService;

    public AuthenticationPrincipalArgumentResolver(MemberService memberService) {
        this.memberService = memberService;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(AuthenticationPrincipal.class);
    }

    @Override
    public LoginDto resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        // TODO extractor 사용하도록 변경
        String authorization = webRequest.getHeader("Authorization");
        String substring = authorization.substring("Basic ".length());
        String decode = new String(Base64.getDecoder().decode(substring));
        String[] split = decode.split(":");

        return memberService.login(new LoginDto(split[0], split[1]));
    }
}
