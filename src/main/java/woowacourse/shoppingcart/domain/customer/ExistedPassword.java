package woowacourse.shoppingcart.domain.customer;

public class ExistedPassword implements Password {

    private final String value;

    public ExistedPassword(final String value) {
        this.value = value;
    }

    @Override
    public boolean isSamePassword(String hashedPassword) {
        return this.value.equals(hashedPassword);
    }

    @Override
    public String getPassword() {
        return value;
    }
}
