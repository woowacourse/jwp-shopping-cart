package cart.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import cart.controller.request.ProductCreateRequest;
import cart.domain.Product;
import cart.dto.ProductDto;
import cart.repository.ProductRepository;

@SpringBootTest
class GeneralProductServiceTest {
	@MockBean
	ProductRepository productRepository;

	@Autowired
	GeneralProductService generalProductService;

	@DisplayName("전체 상품 조회 테스트")
	@Test
	void findAll() {
		// given
		final List<Product> products = List.of(new Product(1L, "KIARA", 1000, "이미지"));

		given(productRepository.findAll()).willReturn(products);

		// when
		final List<ProductDto> findAll = generalProductService.findAll();

		// then
		assertThat(findAll)
			.usingRecursiveComparison()
			.isEqualTo(List.of(new ProductDto(1L, "KIARA", 1000, "이미지")));
	}

	@DisplayName("상품 저장 테스트")
	@Test
	void save(){
		// given
		given(productRepository.save(any())).willReturn(1L);

		// when
		final ProductCreateRequest request = new ProductCreateRequest("KIARA", 1000, "이미지");
		final long saveId = generalProductService.save(request);

		// then
		assertThat(saveId).isEqualTo(1L);
	}

	@DisplayName("상품 삭제 테스트")
	@Test
	void deleteByProductId() {
		// given
		given(productRepository.deleteByProductId(anyLong())).willReturn(1L);

		// when
		long deleteProductId = generalProductService.deleteByProductId(1L);

		// then
		assertThat(deleteProductId).isEqualTo(1L);
	}
}
