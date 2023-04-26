package cart.service;

import cart.dao.ProductJdbcDao;
import cart.entity.ProductEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
class ProductServiceTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private ProductJdbcDao productDao;
    private ProductService productService;

    @BeforeEach
    void setUp() {
        productDao = new ProductJdbcDao(jdbcTemplate);
        this.productService = new ProductService(productDao);

        productService.add("땡칠", "asdf", 100L);
    }

    @Test
    void add() {
        ProductEntity productEntity = productDao.select(getGreatestId());
        assertThat(productEntity).isEqualTo(new ProductEntity(getGreatestId(), "땡칠", "asdf", 100L));
    }

    @Test
    void delete() {
        productService.delete(getGreatestId());

        final List<ProductEntity> result = productDao.findAll();
        ProductEntity deletedItem = new ProductEntity(getGreatestId(), "땡칠", "asdf", 100L);

        assertThat(result).doesNotContain(deletedItem);
    }

    @Test
    void update() {
        productService.update(getGreatestId(), "땡칠", "VERY_BIG_IMAGE", 100L);

        ProductEntity productEntity = productDao.select(getGreatestId());
        assertThat(productEntity).isEqualTo(new ProductEntity(getGreatestId(), "땡칠", "VERY_BIG_IMAGE", 100L));
    }

    @Test
    void getAll() {
        productService.add("비버", "SMALL_IMAGE", 100L);

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