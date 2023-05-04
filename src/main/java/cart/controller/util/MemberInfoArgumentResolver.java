package cart.controller.util;

import cart.exception.AuthTypeNonBasicException;
import cart.exception.AuthorizationException;
import cart.service.dto.MemberInfo;
import javax.servlet.http.HttpServletRequest;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class MemberInfoArgumentResolver implements HandlerMethodArgumentResolver {

    private static final String BASIC_AUTH_PREFIX = "Basic";
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String DELIMITER = ":";

    @Override
    public boolean supportsParameter(final MethodParameter parameter) {
        return parameter.hasParameterAnnotation(MemberInfoPrincipal.class);
    }

    @Override
    public Object resolveArgument(final MethodParameter parameter, final ModelAndViewContainer mavContainer,
                                  final NativeWebRequest webRequest, final WebDataBinderFactory binderFactory) {
        final HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();

        final String credential = request.getHeader(AUTHORIZATION_HEADER);

        validateCredential(credential);

        final String authHeaderValue = credential.substring(BASIC_AUTH_PREFIX.length()).trim();
        final byte[] decodedBytes = Base64.decodeBase64(authHeaderValue);
        final String decodedString = new String(decodedBytes);

        final String[] credentials = decodedString.split(DELIMITER);
        final String email = credentials[0];
        final String password = credentials[1];

        return new MemberInfo(email, password);
    }

    private static void validateCredential(final String credential) {
        if (credential == null) {
            throw new AuthorizationException();
        }
        if (!credential.startsWith(BASIC_AUTH_PREFIX)) {
            throw new AuthTypeNonBasicException();
        }
    }
}
