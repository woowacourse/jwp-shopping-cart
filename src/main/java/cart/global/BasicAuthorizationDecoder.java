package cart.global;

import cart.domain.account.AuthAccount;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class BasicAuthorizationDecoder {

    private static final String BASIC_TYPE = "Basic";
    private static final String DELIM = ":";
    private static final String AUTHORIZATION_HEADER = "Authorization";

    public static AuthAccount decode(HttpServletRequest request) {
        final String header = request.getHeader(AUTHORIZATION_HEADER);

        if (hasNotHeader(header)) {
            throw new IllegalStateException("인증 정보 존재하지 않습니다.");
        }

        if (isBasicAuthFrom(header)) {
            final String decodedString = decodingByBase64(header);
            final String[] credentials = decodedString.split(DELIM);

            final String email = credentials[0];
            final String password = credentials[1];

            return new AuthAccount(email, password);
        }

        throw new IllegalStateException("인증 형태가 올바르지 않습니다.");
    }

    private static String decodingByBase64(final String header) {
        final String authHeaderValue = header.substring(BASIC_TYPE.length()).trim();
        final byte[] decodeBytes = Base64.getDecoder().decode(authHeaderValue);

        return new String(decodeBytes, StandardCharsets.UTF_8);
    }

    private static boolean hasNotHeader(final String header) {
        return header == null;
    }

    private static boolean isBasicAuthFrom(final String header) {
        return header.toLowerCase().startsWith((BASIC_TYPE.toLowerCase()));
    }
}
