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
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@JdbcTest
class H2ProductDaoTest {

    private final ProductEntity productEntity = new ProductEntity("포이", "poi", 30000);

    private H2ProductDao h2ProductDao;

    private SimpleJdbcInsert simpleJdbcInsert;
    @Autowired
    private NamedParameterJdbcTemplate namedParameterjdbcTemplate;

    @BeforeEach
    void setUp() {
        simpleJdbcInsert = new SimpleJdbcInsert(namedParameterjdbcTemplate.getJdbcTemplate())
            .withTableName("product")
            .usingGeneratedKeyColumns("id");
        h2ProductDao = new H2ProductDao(namedParameterjdbcTemplate);
    }

    @Test
    void save() {
        //when
        h2ProductDao.save(productEntity);

        //then
        final List<ProductEntity> productEntities = getProducts();
        final ProductEntity actual = productEntities.get(0);

        assertThat(productEntities).hasSize(1);
        assertThat(actual)
            .usingRecursiveComparison()
            .ignoringFields("id")
            .isEqualTo(productEntity);
    }

    private List<ProductEntity> getProducts() {
        final String sql = "select * from product";
        final List<ProductEntity> productEntities = namedParameterjdbcTemplate.getJdbcOperations().query(sql, (resultSet, count) ->
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
        assertThat(productEntities).hasSize(1);
        assertThat(actual)
            .usingRecursiveComparison()
            .ignoringFields("id")
            .isEqualTo(productEntity);
    }

    private Long saveProduct() {
        final SqlParameterSource parameterSource = new BeanPropertySqlParameterSource(productEntity);
        return simpleJdbcInsert.executeAndReturnKey(parameterSource).longValue();
    }

    @Test
    void update() {
        //given
        final Long id = saveProduct();
        final ProductEntity productEntity = new ProductEntity(id, "포이2", "poi2", 50000);

        //when
        h2ProductDao.update(productEntity);

        //then
        final List<ProductEntity> productEntities = getProducts();
        final ProductEntity actual = productEntities.get(0);

        assertThat(productEntities).hasSize(1);
        assertThat(actual)
            .usingRecursiveComparison()
            .isEqualTo(productEntity);
    }

    @Test
    void delete() {
        //given
        final Long id = saveProduct();

        //when
        h2ProductDao.delete(id);

        //then
        final List<ProductEntity> productEntities = getProducts();
        assertThat(productEntities).isEmpty();
    }
}
