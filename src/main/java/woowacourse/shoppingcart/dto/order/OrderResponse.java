package woowacourse.shoppingcart.dto.order;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import com.fasterxml.jackson.annotation.JsonTypeName;
import java.util.List;
import java.util.stream.Collectors;
import woowacourse.shoppingcart.domain.OrderDetail;

@JsonTypeName("order")
@JsonTypeInfo(include = As.WRAPPER_OBJECT, use = Id.NAME)
public class OrderResponse {

    private long id;
    private List<OrderDetailResponse> orderDetails;

    private OrderResponse() {
    }

    public OrderResponse(long id, List<OrderDetail> orderDetails) {
        this.id = id;
        this.orderDetails = orderDetails.stream()
                .map(OrderDetailResponse::from)
                .collect(Collectors.toList());
    }

    public long getId() {
        return id;
    }

    public List<OrderDetailResponse> getOrderDetails() {
        return orderDetails;
    }
}
