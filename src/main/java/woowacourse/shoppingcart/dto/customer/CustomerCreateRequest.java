package woowacourse.shoppingcart.dto.customer;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import woowacourse.shoppingcart.domain.Customer;

public class CustomerCreateRequest {

    @Email
    @Size(min = 4, max = 50)
    @Pattern(regexp = "\\S*", message = "공백을 포함하면 안됩니다")
    private String email;

    @Size(min = 1, max = 10)
    @Pattern(regexp = "\\S*", message = "공백을 포함하면 안됩니다")
    private String username;

    @Size(min = 8, max = 20)
    @Pattern(regexp = "\\S*", message = "공백을 포함하면 안됩니다")
    private String password;

    private CustomerCreateRequest() {
    }

    public CustomerCreateRequest(String email, String username, String password) {
        this.email = email;
        this.username = username;
        this.password = password;
    }

    public Customer toEntity() {
        return new Customer(email, username, password);
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
