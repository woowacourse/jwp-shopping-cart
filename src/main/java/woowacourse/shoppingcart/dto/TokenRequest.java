package woowacourse.shoppingcart.dto;

public class TokenRequest {

    private Long id;

    public TokenRequest() {
    }

    public TokenRequest(final Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
