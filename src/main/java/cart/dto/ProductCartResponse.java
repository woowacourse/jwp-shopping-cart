package cart.dto;

import cart.entity.ProductCart;

public class ProductCartResponse {
    private Long id;

    public ProductCartResponse(Long id) {
        this.id = id;
    }

    public ProductCartResponse() {
    }

    public static ProductCartResponse from(ProductCart productCart) {
        return new ProductCartResponse(productCart.getId());
    }

    public Long getId() {
        return id;
    }
}
