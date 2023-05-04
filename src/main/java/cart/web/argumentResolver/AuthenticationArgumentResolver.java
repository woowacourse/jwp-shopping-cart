package cart.web.argumentResolver;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import cart.domain.member.MemberService;
import cart.web.cart.controller.AuthorizationExtractor;
import cart.web.cart.dto.AuthInfo;

public class AuthenticationArgumentResolver implements HandlerMethodArgumentResolver {

    private final AuthorizationExtractor<AuthInfo> authorizationExtractor;
    private final MemberService memberService;

    public AuthenticationArgumentResolver(final AuthorizationExtractor<AuthInfo> authorizationExtractor,
        final MemberService memberService) {
        this.authorizationExtractor = authorizationExtractor;
        this.memberService = memberService;
    }

    @Override
    public boolean supportsParameter(final MethodParameter parameter) {
        return parameter.hasParameterAnnotation(AuthorizeMember.class);
    }

    @Override
    public Object resolveArgument(final MethodParameter parameter, final ModelAndViewContainer mavContainer,
        final NativeWebRequest webRequest, final WebDataBinderFactory binderFactory
    ) {
        final HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);

        final AuthInfo authInfo = authorizationExtractor.extract(request);
        final String email = authInfo.getEmail();
        final String password = authInfo.getPassword();
        final long memberId = memberService.getValidatedMemberId(email, password);
        return new AuthorizedMember(memberId, email, password);
    }
}
