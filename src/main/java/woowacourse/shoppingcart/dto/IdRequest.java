package woowacourse.shoppingcart.dto;

import javax.validation.constraints.NotNull;

public class IdRequest {

    @NotNull
    private Long id;

    private IdRequest() {
    }

    public IdRequest(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
