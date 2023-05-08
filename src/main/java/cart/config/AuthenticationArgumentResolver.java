package cart.config;

import cart.auth.Authentication;
import cart.auth.BasicAuthorizationExtractor;
import cart.controller.dto.MemberDto;
import cart.service.MemberService;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class AuthenticationArgumentResolver implements HandlerMethodArgumentResolver {

    private static final String AUTH_HEADER = "Authorization";

    private final BasicAuthorizationExtractor basicAuthorizationExtractor;
    private final MemberService memberService;

    public AuthenticationArgumentResolver(BasicAuthorizationExtractor basicAuthorizationExtractor,
                                          MemberService memberService) {
        this.basicAuthorizationExtractor = basicAuthorizationExtractor;
        this.memberService = memberService;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(Authentication.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        MemberDto memberDto = basicAuthorizationExtractor.extract(webRequest.getHeader(AUTH_HEADER));

        return memberService.findByEmailAndPassword(memberDto.getEmail(), memberDto.getPassword())
                .getId();
    }
}
