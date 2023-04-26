package cart.domain;

public class Price {

    public static final int MAXIMUM_PRICE = 1_000_000;
    public static final int MINIMUM_PRICE = 1_000;
    public static final String PRICE_VALID_MESSAGE = "[ERROR] 가격은 1000원 이상, 100만원 이하만 가능합니다.";
    private final Integer amount;

    public Price(Integer amount) {
        validate(amount);
        this.amount = amount;
    }

    private void validate(Integer amount) {
        if (MINIMUM_PRICE > amount || amount > MAXIMUM_PRICE) {
            throw new IllegalArgumentException(PRICE_VALID_MESSAGE);
        }
    }

    public Integer getAmount() {
        return amount;
    }
}
