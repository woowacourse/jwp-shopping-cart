package cart.auth;

import cart.dto.auth.AuthInfo;
import cart.exception.AuthorizationException;
import java.util.Base64;
import javax.servlet.http.HttpServletRequest;

public class BasicAuthorizationExtractor implements AuthorizationExtractor<AuthInfo> {
    private static final String TYPE = "Basic";
    private static final String DELIMITER = ":";

    @Override
    public AuthInfo extract(final HttpServletRequest request) {
        String header = request.getHeader(AUTHORIZATION);
        validateNull(header);
        validateBasicType(header);

        String value = header.substring(TYPE.length()).trim();
        byte[] decodedBytes = Base64.getDecoder().decode(value);
        String decodedString = new String(decodedBytes);

        String[] credentials = decodedString.split(DELIMITER);
        return AuthInfo.builder()
                .email(credentials[0])
                .password(credentials[1])
                .build();
    }

    private void validateNull(final String header) {
        if (header == null) {
            throw new AuthorizationException("사용자 인증 정보가 없습니다.");
        }
    }

    private void validateBasicType(final String header) {
        if (!header.toLowerCase().startsWith(TYPE.toLowerCase())) {
            throw new AuthorizationException(TYPE + " 타입의 인증 정보가 필요합니다.");
        }
    }
}
