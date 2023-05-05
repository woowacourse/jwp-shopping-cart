package cart.config;

import cart.dto.MemberAuthDto;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class MemberArgumentResolver implements HandlerMethodArgumentResolver {

    private static final String AUTHORIZATION_HEADER_NAME = "Authorization";

    private final BasicAuthenticationExtractor basicAuthenticationExtractor;

    public MemberArgumentResolver(final BasicAuthenticationExtractor basicAuthenticationExtractor) {
        this.basicAuthenticationExtractor = basicAuthenticationExtractor;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(MemberAuthDto.class) &&
                parameter.hasParameterAnnotation(Member.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        final String authorization = webRequest.getHeader(AUTHORIZATION_HEADER_NAME);
        return basicAuthenticationExtractor.extract(authorization);
    }
}
