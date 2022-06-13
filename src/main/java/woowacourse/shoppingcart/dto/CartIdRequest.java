package woowacourse.shoppingcart.dto;

public class CartIdRequest {
    private Long id;

    public CartIdRequest() {
    }

    public CartIdRequest(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
