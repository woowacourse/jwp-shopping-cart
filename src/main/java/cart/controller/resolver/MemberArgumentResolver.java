package cart.controller.resolver;

import cart.config.BasicAuthenticationExtractor;
import cart.dto.MemberAuthDto;
import cart.entity.MemberEntity;
import cart.service.MemberService;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class MemberArgumentResolver implements HandlerMethodArgumentResolver {

    private static final String AUTHORIZATION_HEADER_NAME = "Authorization";

    private final BasicAuthenticationExtractor basicAuthenticationExtractor;
    private final MemberService memberService;

    public MemberArgumentResolver(
            final BasicAuthenticationExtractor basicAuthenticationExtractor,
            final MemberService memberService
    ) {
        this.basicAuthenticationExtractor = basicAuthenticationExtractor;
        this.memberService = memberService;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(Long.class) &&
                parameter.hasParameterAnnotation(MemberId.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        final String authorization = webRequest.getHeader(AUTHORIZATION_HEADER_NAME);
        final MemberAuthDto memberAuthDto = basicAuthenticationExtractor.extract(authorization);
        final MemberEntity member = memberService.findMember(memberAuthDto);
        return member.getId();
    }
}
