package woowacourse.shoppingcart.dto;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import woowacourse.shoppingcart.domain.Orders;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Getter
public class OrderResponse {

	private Long id;
	private List<CartItemResponse> orderDetails;
	private Integer totalPrice;
	private String orderDate;

	public static OrderResponse from(Orders order) {
		return new OrderResponse(
			order.getId(),
			order.getOrderDetails().stream()
				.map(CartItemResponse::from)
				.collect(Collectors.toList()),
			order.calculateTotalPrice(),
			order.getOrderDate()
		);
	}
}
