package cart.auth.interceptor;

import cart.exception.UnsupportedAuthenticationException;
import java.util.Arrays;
import java.util.Base64;
import java.util.function.Function;

enum AuthorizationType {
    BASIC("BASIC",
        (authorization) -> {
            final String encodedCredential = authorization.split(" ")[1];
            return new String(Base64.getDecoder().decode(encodedCredential));
        }
    );

    private final String type;
    private final Function<String, String> decoder;

    AuthorizationType(final String type, final Function<String, String> decoder) {
        this.type = type;
        this.decoder = decoder;
    }

    public static AuthorizationType findType(final String authorizationContent) {
        final String searchingType = authorizationContent.split(" ")[0].toUpperCase();
        return Arrays.stream(values())
            .filter((headerType) -> headerType.type.equals(searchingType))
            .findFirst().orElseThrow(() -> new UnsupportedAuthenticationException("지원하지 않는 인증 방식 입니다."));
    }

    public String decode(final String authorizationContent) {
        return this.decoder.apply(authorizationContent);
    }
}
