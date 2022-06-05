package woowacourse.auth.support;

public interface TokenProvider {

    String createToken(String payload);

    String getPayload(String token);

    boolean validateToken(String token);
}
