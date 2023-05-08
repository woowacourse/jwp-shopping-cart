package cart.auth;

import cart.domain.member.dto.MemberInformation;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class AuthInfoArgumentResolver implements HandlerMethodArgumentResolver {

    private static final String AUTHORIZATION = "Authorization";

    private final AuthorizationExtractor authorizationExtractor;

    public AuthInfoArgumentResolver(
        final AuthorizationExtractor authorizationExtractor) {
        this.authorizationExtractor = authorizationExtractor;
    }

    @Override
    public boolean supportsParameter(final MethodParameter parameter) {
        return parameter.getParameterType().equals(MemberInformation.class)
            && parameter.hasParameterAnnotation(AuthInfo.class);
    }

    @Override
    public Object resolveArgument(final MethodParameter parameter,
        final ModelAndViewContainer mavContainer,
        final NativeWebRequest webRequest, final WebDataBinderFactory binderFactory) {
        return authorizationExtractor.extract(webRequest.getHeader(AUTHORIZATION));
    }
}
