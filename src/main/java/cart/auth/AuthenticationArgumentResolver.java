package cart.auth;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class AuthenticationArgumentResolver implements HandlerMethodArgumentResolver {

    private static final String AUTHORIZATION = "Authorization";
    private static final String BASIC_TYPE = "Basic";
    private static final String DELIMITER = ":";
    private static final int EMAIL_INDEX = 0;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(Auth.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory)
            throws UnauthorizedException {
        String authHeader = webRequest.getHeader(AUTHORIZATION);

        if (authHeader == null) {
            throw new UnauthorizedException();
        }

        if ((authHeader.toLowerCase().startsWith(BASIC_TYPE.toLowerCase()))) {
            String authValue = authHeader.substring(BASIC_TYPE.length()).trim();
            byte[] decodedBytes = Base64.decodeBase64(authValue);
            String decodedString = new String(decodedBytes);
            String[] credentials = decodedString.split(DELIMITER);
            return credentials[EMAIL_INDEX];
        }
        throw new UnauthorizedException();
    }
}
