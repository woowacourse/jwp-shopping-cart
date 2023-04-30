package cart.common.annotation;

import cart.exception.UnAuthorizedException;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.Base64;

public class MemberEmailArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(final MethodParameter parameter) {
        return parameter.hasParameterAnnotation(MemberEmail.class) &&
                parameter.getParameterType().isAssignableFrom(String.class);
    }

    @Override
    public String resolveArgument(final MethodParameter parameter, final ModelAndViewContainer mavContainer,
                                  final NativeWebRequest webRequest, final WebDataBinderFactory binderFactory) throws Exception {
        final String authorization = webRequest.getHeader("Authorization");
        if (authorization == null) {
            throw new UnAuthorizedException();
        }
        final String token = authorization.substring(6);
        final String[] memberToken = new String(Base64.getDecoder().decode(token.getBytes())).split(":");
        return memberToken[0];
    }
}
