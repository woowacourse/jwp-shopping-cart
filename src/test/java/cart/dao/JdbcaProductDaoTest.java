package cart.dao;

import cart.JdbcMySqlDialectTest;
import cart.dao.entity.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@JdbcMySqlDialectTest
class JdbcaProductDaoTest {

    @Autowired
    NamedParameterJdbcTemplate jdbcTemplate;

    ProductDao productDao;

    @BeforeEach
    void setup() {
        productDao = new JdbcProductDao(jdbcTemplate);
    }

    @Test
    @DisplayName("상품의 내용을 저장한다.")
    void save() {
        // given
        final Long id = saveProduct("치킨", 10000);

        // then
        final Product result = findById(id);

        assertAll(
                () -> assertThat(result.getName()).isEqualTo("치킨"),
                () -> assertThat(result.getPrice()).isEqualTo(10000),
                () -> assertThat(result.getImgUrl()).isEqualTo("imgUrl")
        );
    }

    @Test
    @DisplayName("전체 상품을 조회한다.")
    void findAll() {
        // given
        saveProduct("치킨", 10000);
        saveProduct("샐러드", 30000);

        // when
        final List<Product> products = productDao.findAll();

        // then
        assertThat(products).hasSize(2);
    }

    @Test
    @DisplayName("id가 일치하는 상품을 삭제한다.")
    void delete() {
        // given
        final Long id = saveProduct("치킨", 10000);
        saveProduct("치킨", 10000);

        // when
        productDao.delete(id);

        // then
        assertThat(productDao.findAll()).hasSize(1);
    }

    @Test
    @DisplayName("id가 일치하는 상품을 수정한다.")
    void update() {
        // given
        final Long id = saveProduct("치킨", 10000);
        final Product updateProduct = new Product("샐러드", 20000, "changeImgUrl");

        // when
        productDao.update(id, updateProduct);

        //
        final Product result = findById(id);
        assertAll(
                () -> assertThat(result.getName()).isEqualTo("샐러드"),
                () -> assertThat(result.getPrice()).isEqualTo(20000),
                () -> assertThat(result.getImgUrl()).isEqualTo("changeImgUrl")
        );
    }

    private Long saveProduct(final String name, final int price) {
        final Product product = new Product(name, price, "imgUrl");
        return productDao.save(product);
    }

    private Product findById(Long id) {
        final String selectQuery = "select * from product where id = :id";
        final Map<String, Long> params = Collections.singletonMap("id", id);

        return jdbcTemplate.queryForObject(selectQuery, params, createRowMapper());
    }

    private static RowMapper<Product> createRowMapper() {
        return (rs, rowNum) -> new Product(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getInt("price"),
                rs.getString("img_url")
        );
    }
}
