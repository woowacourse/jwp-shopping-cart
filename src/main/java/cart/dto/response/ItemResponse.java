package cart.dto.response;

public class ItemResponse {

    private final Long id;
    private final Long memberId;
    private final ProductResponse productResponse;


    public ItemResponse(Long id, Long memberId, ProductResponse productResponse) {
        this.id = id;
        this.memberId = memberId;
        this.productResponse = productResponse;
    }

    public Long getId() {
        return id;
    }

    public Long getMemberId() {
        return memberId;
    }

    public ProductResponse getProductResponse() {
        return productResponse;
    }
}
