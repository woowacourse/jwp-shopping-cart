package woowacourse.shoppingcart.dto;

public class LoginCustomer {

    private final String username;

    public LoginCustomer(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
