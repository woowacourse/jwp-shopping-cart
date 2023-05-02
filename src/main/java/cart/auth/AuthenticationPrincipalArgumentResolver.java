package cart.auth;


import cart.domain.Member;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.Base64;

public class AuthenticationPrincipalArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(AuthenticationPrincipal.class);
    }

    @Override
    public Member resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        // TODO : MemberId 구현
        String authorization = webRequest.getHeader("Authorization");
        String substring = authorization.substring("Basic ".length());
        String decode = new String(Base64.getDecoder().decode(substring));
        String[] split = decode.split(":");


        return new Member(1L,
                split[0],
                split[1]
        );
    }
}
