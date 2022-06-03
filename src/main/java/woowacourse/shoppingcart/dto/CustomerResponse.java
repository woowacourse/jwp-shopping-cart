package woowacourse.shoppingcart.dto;

public class CustomerResponse {

    private final String username;
    private final String email;

    public CustomerResponse(String username, String email) {
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
