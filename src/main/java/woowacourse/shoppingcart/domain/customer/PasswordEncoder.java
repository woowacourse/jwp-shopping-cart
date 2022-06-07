package woowacourse.shoppingcart.domain.customer;

public interface PasswordEncoder {
    EncodePassword encode(RawPassword rawPassword);
}
