package cart.common.auth;

import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class MemberEmailArgumentResolver implements HandlerMethodArgumentResolver {

    private static final String DELIMITER = ":";
    private static final String AUTHORIZATION_HEADER = "Authorization";

    @Override
    public boolean supportsParameter(final MethodParameter parameter) {
        return parameter.hasParameterAnnotation(MemberEmail.class) &&
            parameter.getParameterType().isAssignableFrom(String.class);
    }

    @Override
    public String resolveArgument(final MethodParameter parameter,
                                  final ModelAndViewContainer mavContainer,
                                  final NativeWebRequest webRequest,
                                  final WebDataBinderFactory binderFactory) {
        final String authorization = webRequest.getHeader(AUTHORIZATION_HEADER);
        final String memberToken = BasicTokenProvider.extractToken(authorization);
        return memberToken.split(DELIMITER)[0];
    }
}
