package woowacourse.shoppingcart.application.dto.request;

public class ProductIdRequest {

    private Long id;

    private ProductIdRequest() {
    }

    public ProductIdRequest(final Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
