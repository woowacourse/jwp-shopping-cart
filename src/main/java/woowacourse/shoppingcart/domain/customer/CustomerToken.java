package woowacourse.shoppingcart.domain.customer;

public class CustomerToken {

    private final String nickname;
    private final String accessToken;

    public CustomerToken(String nickname, String accessToken) {
        this.nickname = nickname;
        this.accessToken = accessToken;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getNickname() {
        return nickname;
    }
}
