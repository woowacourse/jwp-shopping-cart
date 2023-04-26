package cart.dao;

import cart.entity.ProductEntity;
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
class H2ProductDaoTestEntity {

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
        final ProductEntity productEntity = new ProductEntity(1L, "포이", "poi", 30000);

        //when
        h2ProductDao.save(productEntity);

        //then
        final List<ProductEntity> productEntities = getProducts();
        final ProductEntity actual = productEntities.get(0);
        assertAll(
                () -> assertThat(productEntities).hasSize(1),
                () -> assertThat(actual.getName()).isEqualTo("포이"),
                () -> assertThat(actual.getImageUrl()).isEqualTo("poi"),
                () -> assertThat(actual.getPrice()).isEqualTo(30000)
        );
    }

    private List<ProductEntity> getProducts() {
        final String sql = "select * from product";
        final List<ProductEntity> productEntities = jdbcTemplate.getJdbcOperations().query(sql, (resultSet, count) ->
                new ProductEntity(
                        resultSet.getLong("id"),
                        resultSet.getString("name"),
                        resultSet.getString("image_url"),
                        resultSet.getInt("price")
                ));
        return productEntities;
    }

    @Test
    void findAll() {
        //given
        saveProduct();

        //when
        final List<ProductEntity> productEntities = h2ProductDao.findAll();

        //then
        final ProductEntity actual = productEntities.get(0);
        assertAll(
                () -> assertThat(productEntities).hasSize(1),
                () -> assertThat(actual.getName()).isEqualTo("포이"),
                () -> assertThat(actual.getImageUrl()).isEqualTo("poi"),
                () -> assertThat(actual.getPrice()).isEqualTo(30000)
        );
    }

    private void saveProduct() {
        final ProductEntity productEntity = new ProductEntity(1L, "포이", "poi", 30000);
        final String sql = "insert into product (name, image_url, price) values(:name, :imageUrl, :price)";
        final SqlParameterSource parameterSource = new BeanPropertySqlParameterSource(productEntity);
        jdbcTemplate.update(sql, parameterSource);
    }

    @Test
    void update() {
        //given
        saveProduct();
        final ProductEntity productEntity = new ProductEntity(1L, "포이2", "poi2", 50000);

        //when
        h2ProductDao.update(productEntity);

        //then
        final List<ProductEntity> productEntities = getProducts();
        final ProductEntity actual = productEntities.get(0);
        assertAll(
                () -> assertThat(productEntities).hasSize(1),
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
        final List<ProductEntity> productEntities = getProducts();
        assertThat(productEntities).hasSize(0);
    }
}