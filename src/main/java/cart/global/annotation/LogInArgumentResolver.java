package cart.global.annotation;

import cart.auth.AuthAccount;
import cart.global.exception.auth.InvalidAuthorizationTypeException;
import cart.global.exception.auth.InvalidEmailFormatException;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.regex.Pattern;

@Component
public class LogInArgumentResolver implements HandlerMethodArgumentResolver {

    private static final String BASIC_TYPE = "Basic";
    private static final String DELIM = ":";
    private static final String EMAIL_REGEX = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";

    @Override
    public boolean supportsParameter(final MethodParameter parameter) {
        return parameter.hasParameterAnnotation(LogIn.class) &&
                AuthAccount.class.isAssignableFrom(parameter.getParameterType());
    }

    @Override
    public Object resolveArgument(final MethodParameter parameter,
                                  final ModelAndViewContainer mavContainer,
                                  final NativeWebRequest webRequest,
                                  final WebDataBinderFactory binderFactory
    ) throws Exception {

        final String header = webRequest.getHeader(HttpHeaders.AUTHORIZATION);

        if (isBasicAuthFrom(header)) {
            final String[] credentials = decodingByBase64(header).split(DELIM);

            final String email = credentials[0];
            checkEmailFormat(email);
            final String password = credentials[1];

            return new AuthAccount(email, password);
        }

        throw new InvalidAuthorizationTypeException();
    }

    private boolean isBasicAuthFrom(final String header) {
        return header.toLowerCase().startsWith((BASIC_TYPE.toLowerCase()));
    }

    private String decodingByBase64(final String header) {
        final String authHeaderValue = header.substring(BASIC_TYPE.length()).trim();
        final byte[] decodedBytes = Base64.getDecoder().decode(authHeaderValue);

        return new String(decodedBytes, StandardCharsets.UTF_8);
    }

    private void checkEmailFormat(final String email) {
        if (!Pattern.compile(EMAIL_REGEX)
                    .matcher(email)
                    .matches()) {
            throw new InvalidEmailFormatException("이메일 형식이 올바르지 않습니다");
        }
    }
}
