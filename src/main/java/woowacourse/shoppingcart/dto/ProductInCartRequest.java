package woowacourse.shoppingcart.dto;

public class ProductInCartRequest {

    private Long productId;
    private Integer quantity;
    private Boolean checked;

    public ProductInCartRequest() {
    }

    public ProductInCartRequest(Long productId, Integer quantity, Boolean checked) {
        this.productId = productId;
        this.quantity = quantity;
        this.checked = checked;
    }

    public Long getProductId() {
        return productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Boolean getChecked() {
        return checked;
    }
}
