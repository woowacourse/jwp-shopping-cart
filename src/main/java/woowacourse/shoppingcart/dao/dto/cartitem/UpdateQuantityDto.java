package woowacourse.shoppingcart.dao.dto.cartitem;

public class UpdateQuantityDto {

    private final Long id;
    private final int quantity;

    public UpdateQuantityDto(Long id, int quantity) {
        this.id = id;
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public int getQuantity() {
        return quantity;
    }
}
