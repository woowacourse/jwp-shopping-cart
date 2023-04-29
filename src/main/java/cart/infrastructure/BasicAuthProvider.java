package cart.infrastructure;

import cart.exception.AuthenticationException;
import cart.repository.MemberDao;
import java.util.Optional;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;
import org.springframework.util.StringUtils;

@Component
public class BasicAuthProvider {
    private static final String BASIC_HEADER = "Basic ";
    private static final String DELIMITER = ":";
    private static final int EMAIL_INDEX = 0;
    private static final int PASSWORD_INDEX = 1;
    private static final int CREDENTIALS_LENGTH = 2;

    private final MemberDao memberDao;

    public BasicAuthProvider(MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    public User resolveUser(String token) {
        String decodeToken = decodeToken(token);
        String[] credentials = decodeToken.split(DELIMITER);
        validateCredentials(credentials);
        String email = credentials[EMAIL_INDEX];
        String password = credentials[PASSWORD_INDEX];
        Long memberId = getMemberId(email, password);
        return new User(memberId, email, password);
    }

    private String decodeToken(String token) {
        if (!StringUtils.hasText(token)) {
            throw new AuthenticationException("인증 토큰이 비어있습니다.");
        }
        if (!token.startsWith(BASIC_HEADER)) {
            throw new AuthenticationException("베이직 형식의 토큰이 아닙니다.");
        }
        return getDecodeToken(trimToken(token));
    }

    private static String trimToken(String token) {
        return token.substring(BASIC_HEADER.length()).trim();
    }

    private String getDecodeToken(String token) {
        return new String(Base64Utils.decodeFromString(token));
    }

    private void validateCredentials(String[] credentials) {
        if (credentials.length != CREDENTIALS_LENGTH) {
            throw new AuthenticationException("올바른 형식의 토큰이 아닙니다.");
        }
    }

    private Long getMemberId(String email, String password) {
        Optional<Long> memberId = memberDao.findByEmailAndPassword(email, password);
        return memberId.orElseThrow(() -> new AuthenticationException("해당 이메일이 존재하지 않거나 비밀번호가 틀렸습니다."));
    }
}
