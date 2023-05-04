package cart.config;

import cart.auth.Authentication;
import cart.controller.dto.MemberRequest;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class AuthenticationArgumentResolver implements HandlerMethodArgumentResolver {

    private static final String AUTH_HEADER = "Authorization";
    private static final String AUTH_PREFIX = "Basic";
    private static final String DELIMITER = ":";
    private static final int NAME_INDEX = 0;
    private static final int EMAIL_INDEX = 1;
    private static final int PASSWORD_INDEX = 2;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(Authentication.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        String header = webRequest.getHeader(AUTH_HEADER);
        String decodedValue = header.substring(AUTH_PREFIX.length()).trim();
        byte[] decodedBytes = Base64.decodeBase64(decodedValue);
        String decodedString = new String(decodedBytes);

        String[] credentials = decodedString.split(DELIMITER);

        return new MemberRequest(credentials[EMAIL_INDEX], credentials[PASSWORD_INDEX], credentials[NAME_INDEX]);
    }
}
