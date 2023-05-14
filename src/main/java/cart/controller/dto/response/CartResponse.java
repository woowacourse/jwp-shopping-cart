package cart.controller.dto.response;

public class CartResponse {

    private final int id;

    private final ProductResponse product;

    public CartResponse(final int id, final ProductResponse product) {
        this.id = id;
        this.product = product;
    }

    public int getId() {
        return id;
    }

    public ProductResponse getProduct() {
        return product;
    }

}
