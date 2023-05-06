package cart.auth;

import cart.dto.request.AuthRequest;
import cart.exception.custom.UnauthorizedException;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;

@Component
public class BasicAuthParser {

    private static final String AUTHORIZATION_HEADER_PREFIX = "Basic ";

    public AuthRequest parse(String authorization) {
        validate(authorization);
        return decodeByBase64(authorization);
    }

    private void validate(String authorization) {
        if (authorization == null) {
            throw new UnauthorizedException("Authorization Header가 존재하지 않습니다.");
        }

        if (!authorization.startsWith(AUTHORIZATION_HEADER_PREFIX)) {
            throw new UnauthorizedException(
                    String.format("Authorization Header는 '%s'로 시작해야 합니다.", AUTHORIZATION_HEADER_PREFIX));
        }
    }

    private AuthRequest decodeByBase64(String authorization) {
        String encodedValue = authorization.replaceFirst(AUTHORIZATION_HEADER_PREFIX, "");
        String[] authValues = new String(Base64Utils.decodeFromString(encodedValue)).split(":");
        return new AuthRequest(authValues);
    }
}
