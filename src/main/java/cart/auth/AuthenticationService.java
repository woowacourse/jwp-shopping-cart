package cart.auth;

import cart.member.dao.MemberDao;
import cart.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private static final String BASIC_TYPE = "Basic";
    private static final String DELIMITER = ":";
    private static final int EMAIL_INDEX = 0;
    private static final int PASSWORD_INDEX = 1;

    private final MemberDao memberDao;

    public boolean canLogin(final String email, final String password) {
        final Member member = memberDao.findByEmailAndPassword(email, password);
        if (member != null) {
            return true;
        }

        return false;
    }

    public AuthInfo decryptBasic(final String header) {
        String authHeaderValue = header.substring(BASIC_TYPE.length()).trim();
        byte[] decodedBytes = Base64.decodeBase64(authHeaderValue);
        String decodedString = new String(decodedBytes);

        String[] credentials = decodedString.split(DELIMITER);
        String email = credentials[EMAIL_INDEX];
        String password = credentials[PASSWORD_INDEX];

        return new AuthInfo(email, password);
    }
}
