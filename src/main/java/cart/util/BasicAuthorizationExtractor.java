package cart.util;

import cart.dto.AuthInfo;
import cart.exception.AuthException;
import org.apache.tomcat.util.codec.binary.Base64;
import java.util.Arrays;
import java.util.List;

public class BasicAuthorizationExtractor {

    private static final String BASIC_AUTH_TYPE = "BASIC";
    private static final String DELIMITER = ":";
    private static final String AUTH_EXCEPTION_MESSAGE = "인증 정보가 필요합니다.";
    private static final int ONLY_AUTH_TYPE_SIZE = 1;

    public static AuthInfo extract(final String credentials, final String realm) {
        validateBlankOrNull(credentials, realm);

        final List<String> split = Arrays.asList(credentials.split(" "));
        validateIncludeCredential(split, realm);

        final String authType = split.get(0);
        validateAuthType(authType, realm);

        final String credential = split.get(1);
        return toAuthInfo(credential);
    }

    public static void validateBlankOrNull(final String credentials, final String realm) {
        if (credentials == null || credentials.isBlank()) {
            throw new AuthException(AUTH_EXCEPTION_MESSAGE, realm);
        }
    }

    private static void validateIncludeCredential(final List<String> split, final String realm) {
        if (split.size() == ONLY_AUTH_TYPE_SIZE) {
            throw new AuthException(AUTH_EXCEPTION_MESSAGE, realm);
        }
    }

    private static void validateAuthType(final String authType, final String realm) {
        if (!authType.equalsIgnoreCase(BASIC_AUTH_TYPE)) {
            throw new AuthException(AUTH_EXCEPTION_MESSAGE, realm);
        }
    }

    private static AuthInfo toAuthInfo(final String credential) {
        final byte[] decodedBytes = Base64.decodeBase64(credential);
        final String userInfo = new String(decodedBytes);

        final String[] userInfos = userInfo.split(DELIMITER);
        final String email = userInfos[0];
        final String password = userInfos[1];

        return new AuthInfo(email, password);
    }
}
