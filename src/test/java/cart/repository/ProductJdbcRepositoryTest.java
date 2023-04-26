package cart.repository;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import cart.controller.request.ProductCreateRequest;

@ExtendWith(MockitoExtension.class)
class ProductJdbcRepositoryTest {
	@Mock
	ProductJdbcRepository productJdbcRepository;

	@DisplayName("상품 저장 테스트")
	@Test
	void save() {
		// given
		final ProductCreateRequest request = new ProductCreateRequest("사과", 10000, "사과.png");

		given(productJdbcRepository.save(request)).willReturn(1L);

		// when
		final long saveId = productJdbcRepository.save(request);

		// then
		assertThat(saveId).isEqualTo(1L);
	}
}
