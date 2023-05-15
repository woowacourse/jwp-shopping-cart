package cart.auth;

import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class AuthArgumentResolver implements HandlerMethodArgumentResolver {

    private final BasicAuthorizationExtractor basicAuthorizationExtractor;

    public AuthArgumentResolver(final BasicAuthorizationExtractor basicAuthorizationExtractor) {
        this.basicAuthorizationExtractor = basicAuthorizationExtractor;
    }

    @Override
    public boolean supportsParameter(final MethodParameter parameter) {
        return parameter.hasParameterAnnotation(Auth.class);
    }

    @Override
    public AuthInfo resolveArgument(final MethodParameter parameter,
                                    final ModelAndViewContainer mavContainer,
                                    final NativeWebRequest webRequest,
                                    final WebDataBinderFactory binderFactory) {
        String header = webRequest.getHeader(HttpHeaders.AUTHORIZATION);
        AuthInfo authInfo = basicAuthorizationExtractor.extract(header);

        return authInfo;
    }
}
