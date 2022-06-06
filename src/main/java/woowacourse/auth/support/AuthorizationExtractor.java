package woowacourse.auth.support;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import woowacourse.auth.exception.InvalidTokenException;

@Component
public class AuthorizationExtractor {

    public static final String BEARER_TYPE = "Bearer ";

    public String extract(HttpServletRequest request) {
        final String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        validateExists(header);
        validateStartsWithBearer(header);
        return header.substring(BEARER_TYPE.length());
    }

    private void validateExists(String header) {
        if (header == null) {
            throw new InvalidTokenException();
        }
    }

    private void validateStartsWithBearer(String header) {
        if (!header.startsWith(BEARER_TYPE)) {
            throw new InvalidTokenException();
        }
    }
}
