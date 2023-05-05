package cart.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public final class BasicAuthArgumentResolver implements HandlerMethodArgumentResolver {
    private static final String AUTHORIZATION = "Authorization";

    @Autowired
    private BasicAuthExtractor basicAuthExtractor;

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
