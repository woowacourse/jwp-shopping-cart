package cart.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.tuple;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

import cart.TestFixture;
import cart.dao.ProductDao;
import cart.domain.Product;
import cart.repository.exception.NoSuchIdException;

@JdbcTest
class ProductRepositoryTest {

    private static final int INVALID_ID = Integer.MIN_VALUE;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        this.productRepository = new ProductRepository(new ProductDao(jdbcTemplate));

        productRepository.save(new Product("땡칠", TestFixture.IMAGE_0CHIL, 1000L));
        productRepository.save(new Product("비버", TestFixture.IMAGE_BEAVER, 1000L));
        productRepository.save(new Product("코다", TestFixture.IMAGE_KODA, 1000000L));
    }

    @Test
    @DisplayName("상품 저장")
    void save() {
        assertThat(productRepository.getAll())
                .extracting("name", "image", "price")
                .contains(
                        tuple("비버", TestFixture.IMAGE_BEAVER, 1000L)
                );
    }

    @Test
    @DisplayName("상품 제거")
    void delete() {
        productRepository.delete(getGreatestId());

        assertThat(productRepository.getAll())
                .extracting("name")
                .doesNotContain("코다");
    }

    @Test
    @DisplayName("상품 수정")
    void update() {
        productRepository.update(new Product(getGreatestId(), "코다", "VERY_BIG_IMAGE", 100L));

        assertThat(productRepository.getAll())
                .extracting("name", "image", "price")
                .contains(tuple("코다", "VERY_BIG_IMAGE", 100L));
    }

    @Test
    @DisplayName("모든 상품 가져오기")
    void getAll() {
        assertThat(productRepository.getAll())
                .extracting("name", "image", "price")
                .containsExactlyInAnyOrder(
                        tuple("땡칠", TestFixture.IMAGE_0CHIL, 1000L),
                        tuple("비버", TestFixture.IMAGE_BEAVER, 1000L),
                        tuple("코다", TestFixture.IMAGE_KODA, 1000000L)
                );
    }

    @DisplayName("수정된 대상이 없으면 예외를 던진다")
    @Test
    void noUpdateCountThrows() {
        assertThatThrownBy(() -> productRepository.update(new Product(INVALID_ID, "땡칠", TestFixture.IMAGE_KODA, 1000L)))
                .isInstanceOf(NoSuchIdException.class);
    }

    @DisplayName("제거된 대상이 없으면 예외를 던진다")
    @Test
    void noDeleteCountThrows() {
        assertThatThrownBy(() -> productRepository.delete(INVALID_ID))
                .isInstanceOf(NoSuchIdException.class);
    }

    private Integer getGreatestId() {
        return jdbcTemplate.queryForObject("SELECT MAX(id) FROM product", Integer.class);
    }
}