package cart.argumentresolver.basicauthorization;

import cart.annotation.BasicAuthorization;
import javax.servlet.http.HttpServletRequest;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public final class BasicAuthorizationArgumentResolver implements HandlerMethodArgumentResolver {

    private static final String AUTH_INFO_ATTRIBUTE_KEY = "AuthInfo";

    @Override
    public boolean supportsParameter(final MethodParameter parameter) {
        return parameter.hasParameterAnnotation(BasicAuthorization.class);
    }

    @Override
    public Object resolveArgument(
        final MethodParameter parameter,
        final ModelAndViewContainer mavContainer,
        final NativeWebRequest webRequest,
        final WebDataBinderFactory binderFactory
    ) {
        final HttpServletRequest httpServletRequest = webRequest.getNativeRequest(HttpServletRequest.class);
        if (httpServletRequest == null) {
            throw new RuntimeException("httpServletRequest 가 존재하지 않습니다.");
        }
        final String authInfo = (String) httpServletRequest.getAttribute(AUTH_INFO_ATTRIBUTE_KEY);
        final BasicAuthorizationExtractor extractor = new BasicAuthorizationExtractor(authInfo);
        return new BasicAuthInfo(extractor.extractUsername(), extractor.extractPassword());
    }
}
