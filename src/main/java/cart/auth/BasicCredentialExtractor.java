package cart.auth;

import cart.exeception.AuthorizationException;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Component;

@Component
public class BasicCredentialExtractor {

    private static final String BASIC_TYPE = "Basic ";
    private static final String DELIMITER = ":";
    private static final int EMAIL_INDEX = 0;
    private static final int PASSWORD_INDEX = 1;

    public Credential extractMemberInfo(final String authInfo) {
        if ((authInfo.startsWith(BASIC_TYPE))) {
            final String authHeaderValue = authInfo.substring(BASIC_TYPE.length()).trim();
            final String decodedAuthValue = new String(Base64.decodeBase64(authHeaderValue));
            final String[] credentials = decodedAuthValue.split(DELIMITER);
            return new Credential(credentials[EMAIL_INDEX], credentials[PASSWORD_INDEX]);
        }
        throw new AuthorizationException("base 64 형태의 인증 정보가 아닙니다.");
    }
}
