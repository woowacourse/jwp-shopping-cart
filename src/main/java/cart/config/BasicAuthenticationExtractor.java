package cart.config;

import cart.dto.MemberAuthDto;
import cart.exception.AuthenticationException;
import java.util.Base64;
import org.springframework.stereotype.Component;

@Component
public class BasicAuthenticationExtractor {

    private static final String BASIC_REGEX = "^Basic [A-Za-z0-9+/]+=*$";
    private static final String AUTHORIZATION_DELIMITER = " ";
    private static final int AUTHORIZATION_VALUE_INDEX = 1;
    private static final String VALUE_DELIMITER = ":";
    private static final int BASIC_AUTH_FIELD_COUNT = 2;

    public MemberAuthDto extract(final String authorization) {
        validateAuthorization(authorization);
        final String encodedAuth = authorization.split(AUTHORIZATION_DELIMITER)[AUTHORIZATION_VALUE_INDEX];
        final String decodedAuth = new String(Base64.getDecoder().decode(encodedAuth));
        final String[] auth = decodedAuth.split(VALUE_DELIMITER);
        validateAuth(auth);
        return new MemberAuthDto(auth[0], auth[1]);
    }

    private void validateAuthorization(final String authorization) {
        if (authorization == null) {
            throw new AuthenticationException("인증 정보가 존재하지 않습니다.");
        }
        if (!authorization.matches(BASIC_REGEX)) {
            throw new AuthenticationException("유효하지 않은 인증 정보 형식입니다.");
        }
    }

    private void validateAuth(final String[] auth) {
        if (auth.length != BASIC_AUTH_FIELD_COUNT) {
            throw new AuthenticationException("유효하지 않은 인증 정보입니다.");
        }
    }
}
