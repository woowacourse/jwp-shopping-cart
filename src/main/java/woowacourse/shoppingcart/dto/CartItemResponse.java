package woowacourse.shoppingcart.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import woowacourse.shoppingcart.domain.CartItem;
import woowacourse.shoppingcart.domain.OrderDetail;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Getter
public class CartItemResponse {

	private Long productId;
	private String name;
	private String image;
	private Integer price;
	private Integer quantity;

	public static CartItemResponse from(CartItem cartItem) {
		return new CartItemResponse(
			cartItem.getProductId(),
			cartItem.getName(),
			cartItem.getImageUrl(),
			cartItem.getPrice(),
			cartItem.getQuantity()
		);
	}

	public static CartItemResponse from(OrderDetail orderDetail) {
		return new CartItemResponse(
			orderDetail.getProductId(),
			orderDetail.getName(),
			orderDetail.getImageUrl(),
			orderDetail.getPrice(),
			orderDetail.getQuantity()
		);
	}
}
