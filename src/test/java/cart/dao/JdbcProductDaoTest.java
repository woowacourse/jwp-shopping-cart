package cart.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.Collections;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import cart.dao.entity.Product;

@JdbcTest
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
        final Product product = new Product("치킨", 10000, "imgUrl");

        // when
        final Long id = productDao.save(product);

        // then
        final String selectQuery = "select * from product where id = :id";
        final Map<String, Long> params = Collections.singletonMap("id", id);
        final RowMapper<Product> rowMapper = BeanPropertyRowMapper.newInstance(Product.class);
        final Product result = jdbcTemplate.queryForObject(selectQuery, params, rowMapper);

        assertAll(
                () -> assertThat(result.getName()).isEqualTo("치킨"),
                () -> assertThat(result.getPrice()).isEqualTo(10000),
                () -> assertThat(result.getImgUrl()).isEqualTo("imgUrl")
        );
    }
}
