package woowacourse.shoppingcart.dao;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@AllArgsConstructor
@Getter
public class CartItemDto {

	private Long id;
	private final Long customerId;
	private final Long productId;
	private final Integer quantity;
}
