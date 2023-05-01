package cart.common.annotation;

import cart.exception.UnAuthorizedException;
import java.util.Base64;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class MemberEmailArgumentResolver implements HandlerMethodArgumentResolver {

    private static final int BASIC_PREFIX_LENGTH = 6;

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
        final String authorization = webRequest.getHeader("Authorization");
        if (authorization == null || authorization.length() < BASIC_PREFIX_LENGTH) {
            throw new UnAuthorizedException();
        }
        final String token = authorization.substring(BASIC_PREFIX_LENGTH);
        final byte[] decodedToken = Base64.getDecoder().decode(token.getBytes());
        final String[] memberToken = new String(decodedToken).split(":");
        return memberToken[0];
    }
}
