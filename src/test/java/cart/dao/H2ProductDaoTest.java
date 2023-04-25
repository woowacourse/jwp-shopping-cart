package cart.dao;

import cart.entity.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@JdbcTest
class H2ProductDaoTest {

    private H2ProductDao h2ProductDao;

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        h2ProductDao = new H2ProductDao(jdbcTemplate);
        jdbcTemplate.getJdbcOperations().execute("ALTER TABLE product ALTER COLUMN id RESTART WITH 1");
    }

    @Test
    void save() {
        //given
        final Product product = new Product(1L, "포이", "poi", 30000);

        //when
        h2ProductDao.save(product);

        //then
        final List<Product> products = getProducts();
        final Product actual = products.get(0);
        assertAll(
                () -> assertThat(products).hasSize(1),
                () -> assertThat(actual.getName()).isEqualTo("포이"),
                () -> assertThat(actual.getImageUrl()).isEqualTo("poi"),
                () -> assertThat(actual.getPrice()).isEqualTo(30000)
        );
    }

    private List<Product> getProducts() {
        final String sql = "select * from product";
        final List<Product> products = jdbcTemplate.getJdbcOperations().query(sql, (resultSet, count) ->
                new Product(
                        resultSet.getLong("id"),
                        resultSet.getString("name"),
                        resultSet.getString("image_url"),
                        resultSet.getInt("price")
                ));
        return products;
    }

    @Test
    void findAll() {
        //given
        saveProduct();

        //when
        final List<Product> products = h2ProductDao.findAll();

        //then
        final Product actual = products.get(0);
        assertAll(
                () -> assertThat(products).hasSize(1),
                () -> assertThat(actual.getName()).isEqualTo("포이"),
                () -> assertThat(actual.getImageUrl()).isEqualTo("poi"),
                () -> assertThat(actual.getPrice()).isEqualTo(30000)
        );
    }

    private void saveProduct() {
        final Product product = new Product(1L, "포이", "poi", 30000);
        final String sql = "insert into product (name, image_url, price) values(:name, :imageUrl, :price)";
        final SqlParameterSource parameterSource = new BeanPropertySqlParameterSource(product);
        jdbcTemplate.update(sql, parameterSource);
    }

    @Test
    void update() {
        //given
        saveProduct();
        final Product product = new Product(1L, "포이2", "poi2", 50000);

        //when
        h2ProductDao.update(product);

        //then
        final List<Product> products = getProducts();
        final Product actual = products.get(0);
        assertAll(
                () -> assertThat(products).hasSize(1),
                () -> assertThat(actual.getName()).isEqualTo("포이2"),
                () -> assertThat(actual.getImageUrl()).isEqualTo("poi2"),
                () -> assertThat(actual.getPrice()).isEqualTo(50000)
        );
    }

    @Test
    void delete() {
        //given
        saveProduct();

        //when
        h2ProductDao.delete(1L);

        //then
        final List<Product> products = getProducts();
        assertThat(products).hasSize(0);
    }
}