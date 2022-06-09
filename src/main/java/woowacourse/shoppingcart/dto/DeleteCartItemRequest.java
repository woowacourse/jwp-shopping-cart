package woowacourse.shoppingcart.dto;

import javax.validation.constraints.NotNull;

public class DeleteCartItemRequest {

    @NotNull
    private Long id;

    private DeleteCartItemRequest() {
    }

    public DeleteCartItemRequest(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
