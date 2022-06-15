package woowacourse.shoppingcart.dto.customer;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import woowacourse.shoppingcart.domain.customer.Customer;

public class CustomerRequest {

    @Email
    private String email;

    @NotBlank
    private String nickname;

    @NotBlank
    private String password;

    private CustomerRequest() {
    }

    public CustomerRequest(String email, String nickname, String password) {
        this.email = email;
        this.nickname = nickname;
        this.password = password;
    }

    public Customer toCustomer() {
        return new Customer(email, nickname, password);
    }

    public String getEmail() {
        return email;
    }

    public String getNickname() {
        return nickname;
    }

    public String getPassword() {
        return password;
    }
}
