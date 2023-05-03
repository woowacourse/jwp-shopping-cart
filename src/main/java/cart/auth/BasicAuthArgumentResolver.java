package cart.auth;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public final class BasicAuthArgumentResolver implements HandlerMethodArgumentResolver {
    private static final String AUTHORIZATION = "Authorization";
    private static final String BASIC_TYPE = "Basic";
    private static final String DELIMITER = ":";

    @Override
    public boolean supportsParameter(final MethodParameter parameter) {
        return parameter.hasParameterAnnotation(AuthParam.class);
    }

    @Override
    public Object resolveArgument(final MethodParameter parameter, final ModelAndViewContainer mavContainer, final NativeWebRequest webRequest, final WebDataBinderFactory binderFactory) throws Exception {
        String header = webRequest.getHeader(AUTHORIZATION);

        if (header == null) {
            return null;
        }

        if ((header.toLowerCase().startsWith(BASIC_TYPE.toLowerCase()))) {
            String authHeaderValue = header.substring(BASIC_TYPE.length()).trim();
            byte[] decodedBytes = Base64.decodeBase64(authHeaderValue);
            String decodedString = new String(decodedBytes);

            String[] credentials = decodedString.split(DELIMITER);

            return new UserInfo(credentials[0], credentials[1]);
        }

        return null;
    }
}
