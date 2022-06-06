package woowacourse.shoppingcart.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import woowacourse.shoppingcart.domain.CartItem;

@AllArgsConstructor
@NoArgsConstructor
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
}
