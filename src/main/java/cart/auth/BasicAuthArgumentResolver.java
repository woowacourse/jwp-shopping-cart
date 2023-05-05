package cart.auth;

import cart.global.exception.auth.UnsupportedAuthorizationException;
import javax.servlet.http.HttpServletRequest;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class BasicAuthArgumentResolver implements HandlerMethodArgumentResolver {

    private static final String BASIC_TYPE = "Basic";
    private static final String DELIMITER = ":";
    private static final int EMAIL_INDEX = 0;
    private static final int PASSWORD_INDEX = 1;

    @Override
    public boolean supportsParameter(final MethodParameter parameter) {
        final boolean hasParameterAnnotation = parameter.hasParameterAnnotation(Auth.class);
        final boolean hasCredentialType = Credential.class.isAssignableFrom(parameter.getParameterType());
        return hasParameterAnnotation && hasCredentialType;
    }

    @Override
    public Object resolveArgument(
            final MethodParameter parameter,
            final ModelAndViewContainer mavContainer,
            final NativeWebRequest webRequest,
            final WebDataBinderFactory binderFactory
    ) {
        final HttpServletRequest httpServletRequest = (HttpServletRequest) webRequest.getNativeRequest();
        final String authorization = httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION);

        if ((authorization.toLowerCase().startsWith(BASIC_TYPE.toLowerCase()))) {
            final String authHeaderValue = authorization.substring(BASIC_TYPE.length()).trim();
            byte[] decodedBytes = Base64.decodeBase64(authHeaderValue);

            final String decodedString = new String(decodedBytes);
            final String[] credentials = decodedString.split(DELIMITER);

            return new Credential(credentials[EMAIL_INDEX], credentials[PASSWORD_INDEX]);
        }

        throw new UnsupportedAuthorizationException();
    }
}

