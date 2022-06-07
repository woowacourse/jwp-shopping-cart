package woowacourse.auth.dto;

import javax.validation.constraints.NotNull;

public class TokenRequest {

    @NotNull(message = "입력된 값이 없습니다.")
    private Long id;

    private TokenRequest() {
    }

    public TokenRequest(final String id) {
        this.id = Long.valueOf(id);
    }

    public Long getId() {
        return id;
    }
}
