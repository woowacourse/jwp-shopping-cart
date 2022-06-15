package woowacourse.shoppingcart.dao.dto;

public class CartItemDto {

    private Long productId;
    private int quantity;

    public CartItemDto() {
    }

    public CartItemDto(Long productId, int quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
