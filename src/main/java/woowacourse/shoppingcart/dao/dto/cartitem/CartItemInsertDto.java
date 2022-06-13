package woowacourse.shoppingcart.dao.dto.cartitem;

public class CartItemInsertDto {

    private final Long customerId;
    private final Long productId;
    private final int quantity;

    public CartItemInsertDto(Long customerId, Long productId, int quantity) {
        this.customerId = customerId;
        this.productId = productId;
        this.quantity = quantity;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public Long getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }
}
