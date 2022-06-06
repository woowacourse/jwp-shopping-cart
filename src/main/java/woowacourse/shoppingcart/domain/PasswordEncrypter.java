package woowacourse.shoppingcart.domain;

public interface PasswordEncrypter {

    Password encode(CharSequence rawPassword);

    boolean matches(CharSequence rawPassword, Password encodedPassword);
}
