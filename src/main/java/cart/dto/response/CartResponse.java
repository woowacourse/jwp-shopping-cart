package cart.dto.response;

public class CartResponse {
    private final Long id;
    private final ProductResponse product;
    private final int count;

    public CartResponse(Long id, ProductResponse product, int count) {
        this.id = id;
        this.product = product;
        this.count = count;
    }

    public Long getId() {
        return id;
    }

    public ProductResponse getProduct() {
        return product;
    }

    public int getCount() {
        return count;
    }
}
