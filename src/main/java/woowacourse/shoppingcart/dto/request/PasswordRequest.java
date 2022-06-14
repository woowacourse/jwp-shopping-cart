package woowacourse.shoppingcart.dto.request;

public class PasswordRequest {

    private String password;

    private PasswordRequest() {

    }

    public PasswordRequest(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
}
