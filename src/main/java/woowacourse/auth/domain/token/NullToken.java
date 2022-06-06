package woowacourse.auth.domain.token;

import javax.crypto.SecretKey;
import woowacourse.common.exception.ForbiddenException;

public class NullToken implements Token {

    public String getPayload(SecretKey tokenKey) {
        throw new ForbiddenException();
    }

    public String getValue() {
        throw new ForbiddenException();
    }
}
