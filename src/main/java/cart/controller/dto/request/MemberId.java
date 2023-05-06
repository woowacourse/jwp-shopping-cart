package cart.controller.dto.request;

import javax.validation.constraints.NotNull;

public class MemberId {
    @NotNull(message = "아이디가 비어있습니다.")
    private Long id;

    public MemberId() {
    }

    public MemberId(final Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
