package woowacourse.shoppingcart.dto;

import java.util.LinkedList;
import java.util.List;
import woowacourse.shoppingcart.domain.OrderDetail;

public class OrdersDto {

    private Long id;
    private List<OrderedProduct> orderedProducts;

    public OrdersDto() {
    }

    public OrdersDto(Long id, List<OrderedProduct> orderedProducts) {
        this.id = id;
        this.orderedProducts = orderedProducts;
    }

    public static OrdersDto of(Long id, List<OrderDetail> orderDetails) {
        List<OrderedProduct> orderedProducts = new LinkedList<>();
        for (OrderDetail orderDetail : orderDetails) {
            orderedProducts.add(OrderedProduct.of(orderDetail.getProduct(), orderDetail.getQuantity()));
        }
        return new OrdersDto(id, orderedProducts);
    }

    public Long getId() {
        return id;
    }

    public List<OrderedProduct> getOrderedProducts() {
        return orderedProducts;
    }
}
