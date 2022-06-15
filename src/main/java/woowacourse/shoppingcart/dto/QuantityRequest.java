package woowacourse.shoppingcart.dto;

import javax.validation.constraints.Positive;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class QuantityRequest {

	@Positive
	private Integer quantity;
}
