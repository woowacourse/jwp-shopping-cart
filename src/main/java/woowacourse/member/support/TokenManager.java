package woowacourse.member.support;

public interface TokenManager {

    String createToken(Long payload);

    String getPayload(String token);

    boolean validateToken(String token);
}
