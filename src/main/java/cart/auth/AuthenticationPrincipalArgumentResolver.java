package cart.auth;

import static cart.auth.extractor.AuthorizationExtractor.AUTHORIZATION;

import cart.auth.extractor.AuthorizationExtractor;
import cart.dto.MemberDto;
import cart.dto.auth.AuthInfo;
import cart.service.MemberService;
import javax.servlet.http.HttpServletRequest;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class AuthenticationPrincipalArgumentResolver implements HandlerMethodArgumentResolver {

    private final MemberService memberService;

    public AuthenticationPrincipalArgumentResolver(final MemberService memberService) {
        this.memberService = memberService;
    }

    @Override
    public boolean supportsParameter(final MethodParameter parameter) {
        return parameter.hasParameterAnnotation(AuthenticationPrincipal.class);
    }

    @Override
    public MemberDto resolveArgument(final MethodParameter parameter, final ModelAndViewContainer mavContainer,
                                     final NativeWebRequest webRequest, final WebDataBinderFactory binderFactory) {
        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        String header = request.getHeader(AUTHORIZATION);
        AuthorizationExtractor<AuthInfo> extractor = AuthType.getExtractor(header);
        AuthInfo authInfo = extractor.extract(request);

        return memberService.findMember(authInfo);
    }
}
