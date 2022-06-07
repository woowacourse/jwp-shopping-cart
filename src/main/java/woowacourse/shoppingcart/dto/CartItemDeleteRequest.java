package woowacourse.shoppingcart.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class CartItemDeleteRequest {

	private List<Long> productIds;
}
