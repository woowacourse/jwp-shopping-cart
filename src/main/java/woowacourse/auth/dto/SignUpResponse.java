package woowacourse.auth.dto;

import woowacourse.auth.domain.Customer;

public class SignUpResponse {

    private final String username;
    private final String email;

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
