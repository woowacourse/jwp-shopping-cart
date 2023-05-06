package cart.dao;

import cart.domain.product.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@JdbcTest
@Sql(scripts = {"classpath:test.sql"})
class JdbcProductDaoTest {

    private final RowMapper<Product> productRowMapper = (resultSet, rowNum) ->
            new Product(
                    resultSet.getLong("id"),
                    resultSet.getString("name"),
                    resultSet.getInt("price"),
                    resultSet.getString("image")
            );
    private final JdbcProductDao jdbcProductDao;

    private final JdbcTemplate jdbcTemplate;

    private JdbcProductDaoTest(@Autowired final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcProductDao = new JdbcProductDao(jdbcTemplate);
    }

    @Test
    @DisplayName("Product 삽입 테스트")
    void insert() {
        final long id = jdbcProductDao.insert(new Product("IO", 10000, null));
        assertThat(id).isPositive();
    }

    @Test
    @DisplayName("Product Id로 조회 테스트")
    void findById() {
        final long id1 = jdbcProductDao.insert(new Product("IO", 10000, null));
        final long id2 = jdbcProductDao.insert(new Product("ASH", 10000, null));

        final Optional<Product> product1 = jdbcProductDao.findById(id1);
        final Optional<Product> product2 = jdbcProductDao.findById(id2);

        assertAll(
                () -> assertThat(product1).isPresent(),
                () -> assertThat(product1.get().getName()).isEqualTo("IO"),
                () -> assertThat(product2).isPresent(),
                () -> assertThat(product2.get().getName()).isEqualTo("ASH")
        );
    }

    @Test
    @DisplayName("Product 조회 테스트")
    void findAll() {
        jdbcProductDao.insert(new Product("IO", 10000, null));
        jdbcProductDao.insert(new Product("ASH", 10000, null));
        jdbcProductDao.insert(new Product("BROWN", 10000, null));

        assertThat(jdbcProductDao.findAll()).extracting("name")
                .containsExactly("IO", "ASH", "BROWN");
    }

    @Test
    @DisplayName("Product 갱신 테스트")
    void update() {
        final long id = jdbcProductDao.insert(new Product("IO", 10000, null));

        jdbcProductDao.update(new Product(id, "ASH", 1000, "image"));

        final Product product = jdbcTemplate.queryForObject("SELECT * FROM product WHERE id = ?", productRowMapper, id);

        assertAll(
                () -> assertThat(product).isNotNull(),
                () -> assertThat(product.getId()).isEqualTo(id),
                () -> assertThat(product.getName()).isEqualTo("ASH"),
                () -> assertThat(product.getPrice()).isEqualTo(1000),
                () -> assertThat(product.getImage()).isEqualTo("image")
        );
    }

    @Test
    @DisplayName("Product 삭제 테스트")
    void delete() {
        final long id = jdbcProductDao.insert(new Product("IO", 10000, null));

        jdbcProductDao.deleteById(id);

        assertThat(jdbcProductDao.findById(id)).isNull();
    }
}
