package woowacourse.shoppingcart.domain;

public class Cart {

    private static final int POSITIVE_DIGIT_STANDARD = 0;

    private final Long id;
    private final Long customerId;
    private final Long productId;
    private final Integer quantity;

    public Cart(Long customerId, Long productId, Integer quantity) {
        this(null, customerId, productId, quantity);
    }

    public Cart(Long id, Long customerId, Long productId, Integer quantity) {
        validateQuantity(quantity);
        this.id = id;
        this.customerId = customerId;
        this.productId = productId;
        this.quantity = quantity;
    }

    private void validateQuantity(Integer quantity) {
        if (quantity <= POSITIVE_DIGIT_STANDARD) {
            throw new IllegalArgumentException("올바르지 않은 상품 수량 형식입니다.");
        }
    }

    public Long getId() {
        return id;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public Long getProductId() {
        return productId;
    }

    public Integer getQuantity() {
        return quantity;
    }
}
