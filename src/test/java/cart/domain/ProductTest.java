package cart.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class ProductTest {
	@DisplayName("상품 생성 성공 테스트")
	@Test
	void constructor() {
		assertDoesNotThrow(() ->
			new Product(3L, "name", 300, "img")
		);
	}

	@DisplayName("상품명 유효성 검증")
	@ParameterizedTest
	@ValueSource(strings = {"", " ", "qwertyuioplkjhgfdsazx"})
	void validateName(String name) {
		assertThatThrownBy(() -> new Product(3L, name, 300, "https://avatars.githubusercontent.com/u/101039161?v=4"))
			.isInstanceOf(IllegalArgumentException.class);
	}

	@DisplayName("상품 가격 유효성 검증")
	@ParameterizedTest
	@ValueSource(doubles = {-2000, -1, -2})
	void validatePrice(double price) {
		assertThatThrownBy(
			() -> new Product(3L, "hyena", price, "https://avatars.githubusercontent.com/u/101039161?v=4"))
			.isInstanceOf(IllegalArgumentException.class);
	}

	@DisplayName("상품 사진 유효성 검증")
	@Test
	void validateImage() {
		assertThatThrownBy(() -> new Product(3L, "hyena", 300, null))
			.isInstanceOf(IllegalArgumentException.class);
	}
}
