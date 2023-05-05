package cart.dto.response;

public class CartResponse {
    private final long id;
    private final ProductResponse product;
    private final int count;

    public CartResponse(long id, ProductResponse product, int count) {
        this.id = id;
        this.product = product;
        this.count = count;
    }

    public long getId() {
        return id;
    }
    
    public ProductResponse getProduct() {
        return product;
    }

    public int getCount() {
        return count;
    }
}
