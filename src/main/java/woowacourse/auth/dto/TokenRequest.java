package woowacourse.auth.dto;

public class TokenRequest {

    private Long CustomerId;

    public TokenRequest() {
    }

    public TokenRequest(final Long CustomerId) {
        this.CustomerId = CustomerId;
    }

    public Long getCustomerId() {
        return CustomerId;
    }
}
