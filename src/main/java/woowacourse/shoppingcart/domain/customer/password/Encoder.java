package woowacourse.shoppingcart.domain.customer.password;

public interface Encoder {
    String encode(CharSequence rawPassword);

    boolean matches(CharSequence rawPassword, String encodedPassword);
}
