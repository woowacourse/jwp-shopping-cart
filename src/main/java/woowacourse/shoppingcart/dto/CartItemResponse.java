package woowacourse.shoppingcart.dto;

public class CartItemResponse {

    private Long id;
    private int quantity;
    private ProductResponse productResponse;

    private CartItemResponse() {
    }

    public CartItemResponse(Long id, int quantity, ProductResponse productResponse) {
        this.id = id;
        this.quantity = quantity;
        this.productResponse = productResponse;
    }

    public Long getId() {
        return id;
    }

    public int getQuantity() {
        return quantity;
    }

    public ProductResponse getProductResponse() {
        return productResponse;
    }
}
