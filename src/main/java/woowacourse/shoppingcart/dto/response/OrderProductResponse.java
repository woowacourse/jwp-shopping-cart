package woowacourse.shoppingcart.dto.response;

public class OrderProductResponse {
    private ProductResponse product;
    private Integer quantity;

    public OrderProductResponse() {
    }

    public OrderProductResponse(ProductResponse productResponse, Integer quantity) {
        this.product = productResponse;
        this.quantity = quantity;
    }

    public ProductResponse getProduct() {
        return product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    @Override
    public String toString() {
        return "OrderProductResponse{" +
                "product=" + product +
                ", quantity=" + quantity +
                '}';
    }
}
