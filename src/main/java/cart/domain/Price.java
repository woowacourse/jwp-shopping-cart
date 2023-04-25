package cart.domain;

public class Price {

    private final Integer amount;

    public Price(Integer amount) {
        validate(amount);
        this.amount = amount;
    }

    private void validate(Integer amount) {
        if (1_000 > amount || amount > 1_000_000) {
            throw new IllegalArgumentException("가격은 1000원 이상, 100만원 이하만 가능합니다.");
        }
    }

    public Integer getAmount() {
        return amount;
    }
}
