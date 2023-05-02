package cart.domain.product;

public class ProductPrice {

    private static final long MINIMUM_AMOUNT = 0;
    private final long amount;

    public ProductPrice(final long amount) {
        validateMinimum(amount);
        this.amount = amount;
    }

    private void validateMinimum(final long amount) {
        if (amount < MINIMUM_AMOUNT) {
            throw new IllegalArgumentException(String.format("[ERROR] 상품 가격은 %d원 이상이어야 합니다.", MINIMUM_AMOUNT));
        }
    }

    public long getAmount() {
        return amount;
    }
}
