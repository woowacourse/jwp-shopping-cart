package cart.interceptor;

import cart.exception.AuthException;
import org.springframework.web.servlet.HandlerInterceptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;

public class AuthValidateInterceptor implements HandlerInterceptor {

    private static final String AUTH_EXCEPTION_MESSAGE = "인증 정보가 필요합니다.";
    private static final String BASIC_AUTH_TYPE = "BASIC";
    private static final int ONLY_AUTH_TYPE_SIZE = 1;
    private static final int AUTH_TYPE_INDEX = 0;

    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler) throws Exception {
        final String credentials = request.getHeader("Authorization");
        final String realm = request.getRequestURI();

        validateBlankOrNull(credentials, realm);

        final List<String> split = Arrays.asList(credentials.split(" "));
        validateIncludeValue(split, realm);

        final String authType = split.get(AUTH_TYPE_INDEX);
        validateAuthType(authType, realm);

        return true;
    }

    public void validateBlankOrNull(final String credentials, final String realm) {
        if (credentials == null || credentials.isBlank()) {
            throw new AuthException(AUTH_EXCEPTION_MESSAGE, realm);
        }
    }

    private void validateIncludeValue(final List<String> split, final String realm) {
        if (split.size() == ONLY_AUTH_TYPE_SIZE) {
            throw new AuthException(AUTH_EXCEPTION_MESSAGE, realm);
        }
    }

    private void validateAuthType(final String authType, final String realm) {
        if (!authType.equalsIgnoreCase(BASIC_AUTH_TYPE)) {
            throw new AuthException(AUTH_EXCEPTION_MESSAGE, realm);
        }
    }
}
