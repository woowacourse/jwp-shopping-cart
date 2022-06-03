package woowacourse.shoppingcart.application.dto.request;

public class TokenRequest {

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
