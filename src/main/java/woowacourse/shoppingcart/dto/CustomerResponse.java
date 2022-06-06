package woowacourse.shoppingcart.dto;

public class CustomerResponse {
    private final String username;
    private final String email;

    public CustomerResponse(String username, String email) {
        this.username = username;
        this.email = email;
    }

    public static CustomerResponse from(AuthorizedCustomer authorizedCustomer) {
        return new CustomerResponse(authorizedCustomer.getUsername(), authorizedCustomer.getEmail());
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }
}
