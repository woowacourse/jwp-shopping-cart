package cart.controller.dto.request;

import javax.validation.constraints.NotNull;

public class AddCartRequest {

    @NotNull(message = "상품 ID를 필수적으로 입력해주세요.")
    private Long id;

    private AddCartRequest() {
    }

    public AddCartRequest(final Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
