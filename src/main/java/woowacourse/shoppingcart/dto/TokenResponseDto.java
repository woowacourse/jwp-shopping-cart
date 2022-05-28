package woowacourse.shoppingcart.dto;

public class TokenResponseDto {

    private final String accessToken;
    private final Long expirationTime;

    public TokenResponseDto(final String accessToken, final Long expirationTime) {
        this.accessToken = accessToken;
        this.expirationTime = expirationTime;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public Long getExpirationTime() {
        return expirationTime;
    }
}
