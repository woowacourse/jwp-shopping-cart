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
        final String authorization = httpServletRequest.getHeader("Authorization");
        final BasicAuthorizationExtractor extractor = new BasicAuthorizationExtractor(authorization);
        return new BasicAuthInfo(extractor.extractEmail(), extractor.extractPassword());
    }
}
