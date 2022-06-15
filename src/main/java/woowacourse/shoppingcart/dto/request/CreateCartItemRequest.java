package woowacourse.shoppingcart.dto.request;

import javax.validation.constraints.NotNull;

public class CreateCartItemRequest {

    @NotNull(message = "상품 ID를 입력해주세요😉")
    private Long id;

    private CreateCartItemRequest() {
    }

    public CreateCartItemRequest(final Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
