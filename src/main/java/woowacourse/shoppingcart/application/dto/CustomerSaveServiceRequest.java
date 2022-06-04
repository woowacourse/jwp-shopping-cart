package woowacourse.shoppingcart.application.dto;

import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.domain.Email;
import woowacourse.shoppingcart.domain.Password;

public class CustomerSaveServiceRequest {

    private final String name;
    private final String email;
    private final String password;

    public CustomerSaveServiceRequest(final String name, final String email, final String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public Customer toEntity() {
        return new Customer(name, new Email(email), Password.fromRawValue(password));
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
    public String getPassword() {
        return password;
    }
}
