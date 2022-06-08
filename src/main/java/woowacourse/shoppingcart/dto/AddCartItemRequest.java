package woowacourse.shoppingcart.dto;

public class AddCartItemRequest {

    private final Long productId;
    private final int quantity;
    private final boolean checked;

    public AddCartItemRequest(Long productId, int quantity, boolean checked) {
        this.productId = productId;
        this.quantity = quantity;
        this.checked = checked;
    }

    public Long getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public boolean getChecked() {
        return checked;
    }
}
