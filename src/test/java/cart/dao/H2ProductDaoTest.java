package cart.dao;

import cart.entity.ProductEntity;
import java.util.Optional;
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
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertTrue;

@JdbcTest
class H2ProductDaoTest {

    private H2ProductDao h2ProductDao;

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    private SimpleJdbcInsert jdbcInsert;

    @BeforeEach
    void setUp() {
        h2ProductDao = new H2ProductDao(jdbcTemplate);
        this.jdbcInsert = new SimpleJdbcInsert(jdbcTemplate.getJdbcTemplate())
                .withTableName("product")
                .usingGeneratedKeyColumns("id");
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

    @Test
    void findAll() {
        //given
        saveProductAndReturnKey();

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

    @Test
    void findById() {
        //given
        final Long id = saveProductAndReturnKey();

        //when
        final ProductEntity actual = h2ProductDao.findById(id).get();

        //then
        assertAll(
                () -> assertThat(actual.getName()).isEqualTo("포이"),
                () -> assertThat(actual.getImageUrl()).isEqualTo("poi"),
                () -> assertThat(actual.getPrice()).isEqualTo(30000)
        );
    }

    @Test
    void findByIdWithInvalidId() {
        //given

        //when
        final Optional<ProductEntity> actual = h2ProductDao.findById(Long.MAX_VALUE);

        //then
        assertTrue(actual.isEmpty());
    }

    @Test
    void update() {
        //given
        final Long id = saveProductAndReturnKey();
        final ProductEntity productEntity = new ProductEntity(id, "포이2", "poi2", 50000);

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
        final Long id = saveProductAndReturnKey();

        //when
        h2ProductDao.delete(id);

        //then
        final List<ProductEntity> productEntities = getProducts();
        assertThat(productEntities).hasSize(0);
    }

    private Long saveProductAndReturnKey() {
        final ProductEntity productEntity = new ProductEntity(1L, "포이", "poi", 30000);
        final SqlParameterSource parameterSource = new BeanPropertySqlParameterSource(productEntity);
        return jdbcInsert.executeAndReturnKey(parameterSource).longValue();
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
}
