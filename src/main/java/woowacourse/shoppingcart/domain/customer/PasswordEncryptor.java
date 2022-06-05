package woowacourse.shoppingcart.domain.customer;

public interface PasswordEncryptor {

    String encode(final String value);

    boolean matches(final String plainPassword, final String encryptPassword);
}
