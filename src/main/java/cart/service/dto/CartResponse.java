package cart.service.dto;

public class CartResponse {

    private final Long id;
    private final ProductResponse productResponse;

    public CartResponse(final Long id, final ProductResponse productResponse) {
        this.id = id;
        this.productResponse = productResponse;
    }

    public Long getId() {
        return id;
    }

    public ProductResponse getProductResponse() {
        return productResponse;
    }
}
