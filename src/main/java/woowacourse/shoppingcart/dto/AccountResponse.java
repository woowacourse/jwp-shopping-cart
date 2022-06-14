package woowacourse.shoppingcart.dto;

public class AccountResponse {
    private String email;
    private String nickname;

    public AccountResponse() {
    }

    public AccountResponse(String email, String nickname) {
        this.email = email;
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public String getNickname() {
        return nickname;
    }
}
