package woowacourse.shoppingcart.domain.customer;

public interface PasswordEncoder {

    String encode(final String plainPassword);

    boolean isMatchPassword(final String encodedPassword, final String plainPassword);
}
