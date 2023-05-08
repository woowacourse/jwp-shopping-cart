package cart.repository;

import static cart.TestFixture.IMAGE_CHICKEN;
import static cart.TestFixture.IMAGE_ICE_CREAM;
import static cart.TestFixture.IMAGE_VANILLA_LATTE;
import static cart.TestFixture.NAME_CHICKEN;
import static cart.TestFixture.NAME_ICE_CREAM;
import static cart.TestFixture.NAME_VANILLA_LATTE;
import static cart.TestFixture.PRICE_CHICKEN;
import static cart.TestFixture.PRICE_ICE_CREAM;
import static cart.TestFixture.PRICE_VANILLA_LATTE;
import static cart.TestFixture.PRODUCT_CHICKEN;
import static cart.TestFixture.PRODUCT_ICE_CREAM;
import static cart.TestFixture.PRODUCT_VANILLA_LATTE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.tuple;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

import cart.dao.ProductDao;
import cart.domain.Product;
import cart.repository.exception.NoSuchProductException;

@JdbcTest
class ProductRepositoryTest {

    private static final int INVALID_ID = Integer.MIN_VALUE;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        this.productRepository = new ProductRepository(new ProductDao(jdbcTemplate));

        productRepository.save(PRODUCT_CHICKEN);
        productRepository.save(PRODUCT_ICE_CREAM);
        productRepository.save(PRODUCT_VANILLA_LATTE);
    }

    @Test
    @DisplayName("상품 저장")
    void save() {
        assertThat(productRepository.getAll())
                .extracting("name", "image", "price")
                .contains(
                        tuple(NAME_VANILLA_LATTE, IMAGE_VANILLA_LATTE, PRICE_VANILLA_LATTE)
                );
    }

    @Test
    @DisplayName("상품 제거")
    void delete() {
        productRepository.delete(getGreatestId());

        assertThat(productRepository.getAll())
                .extracting("name")
                .doesNotContain(NAME_VANILLA_LATTE);
    }

    @Test
    @DisplayName("상품 수정")
    void update() {
        productRepository.update(
                new Product(getGreatestId(), NAME_VANILLA_LATTE, "VERY_BIG_IMAGE", PRICE_VANILLA_LATTE));

        assertThat(productRepository.getAll())
                .extracting("name", "image", "price")
                .contains(tuple(NAME_VANILLA_LATTE, "VERY_BIG_IMAGE", PRICE_VANILLA_LATTE));
    }

    @Test
    @DisplayName("모든 상품 가져오기")
    void getAll() {
        assertThat(productRepository.getAll())
                .extracting("name", "image", "price")
                .containsExactlyInAnyOrder(
                        tuple(NAME_CHICKEN, IMAGE_CHICKEN, PRICE_CHICKEN),
                        tuple(NAME_ICE_CREAM, IMAGE_ICE_CREAM, PRICE_ICE_CREAM),
                        tuple(NAME_VANILLA_LATTE, IMAGE_VANILLA_LATTE, PRICE_VANILLA_LATTE)
                );
    }

    @DisplayName("수정된 대상이 없으면 예외를 던진다")
    @Test
    void noUpdateCountThrows() {
        assertThatThrownBy(
                () -> productRepository.update(new Product(INVALID_ID, NAME_CHICKEN, IMAGE_CHICKEN, PRICE_CHICKEN)))
                .isInstanceOf(NoSuchProductException.class);
    }

    @DisplayName("제거된 대상이 없으면 예외를 던진다")
    @Test
    void noDeleteCountThrows() {
        assertThatThrownBy(() -> productRepository.delete(INVALID_ID))
                .isInstanceOf(NoSuchProductException.class);
    }

    private Integer getGreatestId() {
        return jdbcTemplate.queryForObject("SELECT MAX(id) FROM product", Integer.class);
    }
}
