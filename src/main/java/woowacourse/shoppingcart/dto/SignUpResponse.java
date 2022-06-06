package woowacourse.shoppingcart.dto;

import woowacourse.shoppingcart.domain.Customer;

public class SignUpResponse {

    private String username;
    private String email;

    private SignUpResponse() {
    }

    public SignUpResponse(String username, String email) {
        this.username = username;
        this.email = email;
    }

    public static SignUpResponse fromCustomer(Customer customer) {
        return new SignUpResponse(
                customer.getUsername(),
                customer.getEmail()
        );
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }
}
