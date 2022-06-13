package woowacourse.shoppingcart.dao.dto;

public class OrdersDto {

    private final Long customerId;

    public OrdersDto(Long customerId) {
        this.customerId = customerId;
    }

    public Long getCustomerId() {
        return customerId;
    }
}
