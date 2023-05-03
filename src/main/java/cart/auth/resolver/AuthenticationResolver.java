package cart.auth.resolver;

import static org.apache.tomcat.util.codec.binary.Base64.decodeBase64;

import cart.auth.info.AuthInfo;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;


public class AuthenticationResolver implements HandlerMethodArgumentResolver {

    private static final String AUTHORIZATION = "Authorization";
    private static final String BASIC_TYPE = "Basic";
    private static final int EMAIL_INDEX = 0;

    @Override
    public boolean supportsParameter(final MethodParameter parameter) {
        return parameter.hasParameterAnnotation(Authentication.class);
    }

    @Override
    public Object resolveArgument(final MethodParameter parameter, final ModelAndViewContainer mavContainer,
            final NativeWebRequest webRequest, final WebDataBinderFactory binderFactory) {
        final String header = webRequest.getHeader(AUTHORIZATION);

        if (header == null || !header.toLowerCase().startsWith(BASIC_TYPE.toLowerCase())) {
            throw new IllegalArgumentException();
        }

        final String authHeaderValue = header.substring(BASIC_TYPE.length()).trim();

        final String[] credentials = new String(decodeBase64(authHeaderValue)).split(":");
        return new AuthInfo(credentials[EMAIL_INDEX]);
    }
}
