package woowacourse.auth.support;

public interface TokenManager {
    String createToken(String payload);

    String getPayload(String token);

    boolean validateToken(String token);
}
