package woowacourse.shoppingcart.dto;

public class CartItemResponse {
    private ProductResponse product;
    private int quantity;

    public CartItemResponse() {
    }

    public CartItemResponse(ProductResponse product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public ProductResponse getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    @Override
    public String toString() {
        return "CartResponse{" +
                "product=" + product +
                ", quantity=" + quantity +
                '}';
    }
}
