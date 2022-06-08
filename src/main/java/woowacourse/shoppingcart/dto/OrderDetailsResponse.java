package woowacourse.shoppingcart.dto;

public class OrderDetailsResponse {

    private final Long id;
    private final ProductResponse product;
    private final Integer quantity;

    public OrderDetailsResponse(Long id, ProductResponse product, Integer quantity) {
        this.id = id;
        this.product = product;
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public ProductResponse getProduct() {
        return product;
    }

    public Integer getQuantity() {
        return quantity;
    }
}
