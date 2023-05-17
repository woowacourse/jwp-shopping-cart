package cart.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.List;

import org.junit.jupiter.api.Assertions;
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
class ProductServiceTest {
	@Mock
	ProductRepository productRepository;

	@InjectMocks
	ProductService productService;

	@DisplayName("상품 저장 테스트")
	@Test
	void save() {
		// given
		given(productRepository.insert(any())).willReturn(ProductId.from(1L));

		// when
		final ProductUpdateRequest request = new ProductUpdateRequest("KIARA", 1000, "이미지");
		final ProductResponse response = productService.insert(request);

		// then
		assertThat(1L).isEqualTo(response.getId());
	}

	@DisplayName("전체 상품 조회 테스트")
	@Test
	void findAll() {
		// given
		final Product product = new Product(ProductId.from(1L), "사과", 1000, "사과이미지");

		given(productRepository.insert(product)).willReturn(ProductId.from(1L));
		given(productRepository.findAll()).willReturn(List.of(product));

		final ProductUpdateRequest request = new ProductUpdateRequest("사과", 1000, "사과이미지");
		productService.insert(request);

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
		final String name = "hyena";
		final double price = 400;
		final String image = "이미지";
		given(productRepository.updateByProductId(any())).willReturn(ProductId.from(1L));
		given(productRepository.findByProductId(any()))
			.willReturn(new Product(ProductId.from(1L), name, price, image));

		// when
		final ProductUpdateRequest request = new ProductUpdateRequest(name, price, image);
		final ProductResponse response = productService.update(ProductId.from(1L), request);

		// then
		Assertions.assertAll(
			() -> assertThat(response.getId()).isEqualTo(1L),
			() -> assertThat(response.getName()).isEqualTo(name),
			() -> assertThat(response.getPrice()).isEqualTo(price),
			() -> assertThat(response.getImage()).isEqualTo(image)
		);
	}
}
