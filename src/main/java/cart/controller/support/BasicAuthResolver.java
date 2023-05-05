package cart.controller.support;

import static org.apache.tomcat.util.codec.binary.Base64.decodeBase64;

import cart.dto.BasicCredentials;
import java.util.Optional;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class BasicAuthResolver implements HandlerMethodArgumentResolver {

    private static final String AUTH_HEADER_NAME = "Authorization";
    private static final String AUTHORIZATION_SCHEME_BASIC = "Basic";
    private static final String DELIMITER = ":";

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(BasicAuth.class);
    }

    @Override
    public BasicCredentials resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                            NativeWebRequest webRequest, WebDataBinderFactory binderFactory)
            throws Exception {
        String authorizationHeader = getAuthorizationHeader(parameter, webRequest);
        if (isBasicAuthHeader(authorizationHeader)) {
            return decodeBasicCredentials(authorizationHeader);
        }
        throw new IllegalArgumentException("올바른 인증방식을 사용해주세요.");
    }

    private String getAuthorizationHeader(MethodParameter parameter, NativeWebRequest webRequest)
            throws MissingRequestHeaderException {
        return Optional.ofNullable(webRequest.getHeader(AUTH_HEADER_NAME))
                .orElseThrow(() -> new MissingRequestHeaderException(AUTH_HEADER_NAME, parameter));
    }

    private boolean isBasicAuthHeader(String authorization) {
        return authorization.toLowerCase().startsWith(AUTHORIZATION_SCHEME_BASIC.toLowerCase());
    }

    private BasicCredentials decodeBasicCredentials(String authorizationHeader) {
        String credentials = authorizationHeader.substring(AUTHORIZATION_SCHEME_BASIC.length()).trim();
        String[] splitCredentials = new String(decodeBase64(credentials)).split(DELIMITER);
        return new BasicCredentials(splitCredentials[0], splitCredentials[1]);
    }
}
