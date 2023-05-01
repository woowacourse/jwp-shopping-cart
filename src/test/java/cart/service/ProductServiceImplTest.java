package cart.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import cart.service.dto.ProductUpdateRequest;
import cart.domain.Product;
import cart.controller.response.ProductResponse;
import cart.repository.ProductRepository;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {
	@Mock
	ProductRepository productRepository;

	@InjectMocks
	ProductServiceImpl productServiceImpl;

	@DisplayName("전체 상품 조회 테스트")
	@Test
	void findAll() {
		// given
		final List<Product> products = List.of(new Product(1L, "KIARA", 1000, "이미지"));

		given(productRepository.findAll()).willReturn(products);

		// when
		final List<ProductResponse> findAll = productServiceImpl.findAll();

		// then
		assertThat(findAll)
			.usingRecursiveComparison()
			.isEqualTo(List.of(new ProductResponse(1L, "KIARA", 1000, "이미지")));
	}

	@DisplayName("상품 저장 테스트")
	@Test
	void save() {
		// given
		given(productRepository.save(any())).willReturn(1L);

		// when
		final ProductUpdateRequest request = new ProductUpdateRequest("KIARA", 1000, "이미지");
		final long saveId = productServiceImpl.save(request);

		// then
		assertThat(saveId).isEqualTo(1L);
	}

	@DisplayName("상품 삭제 테스트")
	@Test
	void deleteByProductId() {
		// given
		given(productRepository.deleteByProductId(anyLong())).willReturn(true);

		// when
		final long deleteProductId = productServiceImpl.deleteByProductId(1L);

		// then
		assertThat(deleteProductId).isEqualTo(1L);
	}

	@DisplayName("상품 갱신 후 조회 테스트")
	@Test
	void updateProduct() {
		// given
		given(productRepository.updateByProductId(anyLong(), any())).willReturn(1L);
		given(productRepository.findByProductId(anyLong()))
			.willReturn(Optional.ofNullable(new Product(1L, "hyena", 400, "이미지")));

		// when
		final ProductUpdateRequest request = new ProductUpdateRequest("hyena", 400, "이미지");
		final ProductResponse updateProduct = productServiceImpl.update(1L, request);

		// then
		assertThat(updateProduct)
			.hasFieldOrPropertyWithValue("id", 1L)
			.hasFieldOrPropertyWithValue("name", "hyena")
			.hasFieldOrPropertyWithValue("price", 400.0)
			.hasFieldOrPropertyWithValue("image", "이미지");
	}
}
