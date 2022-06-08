package woowacourse.shoppingcart.dao.dto.cartitem;

public class AddQuantityDto {

    private final Long id;
    private final int quantity;

    public AddQuantityDto(Long id, int quantity) {
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
