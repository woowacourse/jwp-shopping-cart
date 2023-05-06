package cart.auth;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.regex.Pattern;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class AuthenticationArgumentResolver implements HandlerMethodArgumentResolver {

    private static final String AUTHORIZATION = "Authorization";
    private static final String BASIC_PREFIX = "Basic ";
    private static final String BASIC_DELIMITER = ":";
    private static final String EMPTY = "";
    private static final Pattern BASIC_CREDENTIAL_PATTERN = Pattern.compile("^Basic [A-Za-z0-9+/]+=*$");

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.withContainingClass(AuthMember.class)
                .hasParameterAnnotation(AuthPrincipal.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        String authorization = webRequest.getHeader(AUTHORIZATION);
        validateCredentials(authorization);
        String[] emailAndName = extractBasicAuthInfo(authorization);
        return getMember(emailAndName);
    }

    private void validateCredentials(String authorization) {
        validateNull(authorization);
        validateBasicAuth(authorization);
    }

    private void validateNull(String authorization) {
        if (authorization == null || authorization.isBlank()) {
            throw new AuthenticationException();
        }
    }

    private void validateBasicAuth(String authorization) {
        if (!BASIC_CREDENTIAL_PATTERN.matcher(authorization).matches()) {
            throw new AuthenticationException();
        }
    }

    private String[] extractBasicAuthInfo(String authorization) {
        String credentials = authorization.replace(BASIC_PREFIX, EMPTY);
        byte[] decodedBytes = Base64.getDecoder().decode(credentials);
        String decodedString = new String(decodedBytes, StandardCharsets.UTF_8);
        return decodedString.split(BASIC_DELIMITER);
    }

    private AuthMember getMember(String[] emailAndName) {
        if (emailAndName.length != 2) {
            throw new AuthenticationException();
        }
        String email = emailAndName[0];
        String password = emailAndName[1];
        return new AuthMember(email, password);
    }
}
