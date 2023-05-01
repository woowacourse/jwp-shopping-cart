package cart.repository;

import cart.dao.ProductJdbcDao;
import cart.entity.ProductEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
class ProductRepositoryTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private ProductJdbcDao productDao;
    private ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        productDao = new ProductJdbcDao(jdbcTemplate);
        this.productRepository = new ProductRepository(productDao);

        productRepository.save("땡칠", "asdf", 100L);
    }

    @Test
    @DisplayName("상품 저장")
    void save() {
        ProductEntity productEntity = productDao.select(getGreatestId());
        assertThat(productEntity).isEqualTo(new ProductEntity(getGreatestId(), "땡칠", "asdf", 100L));
    }

    @Test
    @DisplayName("상품 제거")
    void delete() {
        productRepository.delete(getGreatestId());

        final List<ProductEntity> result = productDao.findAll();
        ProductEntity deletedItem = new ProductEntity(getGreatestId(), "땡칠", "asdf", 100L);

        assertThat(result).doesNotContain(deletedItem);
    }

    @Test
    @DisplayName("상품 수정")
    void update() {
        productRepository.update(getGreatestId(), "땡칠", "VERY_BIG_IMAGE", 100L);

        ProductEntity productEntity = productDao.select(getGreatestId());
        assertThat(productEntity).isEqualTo(new ProductEntity(getGreatestId(), "땡칠", "VERY_BIG_IMAGE", 100L));
    }

    @Test
    @DisplayName("모든 상품 가져오기")
    void getAll() {
        productRepository.save("비버", "SMALL_IMAGE", 100L);

        final List<ProductEntity> result = productDao.findAll();
        final List<ProductEntity> expectedEntities = List.of(
                new ProductEntity(getGreatestId() - 1, "비버", "SMALL_IMAGE", 100L),
                new ProductEntity(getGreatestId(), "땡칠", "asdf", 100L)
        );

        assertThat(result).containsExactlyInAnyOrderElementsOf(expectedEntities);
    }

    private Integer getGreatestId() {
        return jdbcTemplate.queryForObject("SELECT MAX(id) FROM product", Integer.class);
    }
}