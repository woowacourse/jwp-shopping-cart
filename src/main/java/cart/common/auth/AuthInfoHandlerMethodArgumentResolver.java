package cart.common.auth;

import cart.domain.auth.service.AuthorizationExtractor;
import cart.dto.MemberInformation;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class AuthInfoHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {

    private static final String AUTHORIZATION = "Authorization";

    private final AuthorizationExtractor authorizationExtractor;

    public AuthInfoHandlerMethodArgumentResolver(
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
