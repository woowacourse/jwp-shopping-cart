package woowacourse.shoppingcart.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import woowacourse.exception.ErrorCode;
import woowacourse.exception.InvalidProductException;

@Getter
@EqualsAndHashCode
public class ProductName {

	private final String value;

	public ProductName(String value) {
		validateName(value);
		this.value = value;
	}

	private void validateName(String value) {
		if (value.isBlank()) {
			throw new InvalidProductException(ErrorCode.ARGUMENT, "상품 이름은 비어있을 수 없습니다.");
		}
	}
}
