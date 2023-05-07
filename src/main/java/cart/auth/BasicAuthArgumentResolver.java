package cart.auth;

import cart.exception.AuthorizationException;
import cart.exception.ErrorCode;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class BasicAuthArgumentResolver implements HandlerMethodArgumentResolver {
    private static final String BASIC_TYPE = "Basic";
    private static final String DELIMITER = ":";
    public static final String AUTHORIZATION = "Authorization";

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(BasicAuth.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        String header = webRequest.getHeader(AUTHORIZATION);

        if (header == null) {
            throw new AuthorizationException(ErrorCode.AUTHORIZATION_NOT_FOUND);
        }

        String authHeaderValue = header.substring(BASIC_TYPE.length()).trim();

        byte[] decodedBytes = Base64.decodeBase64(authHeaderValue);
        String decodedString = new String(decodedBytes);

        String[] credentials = decodedString.split(DELIMITER);
        String email = credentials[0];
        String password = credentials[1];

        return new AuthInfo(email, password);
    }
}
