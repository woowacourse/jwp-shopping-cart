package woowacourse.shoppingcart.domain.customer;

public interface PasswordEncoder {

    Password encode(final PlainPassword plainPassword);

    boolean isMatchPassword(final Password encodedPassword, final PlainPassword plainPassword);
}
