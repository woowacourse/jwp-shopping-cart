package woowacourse.shoppingcart.dto;

public class AddCartItemRequest {

    private Long productId;
    private Long quantity;
    private Boolean checked;

    private AddCartItemRequest() {
    }

    public AddCartItemRequest(Long productId, Long quantity, Boolean checked) {
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

    public Boolean getChecked() {
        return checked;
    }
}
