package woowacourse.shoppingcart.domain.customer;

public class PasswordTestEncryptor implements PasswordEncryptor {

    @Override
    public String encode(final String value) {
        return value;
    }

    @Override
    public boolean matches(final String plainPassword, final String encryptPassword) {
        return plainPassword.equals(encryptPassword);
    }
}
