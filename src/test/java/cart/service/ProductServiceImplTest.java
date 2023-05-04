package cart.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import cart.domain.product.Product;
import cart.domain.product.ProductId;
import cart.repository.ProductRepository;
import cart.service.request.ProductUpdateRequest;
import cart.service.response.ProductResponse;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {
	@Mock
	ProductRepository productRepository;

	@InjectMocks
	ProductServiceImpl productService;

	@DisplayName("상품 저장 테스트")
	@Test
	void save() {
		// given
		given(productRepository.save(any())).willReturn(ProductId.from(1L));

		// when
		final ProductUpdateRequest request = new ProductUpdateRequest("KIARA", 1000, "이미지");
		final ProductId productId = productService.save(request);

		// then
		assertThat(ProductId.from(1L)).isEqualTo(productId);
	}

	@DisplayName("전체 상품 조회 테스트")
	@Test
	void findAll() {
		// given
		final Product product = new Product(ProductId.from(1L), "사과", 1000, "사과이미지");

		given(productRepository.save(product)).willReturn(ProductId.from(1L));
		given(productRepository.findAll()).willReturn(List.of(product));

		final ProductUpdateRequest request = new ProductUpdateRequest("사과", 1000, "사과이미지");
		productService.save(request);

		// when
		final List<ProductResponse> findAll = productService.findAll();

		// then
		assertThat(findAll)
			.usingRecursiveComparison()
			.isEqualTo(List.of(new ProductResponse(1L, "사과", 1000, "사과이미지")));
	}

	@DisplayName("상품 삭제 테스트")
	@Test
	void deleteByProductId() {
		// given
		given(productRepository.deleteByProductId(any())).willReturn(true);

		// when
		final ProductId deleteProductId = productService.deleteByProductId(ProductId.from(1L));

		// then
		assertThat(deleteProductId).isEqualTo(ProductId.from(1L));
	}

	@DisplayName("상품 갱신 후 조회 테스트")
	@Test
	void update() {
		// given
		given(productRepository.updateByProductId(any(), any())).willReturn(ProductId.from(1L));
		given(productRepository.findByProductId(any()))
			.willReturn(new Product(ProductId.from(1L), "hyena", 400, "이미지"));

		// when
		final ProductUpdateRequest request = new ProductUpdateRequest("hyena", 400, "이미지");
		final ProductResponse response = productService.update(ProductId.from(1L), request);

		// then
		assertThat(response)
			.hasFieldOrPropertyWithValue("id", 1L)
			.hasFieldOrPropertyWithValue("name", "hyena")
			.hasFieldOrPropertyWithValue("price", 400.0)
			.hasFieldOrPropertyWithValue("image", "이미지");
	}
}
