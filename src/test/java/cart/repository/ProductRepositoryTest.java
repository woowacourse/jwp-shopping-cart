package cart.repository;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import cart.domain.product.Product;
import cart.domain.product.ProductId;
import cart.service.request.ProductUpdateRequest;

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
		final Product product = new Product("사과", 10000, "사과.png");

		// when
		productRepository.save(product);
		final List<Product> allProducts = productRepository.findAll();

		// then
		assertThat(1).isEqualTo(allProducts.size());

	}

	@DisplayName("상품 저장 테스트")
	@Test
	void save() {
		// given
		final Product product = new Product("사과", 10000, "사과.png");

		// when
		final ProductId productId = productRepository.save(product);
		final int count = productRepository.findAll().size();

		// then
		assertThat(count).isEqualTo(1);
	}

	@DisplayName("상품 삭제 테스트")
	@Test
	void deleteByProductId() {
		// given
		final Product product = new Product("사과", 10000, "사과.png");

		productRepository.save(product);

		// when
		final boolean isDelete = productRepository.deleteByProductId(ProductId.from(1L));

		// then
		assertThat(isDelete).isTrue();
	}

	@DisplayName("상품 갱신 테스트")
	@Test
	void update() {
		// given
		final Product product = new Product("사과", 10000, "사과.png");

		productRepository.save(product);

		// when
		final ProductId productId = productRepository.updateByProductId(ProductId.from(1L), product);

		// then
		assertThat(productId).isEqualTo(ProductId.from(1L));
	}
}
