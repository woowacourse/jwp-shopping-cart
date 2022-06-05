package woowacourse.shoppingcart.domain.customer.password;

public interface PasswordEncoder {

    EncodedPassword encode(RawPassword rawPassword);

    boolean matches(RawPassword rawPassword, EncodedPassword encodedPassword);
}
