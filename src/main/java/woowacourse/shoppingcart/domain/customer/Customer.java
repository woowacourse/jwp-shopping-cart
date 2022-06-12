package woowacourse.shoppingcart.domain.customer;

import woowacourse.shoppingcart.exception.EmailValidationException;
import woowacourse.utils.StringValidator;

import static woowacourse.shoppingcart.domain.customer.Email.empty;

public class Customer {

    private final Long id;
    private final Email email;
    private final String password;
    private final Username username;

    public Customer(Long id, Email email, String password, Username username) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.username = username;
    }

    public static Customer createWithoutId(final String email, final String password, final String username) {
        StringValidator.validateNullOrBlank(email, new EmailValidationException("이메일에는 공백이 들어가면 안됩니다."));
        return new Customer(null, new Email(email), password, new Username(username));
    }

    public static Customer createWithoutPassword(final Long id, final String email, final String username) {
        return new Customer(id, new Email(email), null, new Username(username));
    }

    public static Customer createWithoutEmailAndPassword(final Long id, final String username) {
        return new Customer(id, empty(), null, new Username(username));
    }


    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email.getValue();
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username.getValue();
    }
}
