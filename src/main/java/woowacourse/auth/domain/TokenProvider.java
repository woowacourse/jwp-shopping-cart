package woowacourse.auth.domain;

import java.util.Map;

public interface TokenProvider {
    String createToken(Map<String, Object> claims);

    Map<String, Object> getPayload(String token);
}
