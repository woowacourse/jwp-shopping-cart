package woowacourse.shoppingcart.domain.customer;

public interface PasswordEncryptor {
    String encrypt(String password);
}
