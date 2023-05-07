package cart.auth;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Component;

@Component
public class BasicAuthorizationParser implements AuthorizationParser<AuthInfo> {

    private static final String BASIC_TYPE = "Basic";
    private static final String DELIMITER = ":";
    private static final int CREDENTAIL_INDEX_SIZE = 2;

    @Override
    public AuthInfo parse(final String requestHeader) {
        String authHeaderValue = getBase64Code(requestHeader);
        byte[] decodedBytes = Base64.decodeBase64(authHeaderValue);
        String decodedString = new String(decodedBytes);

        String[] credentials = getCredentials(decodedString);
        String email = credentials[0];
        String password = credentials[1];

        return new AuthInfo(email, password);
    }

    private String getBase64Code(final String requestHeader) {
        validateHeader(requestHeader);
        validateBasicAuthorizationType(requestHeader);
        return requestHeader.substring(BASIC_TYPE.length()).trim();
    }

    private void validateHeader(final String header) {
        if (header == null || header.isBlank()) {
            throw new IllegalArgumentException("Header 에 인증 정보를 담아주세요.");
        }
    }

    private void validateBasicAuthorizationType(final String header) {
        if (!header.toLowerCase().startsWith(BASIC_TYPE.toLowerCase())) {
            throw new IllegalArgumentException("Basic 타입 인증 요청을 해주세요.");
        }
    }

    private String[] getCredentials(final String decodedString) {
        final String[] credentials = decodedString.split(DELIMITER);

        validateCredentials(credentials);
        return credentials;
    }

    private void validateCredentials(final String[] credentials) {
        if (credentials.length != CREDENTAIL_INDEX_SIZE) {
            throw new IllegalArgumentException("올바른 base64 인증정보를 입력하세요");
        }
    }
}
