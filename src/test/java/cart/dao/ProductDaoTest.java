package cart.dao;

import cart.domain.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

@JdbcTest
class ProductDaoTest {
    private static final Product PRODUCT_A = new Product("마우스", 10000, "image");
    private static final Product PRODUCT_B = new Product("키보드", 20000, "image2");
    @Autowired
    private DataSource dataSource;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private ProductDao productDao;


    private final RowMapper<Product> rowMapper = (resultSet, rowNum) -> {
        Product product = new Product(
                resultSet.getLong("id"),
                resultSet.getString("name"),
                resultSet.getInt("price"),
                resultSet.getString("image_url"));
        return product;
    };


    @BeforeEach
    void setUp() {
        productDao = new ProductDao(dataSource);
    }

    @Test
    @DisplayName("상품 데이터를 저장한다")
    void save() {
        Long id = productDao.save(PRODUCT_A);

        Product findProduct = findProductById(id);

        assertThat(findProduct).usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(PRODUCT_A);
    }

    private Product findProductById(Long id) {
        return jdbcTemplate.queryForObject("select * from product where id = ?", rowMapper, id);
    }

    @Test
    @DisplayName("저장된 모든 상품 데이터를 가져온다")
    void findAll() {
        jdbcTemplate.update("delete from product");
        productDao.save(PRODUCT_A);
        productDao.save(PRODUCT_B);

        List<Product> findProducts = productDao.findAll();

        assertThat(findProducts.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("상품데이터를 삭제한다.")
    void delete() {
        Long id = productDao.save(PRODUCT_A);
        productDao.deleteById(id);

        assertThatThrownBy(
                () -> findProductById(id)).isInstanceOf(EmptyResultDataAccessException.class);
    }
    @Test
    @DisplayName("상품데이터를 수정한다.")
    void update() {
        Long id = productDao.save(PRODUCT_A);
        productDao.updateById(id, PRODUCT_B);

        Product findProduct = findProductById(id);

        assertAll(
                () -> assertThat(findProduct).usingRecursiveComparison()
                        .ignoringFields("id")
                        .isEqualTo(PRODUCT_B),

                () -> assertThat(findProduct.getId()).isEqualTo(id));
    }
}