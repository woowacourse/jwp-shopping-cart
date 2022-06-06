package woowacourse.shoppingcart.dto;

import woowacourse.shoppingcart.domain.Customer;

public class SignUpResponse {

    private final String username;
    private final String email;

    public SignUpResponse(String username, String email) {
        this.username = username;
        this.email = email;
    }

    public static SignUpResponse from(Customer customer) {
        return new SignUpResponse(
                customer.getUsername().getValue(),
                customer.getEmail().getValue()
        );
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }
}
