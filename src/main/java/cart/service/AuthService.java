package cart.service;

import cart.execption.AuthorizationException;
import cart.service.dto.MemberInfo;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private static final String BASIC_TYPE = "Basic ";
    private static final String DELIMITER = ":";


    public MemberInfo extractMemberInfo(final String authInfo) {
        if ((authInfo.toLowerCase().startsWith(BASIC_TYPE.toLowerCase()))) {
            String authHeaderValue = authInfo.substring(BASIC_TYPE.length()).trim();
            byte[] decodedBytes = Base64.decodeBase64(authHeaderValue);
            String decodedString = new String(decodedBytes);

            String[] credentials = decodedString.split(DELIMITER);
            String email = credentials[0];
            String password = credentials[1];

            return new MemberInfo(email, password);
        }
        throw new AuthorizationException("base 64 형태의 인증 정보가 아닙니다.");
    }
}
