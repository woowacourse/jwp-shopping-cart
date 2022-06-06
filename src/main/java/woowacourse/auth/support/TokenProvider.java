package woowacourse.auth.support;

import woowacourse.auth.dto.TokenRequest;

public interface TokenProvider {

    String createToken(TokenRequest tokenRequest);

    String getPayload(String token);

    void validateToken(String token);
}
