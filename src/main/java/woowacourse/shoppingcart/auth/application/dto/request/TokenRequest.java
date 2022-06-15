package woowacourse.shoppingcart.auth.application.dto.request;

public class TokenRequest {

    private String email;
    private String password;

    public TokenRequest() {
    }

    public TokenRequest(final String email, final String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
