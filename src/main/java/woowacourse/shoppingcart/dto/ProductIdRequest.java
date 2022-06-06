package woowacourse.shoppingcart.dto;

import java.util.List;

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
