package woowacourse.shoppingcart.dto;

public class ProductIdRequest {
    private Long id;

    public ProductIdRequest() {
    }

    public ProductIdRequest(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
