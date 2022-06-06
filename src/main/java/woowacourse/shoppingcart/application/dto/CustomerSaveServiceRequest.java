package woowacourse.shoppingcart.application.dto;

import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.domain.Email;
import woowacourse.shoppingcart.domain.EncodedPassword;
import woowacourse.shoppingcart.domain.PlainPassword;

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
        final PlainPassword plainPassword = new PlainPassword(password);
        return new Customer(name, new Email(email), new EncodedPassword(plainPassword.encode()));
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
