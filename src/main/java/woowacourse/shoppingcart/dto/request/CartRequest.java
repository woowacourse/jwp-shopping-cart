package woowacourse.shoppingcart.dto.request;

public class CartRequest {

    private Long productId;
    private int quantity;
    private boolean checked;

    public CartRequest() {
    }

    public CartRequest(Long productId, int quantity, boolean checked) {
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
