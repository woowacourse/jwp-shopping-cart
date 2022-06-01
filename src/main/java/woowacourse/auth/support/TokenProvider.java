package woowacourse.auth.support;

import java.util.Map;

public interface TokenProvider {
    String createToken(Map<String, Object> claims);

    long getPayload(String token);
}
