package woowacourse.auth.dto;

import woowacourse.shoppingcart.domain.Customer;

public class LogInResponse {

    private String username;
    private String email;
    private String token;

    private LogInResponse() {
    }

    private LogInResponse(String username, String email, String token) {
        this.username = username;
        this.email = email;
        this.token = token;
    }

    public static LogInResponse from(Customer customer, String token) {
        return new LogInResponse(
                customer.getUsername(),
                customer.getEmail(),
                token
        );
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getToken() {
        return token;
    }
}
