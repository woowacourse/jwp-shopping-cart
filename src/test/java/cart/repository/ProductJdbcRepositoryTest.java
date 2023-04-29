package cart.repository;

import cart.controller.request.ProductCreateRequest;
import cart.controller.request.ProductUpdateRequest;
import cart.domain.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class ProductJdbcRepositoryTest {
	@Mock
	ProductJdbcRepository productJdbcRepository;

	@DisplayName("전체 상품 조회 테스트")
	@Test
	void findAll() {
		// given
		final List<Product> products = List.of(new Product(1L, "사과", 10000, "사과.png"));

		given(productJdbcRepository.findAll()).willReturn(products);

		// when
		final List<Product> findProducts = productJdbcRepository.findAll();

		// then
		assertThat(findProducts).isEqualTo(products);
	}

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

	@DisplayName("상품 삭제 테스트")
	@Test
	void deleteByProductId() {
		// given
		given(productJdbcRepository.deleteByProductId(anyLong())).willReturn(1L);

		// when
		final long deleteProductId = productJdbcRepository.deleteByProductId(1L);

		// then
		assertThat(deleteProductId).isEqualTo(1L);
	}

	@DisplayName("상품 갱신 테스트")
	@Test
	void updateProduct(){
		// given
		given(productJdbcRepository.updateByProductId(anyLong(), any())).willReturn(1L);

		// when
		final ProductUpdateRequest request = new ProductUpdateRequest("kiara", 300.0, "이미지2");
		final long updateProductId = productJdbcRepository.updateByProductId(1L, request);

		// then
		assertThat(updateProductId).isEqualTo(1L);
	}
}
