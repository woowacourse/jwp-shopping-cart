package cart.mvcconfig.argumentresolver;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

import cart.dto.AuthInfo;
import cart.exception.AuthorizationException;
import cart.mvcconfig.annotation.AuthenticationPrincipal;
import javax.servlet.http.HttpServletRequest;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class AuthenticationPrincipalArgumentResolver implements HandlerMethodArgumentResolver {

    private static final String BASIC_TYPE = "Basic";
    private static final String DELIMITER = ":";
    private static final int CREDENTIALS_SIZE = 2;

    @Override
    public boolean supportsParameter(final MethodParameter parameter) {
        return parameter.getParameterType().equals(AuthInfo.class) &&
                parameter.hasParameterAnnotation(AuthenticationPrincipal.class);
    }

    @Override
    public Object resolveArgument(final MethodParameter parameter, final ModelAndViewContainer mavContainer,
                                  final NativeWebRequest webRequest, final WebDataBinderFactory binderFactory) {
        final HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        String header = request.getHeader(AUTHORIZATION);
        validateHeader(header);

        String authHeaderValue = header.substring(BASIC_TYPE.length()).trim();
        byte[] decodedBytes = Base64.decodeBase64(authHeaderValue);
        String decodedString = new String(decodedBytes);

        String[] credentials = decodedString.split(DELIMITER);
        validateCredentials(credentials);

        String email = credentials[0];
        String password = credentials[1];

        return new AuthInfo(email, password);
    }

    private void validateHeader(final String header) {
        if (header == null || !header.toLowerCase().startsWith(BASIC_TYPE.toLowerCase())) {
            throw new AuthorizationException("유효하지 않은 인증 정보입니다.");
        }
    }

    private void validateCredentials(final String[] credentials) {
        if (credentials.length != CREDENTIALS_SIZE) {
            throw new AuthorizationException("유효하지 않은 인증 정보입니다.");
        }
    }
}
