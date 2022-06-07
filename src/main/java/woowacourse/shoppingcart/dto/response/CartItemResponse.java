package woowacourse.shoppingcart.dto.response;

public class CartItemResponse {

    private ProductResponse product;
    private Integer quantity;

    public CartItemResponse() {
    }

    public CartItemResponse(ProductResponse product, Integer quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public ProductResponse getProduct() {
        return product;
    }

    public Integer getQuantity() {
        return quantity;
    }
}
