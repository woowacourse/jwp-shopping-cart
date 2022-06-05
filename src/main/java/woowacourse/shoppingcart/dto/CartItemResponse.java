package woowacourse.shoppingcart.dto;

public class CartItemResponse {
    private ProductResponse productResponse;
    private int quantity;

    public CartItemResponse() {
    }

    public CartItemResponse(ProductResponse productResponse, int quantity) {
        this.productResponse = productResponse;
        this.quantity = quantity;
    }

    public ProductResponse getProductResponse() {
        return productResponse;
    }

    public int getQuantity() {
        return quantity;
    }

    @Override
    public String toString() {
        return "CartResponse{" +
                "productResponse=" + productResponse +
                ", quantity=" + quantity +
                '}';
    }
}
