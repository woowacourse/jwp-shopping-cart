package cart.config;

import cart.auth.Authentication;
import cart.auth.BasicAuthorizationExtractor;
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

    public AuthenticationArgumentResolver(BasicAuthorizationExtractor basicAuthorizationExtractor) {
        this.basicAuthorizationExtractor = basicAuthorizationExtractor;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(Authentication.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        return basicAuthorizationExtractor.extract(webRequest.getHeader(AUTH_HEADER));
    }
}
