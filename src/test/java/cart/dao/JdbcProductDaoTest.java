package cart.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.jdbc.Sql;

import cart.dao.entity.Product;

@JdbcTest
@Sql({"/dropTable.sql", "/data.sql"})
class JdbcProductDaoTest {

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
        final Long id = saveProduct("치킨", 10000, "imgUrl");

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
        saveProduct("치킨", 10000, "chickenImage");
        saveProduct("샐러드", 30000, "saladImage");

        // when
        final List<Product> products = productDao.findAll();

        // then
        assertThat(products).hasSize(2);
    }

    @Test
    @DisplayName("id가 일치하는 상품을 삭제한다.")
    void delete() {
        // given
        final Long id = saveProduct("치킨", 10000, "chickenImage");
        saveProduct("피자", 30000, "pizzaImage");

        // when
        productDao.delete(id);

        // then
        final List<Product> products = productDao.findAll();
        final boolean isDeleted = products.stream()
                .anyMatch(product -> Objects.equals(product.getId(), id));
        assertThat(isDeleted).isFalse();
    }

    @Test
    @DisplayName("id가 일치하는 상품을 수정한다.")
    void update() {
        // given
        final Long id = saveProduct("치킨", 10000, "chickenImage");
        final Product updateProduct = new Product("샐러드", 20000, "saladImg");

        // when
        productDao.update(id, updateProduct);

        //
        final Product result = findById(id);
        assertAll(
                () -> assertThat(result.getName()).isEqualTo("샐러드"),
                () -> assertThat(result.getPrice()).isEqualTo(20000),
                () -> assertThat(result.getImgUrl()).isEqualTo("saladImg")
        );
    }

    private Long saveProduct(final String name, final int price, String imgUrl) {
        final Product product = new Product(name, price, imgUrl);
        return productDao.save(product);
    }

    private Product findById(Long id) {
        final String selectQuery = "select * from product where id = :id";
        final Map<String, Long> params = Collections.singletonMap("id", id);
        final RowMapper<Product> rowMapper = BeanPropertyRowMapper.newInstance(Product.class);

        return jdbcTemplate.queryForObject(selectQuery, params, rowMapper);
    }
}
