package woowacourse.shoppingcart.dao.dto.cartitem;

public class UpdateQuantityDto {

    private final Long id;
    private final int quantity;
    private final Long customerId;

    public UpdateQuantityDto(Long id, int quantity, Long customerId) {
        this.id = id;
        this.quantity = quantity;
        this.customerId = customerId;
    }

    public Long getId() {
        return id;
    }

    public int getQuantity() {
        return quantity;
    }

    public Long getCustomerId() {
        return customerId;
    }
}
