package woowacourse.shoppingcart.domain.customer.password;

public interface Password {

    void validateMatchingOriginalPassword(final String other);

    void validateMatchingLoginPassword(final String other);

    String getValue();
}
