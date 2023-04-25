package cart.dto.request;

import javax.validation.constraints.NotNull;

public class ProductDeleteRequest {

    @NotNull
    private Long id;

    public ProductDeleteRequest(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
