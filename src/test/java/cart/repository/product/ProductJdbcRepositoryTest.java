package cart.repository.product;

import cart.config.RepositoryTestConfig;
import cart.domain.product.Product;
import cart.domain.product.ProductId;
import cart.service.request.ProductUpdateRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class ProductJdbcRepositoryTest extends RepositoryTestConfig {
    private static final Product CHICKEN = new Product("치킨", 10000, "image-url");

    ProductJdbcRepository productJdbcRepository;

    @BeforeEach
    void setUp() {
        productJdbcRepository = new ProductJdbcRepository(jdbcTemplate);
    }

    @DisplayName("전체 상품 조회 테스트")
    @Test
    void findAll() {
        // given
        productJdbcRepository.save(CHICKEN);

        // when
        final List<Product> findProducts = productJdbcRepository.findAll();


        // then
        assertThat(findProducts).hasSize(1);
    }

    @DisplayName("상품 저장 테스트")
    @Test
    void save() {
        // when
        final ProductId saveId = productJdbcRepository.save(CHICKEN);

        final Optional<Product> maybeProduct = productJdbcRepository.findByProductId(saveId);
        assertThat(maybeProduct).isPresent();

        final Product product = maybeProduct.get();

        // then
        assertThat(product)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(CHICKEN);
    }

    @DisplayName("상품 삭제 테스트")
    @Test
    void deleteByProductId() {
        // given
        final ProductId productId = productJdbcRepository.save(CHICKEN);

        // when
        productJdbcRepository.deleteByProductId(productId);

        final Optional<Product> maybeProduct = productJdbcRepository.findByProductId(productId);

        // then
        assertThat(maybeProduct).isNotPresent();
    }

    @DisplayName("상품 갱신 테스트")
    @Test
    void updateProduct() {
        // given
        final ProductId productId = productJdbcRepository.save(CHICKEN);
        final ProductUpdateRequest request = new ProductUpdateRequest("kiara", 300.0, "이미지2");

        // when
        final ProductId updateProductId = productJdbcRepository.updateByProductId(productId, request);

        // then
        assertThat(updateProductId).isEqualTo(productId);
    }
}
