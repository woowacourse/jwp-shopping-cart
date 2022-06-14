package woowacourse.shoppingcart.dto.request;

import javax.validation.constraints.Positive;

public class CartIdRequest {

    @Positive
    private Long id;

    private CartIdRequest() {
    }

    public CartIdRequest(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
