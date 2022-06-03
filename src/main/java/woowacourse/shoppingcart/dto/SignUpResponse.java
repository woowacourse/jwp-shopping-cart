package woowacourse.shoppingcart.dto;

public class SignUpResponse {
    private final String username;
    private final String email;

    public SignUpResponse(String username, String email) {
        this.username = username;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }
}
