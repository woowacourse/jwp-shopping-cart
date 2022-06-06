package woowacourse.auth.domain.token;

import javax.crypto.SecretKey;

public interface Token {

    String getPayload(SecretKey tokenKey);

    String getValue();
}
