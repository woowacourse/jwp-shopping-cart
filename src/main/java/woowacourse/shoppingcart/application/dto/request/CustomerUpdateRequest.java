package woowacourse.shoppingcart.application.dto.request;

public class CustomerUpdateRequest {

    private String nickname;
    private String password;

    private CustomerUpdateRequest() {
    }

    public CustomerUpdateRequest(final String nickname, final String password) {
        this.nickname = nickname;
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public String getPassword() {
        return password;
    }
}
