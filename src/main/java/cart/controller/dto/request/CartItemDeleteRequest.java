package cart.controller.dto.request;

public class CartItemDeleteRequest {

    private Long id;

    public CartItemDeleteRequest() {
    }

    public CartItemDeleteRequest(final Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
