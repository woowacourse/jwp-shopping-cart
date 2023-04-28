package cart.web.controller.dto.response;

public class ProductDeleteResponse {
    private Long deletedId;

    public ProductDeleteResponse() {
    }

    public ProductDeleteResponse(Long deletedId) {
        this.deletedId = deletedId;
    }

    public Long getDeletedId() {
        return deletedId;
    }
}
