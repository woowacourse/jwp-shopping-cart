package cart.domain;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ProductTest {
	@DisplayName("상품 생성 성공 테스트")
	@Test
	void constructor() {
		assertDoesNotThrow(() ->
			new Product(3L, "name", 300, "img")
		);
	}
}
