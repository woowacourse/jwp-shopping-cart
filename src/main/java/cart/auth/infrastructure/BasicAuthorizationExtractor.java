package cart.auth.infrastructure;

import cart.common.exceptions.AuthorizationException;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.http.HttpHeaders;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

public class BasicAuthorizationExtractor {

    public static final String INVALID_USER_CREDENTIAL_ERROR = "유저 인증 정보가 잘못되었습니다.";
    private static final String BASIC_TYPE = "Basic";
    private static final String DELIMITER = ":";

    public List<String> extract(final HttpServletRequest request) {
        final Optional<String> maybeAuthorizationHeader = this.getAuthorizationHeader(request);
        final Optional<String> mayBeDecodedHeader = this.getDecodedHeader(maybeAuthorizationHeader);
        final List<String> credentials = this.getCredentials(mayBeDecodedHeader);

        if (credentials.size() != 2) {
            throw new AuthorizationException(INVALID_USER_CREDENTIAL_ERROR);
        }

        return credentials;
    }

    private Optional<String> getAuthorizationHeader(final HttpServletRequest request) {
        return Optional.ofNullable(request.getHeader(HttpHeaders.AUTHORIZATION))
                .filter(header -> header.toLowerCase().startsWith(BASIC_TYPE.toLowerCase()))
                .map(header -> header.substring(BASIC_TYPE.length()).trim());
    }

    private Optional<String> getDecodedHeader(final Optional<String> maybeAuthorizationHeader) {
        return maybeAuthorizationHeader
                .map(Base64::decodeBase64)
                .map(String::new);
    }

    private List<String> getCredentials(final Optional<String> mayBeDecodedHeader) {
        return mayBeDecodedHeader
                .map(decodedString -> decodedString.split(DELIMITER))
                .map(List::of)
                .orElseThrow(() -> new AuthorizationException(INVALID_USER_CREDENTIAL_ERROR));
    }
}
