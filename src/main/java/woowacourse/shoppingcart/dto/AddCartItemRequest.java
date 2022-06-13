package woowacourse.shoppingcart.dto;

public class AddCartItemRequest {

    private Long productId;
    private int quantity;
    private boolean checked;

    public AddCartItemRequest() {
    }

    public AddCartItemRequest(final Long productId, final int quantity, final boolean checked) {
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
