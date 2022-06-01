package woowacourse.shoppingcart.domain.customer.password;

public interface PasswordEncoder {

    Password encode(Password rawPassword);

    boolean matches(Password rawPassword, Password encodedPassword);
}
