package woowacourse.shoppingcart.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import woowacourse.exception.ErrorCode;
import woowacourse.exception.InvalidCartItemException;

@Getter
@EqualsAndHashCode
public class Quantity {

	private final int value;

	public Quantity(int value) {
		validatePrice(value);
		this.value = value;
	}

	private void validatePrice(int value) {
		if (value <= 0) {
			throw new InvalidCartItemException(ErrorCode.QUANTITY_FORMAT, "수량은 양수여야 합니다.");
		}
	}
}
