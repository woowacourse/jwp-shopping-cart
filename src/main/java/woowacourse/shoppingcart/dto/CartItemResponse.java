package woowacourse.shoppingcart.dto;

public class CartItemResponse {

    private final ProductResponse product;

    public CartItemResponse(ProductResponse product, Integer quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    private final Integer quantity;

    public ProductResponse getProduct() {
        return product;
    }

    public Integer getQuantity() {
        return quantity;
    }
}
