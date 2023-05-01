package cart.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

import cart.dao.ProductJdbcDao;

@JdbcTest
class ProductRepositoryTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        this.productRepository = new ProductRepository(new ProductJdbcDao(jdbcTemplate));

        productRepository.save("땡칠", "https://avatars.githubusercontent.com/u/39221443", 1000L);
        productRepository.save("비버", "https://avatars.githubusercontent.com/u/109223081", 1000L);
        productRepository.save("코다", "https://avatars.githubusercontent.com/u/63405904", 1000000L);
    }

    @Test
    @DisplayName("상품 저장")
    void save() {
        assertThat(productRepository.getAll())
                .extracting("name", "image", "price")
                .contains(
                        tuple("비버", "https://avatars.githubusercontent.com/u/109223081", 1000L)
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
        productRepository.update(getGreatestId(), "코다", "VERY_BIG_IMAGE", 100L);

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
                        tuple("땡칠", "https://avatars.githubusercontent.com/u/39221443", 1000L),
                        tuple("비버", "https://avatars.githubusercontent.com/u/109223081", 1000L),
                        tuple("코다", "https://avatars.githubusercontent.com/u/63405904", 1000000L)
                );
    }

    private Integer getGreatestId() {
        return jdbcTemplate.queryForObject("SELECT MAX(id) FROM product", Integer.class);
    }
}