package cart.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import cart.domain.Product;
import java.util.List;
import javax.sql.DataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

@JdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
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
    void save_success() {
        //when
        Long id = productDao.save(PRODUCT_A);
        //then
        Product savedProduct = findProductById(id);
        assertThat(savedProduct).usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(PRODUCT_A);
    }

    private Product findProductById(Long id) {
        return jdbcTemplate.queryForObject("select * from product where id = ?", rowMapper, id);
    }

    @Test
    @DisplayName("저장된 모든 상품 데이터를 가져온다")
    void findAll() {
        //given
        productDao.save(PRODUCT_A);
        productDao.save(PRODUCT_B);
        //when
        List<Product> actual = productDao.findAll();
        //then
        assertThat(actual.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("상품데이터를 삭제한다.")
    void delete_success() {
        //given
        Long id = productDao.save(PRODUCT_A);
        //when
        productDao.deleteById(id);
        //then
        assertThatThrownBy(() -> findProductById(id)).isInstanceOf(EmptyResultDataAccessException.class);
    }

    @Test
    @DisplayName("상품 데이터를 업데이트한다.")
    void update_success() {
        //given
        Long id = productDao.save(PRODUCT_A);
        //when
        productDao.updateById(id, PRODUCT_B);
        //then
        Product actual = findProductById(id);
        assertThat(actual).usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(PRODUCT_B);
    }
}
