package woowacourse.shoppingcart.dto;

public class DeleteCartItemRequest {

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
