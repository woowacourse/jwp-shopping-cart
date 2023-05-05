package cart.web.argumentResolver;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import cart.domain.member.AuthService;
import cart.web.cart.dto.AuthInfo;
import cart.web.interceptor.AuthorizationExtractor;

public class AuthenticationArgumentResolver implements HandlerMethodArgumentResolver {

    private final AuthorizationExtractor<AuthInfo> authorizationExtractor;
    private final AuthService authService;

    public AuthenticationArgumentResolver(final AuthorizationExtractor<AuthInfo> authorizationExtractor,
        final AuthService authService) {
        this.authorizationExtractor = authorizationExtractor;
        this.authService = authService;
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
        final long memberId = authService.getValidatedMemberId(email, password);
        return new AuthorizedMember(memberId, email, password);
    }
}
