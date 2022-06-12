package woowacourse.auth.domain.token;

import javax.crypto.SecretKey;
import woowacourse.common.exception.AuthenticationException;

public class NullToken implements Token {

    public String getPayload(SecretKey tokenKey) {
        throw AuthenticationException.ofUnauthenticated();
    }

    public String getValue() {
        throw AuthenticationException.ofUnauthenticated();
    }
}
