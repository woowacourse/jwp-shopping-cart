package woowacourse.shoppingcart.dto;

public class CartProductRequest {

    private Long productId;
    private Long quantity;
    private boolean checked;

    private CartProductRequest() {
    }

    public CartProductRequest(Long productId, Long quantity, boolean checked) {
        this.productId = productId;
        this.quantity = quantity;
        this.checked = checked;
    }

    public Long getProductId() {
        return productId;
    }

    public Long getQuantity() {
        return quantity;
    }

    public boolean isChecked() {
        return checked;
    }
}
