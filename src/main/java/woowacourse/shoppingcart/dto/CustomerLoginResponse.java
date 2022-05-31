package woowacourse.shoppingcart.dto;

public class CustomerLoginResponse {
    private String accessToken;
    private Long id;
    private String userId;
    private String nickname;

    public CustomerLoginResponse() {
    }

    public CustomerLoginResponse(final String accessToken, final Long id, final String userId, final String nickname) {
        this.accessToken = accessToken;
        this.id = id;
        this.userId = userId;
        this.nickname = nickname;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public Long getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public String getNickname() {
        return nickname;
    }
}
