package woowacourse.shoppingcart.domain.customer;

public class CustomerToken {

    private final String accessToken;
    private final String nickname;

    public CustomerToken(String accessToken, String nickname) {
        this.accessToken = accessToken;
        this.nickname = nickname;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getNickname() {
        return nickname;
    }
}
