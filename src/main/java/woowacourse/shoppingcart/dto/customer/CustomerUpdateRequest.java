package woowacourse.shoppingcart.dto.customer;

public class CustomerUpdateRequest {

    private String nickname;
    private String password;
    private String newPassword;

    public CustomerUpdateRequest() {
    }

    public CustomerUpdateRequest(final String nickname, final String password, final String newPassword) {
        this.nickname = nickname;
        this.password = password;
        this.newPassword = newPassword;
    }

    public String getNickname() {
        return nickname;
    }

    public String getPassword() {
        return password;
    }

    public String getNewPassword() {
        return newPassword;
    }
}
