package cart.web.controller.dto.response;

public class ProductDeleteResponse {
    private final Long deletedId;

    public ProductDeleteResponse(Long deletedId) {
        this.deletedId = deletedId;
    }

    public Long getDeletedId() {
        return deletedId;
    }
}
