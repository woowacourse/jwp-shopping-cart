package woowacourse.shoppingcart.dto;

public class CustomerRequest {

    private String username;
    private String password;

    public CustomerRequest() {
    }

    public CustomerRequest(final String username, final String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
