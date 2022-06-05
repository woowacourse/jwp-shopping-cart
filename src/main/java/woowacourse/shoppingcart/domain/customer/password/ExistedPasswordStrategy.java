package woowacourse.shoppingcart.domain.customer.password;

public class ExistedPasswordStrategy implements PasswordCreationStrategy {

    ExistedPasswordStrategy() {
    }

    @Override
    public Password createPassword(final String value) {
        return new Password(value);
    }
}
