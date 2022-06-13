package woowacourse.shoppingcart.dto;

public class DeleteCartItemRequest {

    private Long id;

    public DeleteCartItemRequest() {
    }

    public DeleteCartItemRequest(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
