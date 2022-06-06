package woowacourse.shoppingcart.dto;

public class CustomerResponse {

    private String email;
    private String username;

    public CustomerResponse() {
    }

    public CustomerResponse(String email, String username) {
        this.email = email;
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }
}
