package cart.auth;

import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public final class BasicAuthArgumentResolver implements HandlerMethodArgumentResolver {
    private static final String AUTHORIZATION = "Authorization";

    private final BasicAuthExtractor basicAuthExtractor;

    public BasicAuthArgumentResolver(final BasicAuthExtractor basicAuthExtractor) {
        this.basicAuthExtractor = basicAuthExtractor;
    }

    @Override
    public boolean supportsParameter(final MethodParameter parameter) {
        return parameter.hasParameterAnnotation(AuthParam.class);
    }

    @Override
    public Object resolveArgument(final MethodParameter parameter, final ModelAndViewContainer mavContainer, final NativeWebRequest webRequest, final WebDataBinderFactory binderFactory) throws Exception {
        String header = webRequest.getHeader(AUTHORIZATION);

        return basicAuthExtractor.extract(header);
    }
}
