package cart.auth;

import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class AuthenticationArgumentResolver implements HandlerMethodArgumentResolver {

    private final AuthenticationExtractor<MemberAuthentication> authenticationExtractor;
    private final AuthenticationService authenticationService;

    public AuthenticationArgumentResolver(final AuthenticationExtractor<MemberAuthentication> authenticationExtractor, final AuthenticationService authenticationService) {
        this.authenticationExtractor = authenticationExtractor;
        this.authenticationService = authenticationService;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(AuthenticationPrincipal.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        MemberAuthentication memberAuthentication = authenticationExtractor.extract(webRequest);
        return authenticationService.login(memberAuthentication);
    }

}
