package cart.repository;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import cart.domain.Product;
import cart.service.dto.ProductUpdateRequest;

@SpringBootTest
class ProductRepositoryTest {
	ProductUpdateRequest request;
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@BeforeEach
	void setUp() {
		request = new ProductUpdateRequest("사과", 10000, "사과.png");
		jdbcTemplate.execute("TRUNCATE TABLE products RESTART IDENTITY");
	}

	@DisplayName("전체 상품 조회 테스트")
	@Test
	void findAll() {
		// given
		final List<Product> products = List.of(new Product(1L, "사과", 10000, "사과.png"));

		// when
		productRepository.save(request);
		final List<Product> findProducts = productRepository.findAll();

		// then
		assertThat(findProducts.get(0)).isEqualTo(products.get(0));

	}

	@DisplayName("상품 저장 테스트")
	@Test
	void save() {
		// given

		// when
		final long saveId = productRepository.save(request);
		final int count = productRepository.findAll().size();

		// then
		assertThat(count).isEqualTo(1);
	}

	@DisplayName("상품 삭제 테스트")
	@Test
	void deleteByProductId() {
		// given
		productRepository.save(request);

		// when
		final boolean isDelete = productRepository.deleteByProductId(1L);

		// then
		assertThat(isDelete).isTrue();
	}

	@DisplayName("상품 갱신 테스트")
	@Test
	void updateProduct() {
		// given
		productRepository.save(request);
		final ProductUpdateRequest newRequest = new ProductUpdateRequest("kiara", 300.0, "이미지2");

		// when
		final long updateProductId = productRepository.updateByProductId(1L, newRequest);

		// then
		assertThat(updateProductId).isEqualTo(1L);
	}
}
