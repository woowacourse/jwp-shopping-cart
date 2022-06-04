package woowacourse.shoppingcart.dto;

public class PasswordRequest {

    private String password;

    public PasswordRequest() {
    }

    public PasswordRequest(final String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
}
