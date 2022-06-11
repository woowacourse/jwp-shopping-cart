package woowacourse.shoppingcart.dto;

public class OrdersDetailDto {

    private OrdersDetailProductResponseDto product;
    private int count;

    private OrdersDetailDto() {
    }

    public OrdersDetailDto(final OrdersDetailProductResponseDto product, final int count) {
        this.product = product;
        this.count = count;
    }

    public OrdersDetailProductResponseDto getProduct() {
        return product;
    }

    public int getCount() {
        return count;
    }
}
