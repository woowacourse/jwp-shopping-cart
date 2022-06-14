package woowacourse.shoppingcart.dto.request;

import javax.validation.constraints.NotBlank;
import woowacourse.shoppingcart.domain.Customer;

public class SignUpRequest {

    @NotBlank
    private String username;

    @NotBlank
    private String email;

    @NotBlank
    private String password;

    private SignUpRequest() {
    }

    public SignUpRequest(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public Customer toCustomer() {
        return new Customer(username, email, password);
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
