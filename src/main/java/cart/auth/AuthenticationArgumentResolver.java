package cart.auth;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class AuthenticationArgumentResolver implements HandlerMethodArgumentResolver {
    private final String AUTHORIZATION = "Authorization";

    private final BasicAuthorizationExtractor basicAuthorizationExtractor = new BasicAuthorizationExtractor();

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.withContainingClass(AuthInfo.class)
                .hasParameterAnnotation(AuthenticationPrincipal.class);
    }

    @Override
    public AuthInfo resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        String header = webRequest.getHeader(AUTHORIZATION);
        return basicAuthorizationExtractor.extract(header);
    }

}
