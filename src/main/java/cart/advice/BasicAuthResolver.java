package cart.advice;

import static org.apache.tomcat.util.codec.binary.Base64.decodeBase64;

import cart.dto.AuthPayload;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class BasicAuthResolver implements HandlerMethodArgumentResolver {

    private static final String AUTHORIZATION = "Authorization";
    private static final String BASIC_TYPE = "Basic";
    private static final String DELIMITER = ":";
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(BasicAuth.class);
    }

    @Override
    public AuthPayload resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                       NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        String authorization = webRequest.getHeader(AUTHORIZATION);

        if (isBasicAuthHeader(authorization)) {
            String decodedString = decodeBasicAuth(authorization);
            return extractPayload(decodedString);
        }
        return null;
    }

    private boolean isBasicAuthHeader(String authorization) {
        return authorization.toLowerCase().startsWith(BASIC_TYPE.toLowerCase());
    }

    private String decodeBasicAuth(String authorization) {
        String authHeaderValue = authorization.substring(BASIC_TYPE.length()).trim();
        byte[] decodedBytes = decodeBase64(authHeaderValue);
        String decodedString = new String(decodedBytes);
        return decodedString;
    }

    private AuthPayload extractPayload(String decodedString) {
        String[] credentials = decodedString.split(DELIMITER);
        String email = credentials[0];
        String password = credentials[1];
        return new AuthPayload(email, password);
    }
}
