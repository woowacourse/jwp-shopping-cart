package cart.auth;

import cart.auth.extractor.AuthorizationExtractor;
import cart.auth.extractor.BasicAuthorizationExtractor;
import cart.dto.auth.AuthInfo;
import cart.exception.AuthorizationException;
import java.util.Arrays;

public enum AuthType {
    BASIC("Basic", new BasicAuthorizationExtractor());

    private final String type;
    private final AuthorizationExtractor<AuthInfo> authorizationExtractor;

    AuthType(final String type, final AuthorizationExtractor<AuthInfo> authorizationExtractor) {
        this.type = type;
        this.authorizationExtractor = authorizationExtractor;
    }

    public static AuthorizationExtractor<AuthInfo> getExtractor(String authorizationValue) {
        validateNull(authorizationValue);
        final AuthType authorizationType = Arrays.stream(AuthType.values())
                .filter(authType -> authorizationValue.startsWith(authType.type))
                .findFirst()
                .orElseThrow(() -> new AuthorizationException("잘못된 인증 방식입니다."));

        return authorizationType.authorizationExtractor;
    }

    private static void validateNull(final String authorizationValue) {
        if (authorizationValue == null) {
            throw new AuthorizationException("사용자 인증 정보가 없습니다.");
        }
    }
}
