package woowacourse.shoppingcart.domain.customer.password;

public interface PasswordCreationStrategy {

    Password createPassword(final String value);
}
