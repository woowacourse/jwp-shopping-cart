package cart.domain;

public class Price {

    private static final int MIN_AMOUNT = 1_000;
    private static final int MAX_AMOUNT = 1_000_000;

    private final Integer amount;

    public Price(Integer amount) {
        validate(amount);
        this.amount = amount;
    }

    private void validate(Integer amount) {
        if (MIN_AMOUNT > amount || amount > MAX_AMOUNT) {
            throw new IllegalArgumentException("가격은 1000원 이상, 100만원 이하만 가능합니다.");
        }
    }

    public Integer getAmount() {
        return amount;
    }
}
