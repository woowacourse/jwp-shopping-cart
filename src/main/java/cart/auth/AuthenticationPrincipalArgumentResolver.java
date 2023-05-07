package cart.auth;


import cart.dto.LoginDto;
import cart.service.MemberService;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;

@Component
public class AuthenticationPrincipalArgumentResolver implements HandlerMethodArgumentResolver {

    private final BasicAuthorizationHeaderExtractor extractor;
    private final MemberService memberService;


    public AuthenticationPrincipalArgumentResolver(BasicAuthorizationHeaderExtractor extractor, MemberService memberService) {
        this.extractor = extractor;
        this.memberService = memberService;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(AuthenticationPrincipal.class);
    }

    @Override
    public LoginDto resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) webRequest.getNativeRequest();
        LoginDto extract = extractor.extract(httpServletRequest);

        if (extract == null) {
            throw new AuthorizationException();
        }

        return memberService.findMemberByLoginDto(extract)
                .orElseThrow(AuthorizationException::new);
    }
}
