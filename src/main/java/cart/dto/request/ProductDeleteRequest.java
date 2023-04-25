package cart.dto.request;

public class ProductDeleteRequest {

    private Long id;

    public ProductDeleteRequest(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
