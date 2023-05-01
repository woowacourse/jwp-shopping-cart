package cart.repository.product;

import cart.domain.product.Product;
import cart.domain.product.ProductId;
import cart.service.request.ProductUpdateRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class ProductJdbcRepositoryTest {
	@Mock
	ProductJdbcRepository productJdbcRepository;

	@DisplayName("전체 상품 조회 테스트")
	@Test
	void findAll() {
		// given
		final List<Product> products = List.of(new Product(ProductId.from(1L), "사과", 10000, "사과.png"));

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
		final Product product = new Product("사과", 10000, "사과.png");

		given(productJdbcRepository.save(product)).willReturn(ProductId.from(1L));

		// when
		final ProductId saveId = productJdbcRepository.save(product);

		// then
		assertThat(saveId.getId()).isEqualTo(1L);
	}

	@DisplayName("상품 삭제 테스트")

	@Test
	void deleteByProductId() {
		// given
		given(productJdbcRepository.deleteByProductId(any())).willReturn(ProductId.from(1L));

		// when
		final ProductId deleteProductId = productJdbcRepository.deleteByProductId(ProductId.from(1L));

		// then
		assertThat(deleteProductId.getId()).isEqualTo(1L);
	}

	@DisplayName("상품 갱신 테스트")
	@Test
	void updateProduct(){
		// given
		given(productJdbcRepository.updateByProductId(any(), any())).willReturn(ProductId.from(1L));

		// when
		final ProductUpdateRequest request = new ProductUpdateRequest("kiara", 300.0, "이미지2");
		final ProductId updateProductId = productJdbcRepository.updateByProductId(ProductId.from(1L), request);

		// then
		assertThat(updateProductId.getId()).isEqualTo(1L);
	}
}
