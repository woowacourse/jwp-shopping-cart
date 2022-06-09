package woowacourse.shoppingcart.domain.cart;

public class Quantity {

    public static final int GUEST_QUANTITY = 0;
    private static final int MINIMUM_QUANTITY = 0;
    private final int quantity;

    public Quantity(final int quantity) {
        validate(quantity);
        this.quantity = quantity;
    }

    private static void validate(final int quantity) {
        if (quantity < MINIMUM_QUANTITY) {
            throw new IllegalArgumentException("올바르지 않은 상품 수량 형식입니다.");
        }
    }

    public int getValue() {
        return quantity;
    }
}
