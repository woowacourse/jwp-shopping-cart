package woowacourse.shoppingcart.dto;

import java.util.Objects;

public class OrderDetailResponse {
    private ProductResponse product;
    private int quantity;

    public OrderDetailResponse() {
    }

    public OrderDetailResponse(ProductResponse product, int quantity) {
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
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        OrderDetailResponse that = (OrderDetailResponse) o;
        return quantity == that.quantity && Objects.equals(product, that.product);
    }

    @Override
    public int hashCode() {
        return Objects.hash(product, quantity);
    }

    @Override
    public String toString() {
        return "OrderDetailResponse{" +
                "product=" + product +
                ", quantity=" + quantity +
                '}';
    }
}
