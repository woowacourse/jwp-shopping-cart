package woowacourse.shoppingcart.domain.customer;

public interface Password {

    boolean isSamePassword(final String hashedPassword);
    String getPassword();
}
