package woowacourse.shoppingcart.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import woowacourse.exception.ErrorCode;
import woowacourse.exception.InvalidProductException;

@Getter
@EqualsAndHashCode
public class Price {

	private final int value;

	public Price(int value) {
		validatePrice(value);
		this.value = value;
	}

	private void validatePrice(int value) {
		if (value <= 0) {
			throw new InvalidProductException(ErrorCode.ARGUMENT, "가격은 양수여야 합니다.");
		}
	}

	public int multiply(int number) {
		return value * number;
	}
}
