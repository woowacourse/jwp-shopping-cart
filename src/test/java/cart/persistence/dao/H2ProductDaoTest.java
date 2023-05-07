package cart.persistence.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import cart.persistence.entity.ProductEntity;
import java.sql.PreparedStatement;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

@JdbcTest
class H2ProductDaoTest {

    private final ProductDao productDao;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public H2ProductDaoTest(JdbcTemplate jdbcTemplate) {
        this.productDao = new H2ProductDao(jdbcTemplate);
    }

    @DisplayName("상품을 저장한다.")
    @Test
    void shouldSaveProductWhenRequest() {
        final ProductEntity productEntityToSave = ProductEntity.createToSave("changer", 10, "domain.com");
        final long productId = this.productDao.save(productEntityToSave);
        final String sql = "SELECT id, name, price, image_url FROM product WHERE id = ?";

        final ProductEntity productEntityFromDb = jdbcTemplate.queryForObject(sql,
                (resultSet, rowNumber) -> ProductEntity.create(
                        resultSet.getLong("id"),
                        resultSet.getString("name"),
                        resultSet.getLong("price"),
                        resultSet.getString("image_url"))
                , productId);

        assertAll(
                () -> assertThat(productEntityFromDb.getName()).isEqualTo("changer"),
                () -> assertThat(productEntityFromDb.getPrice()).isEqualTo(10),
                () -> assertThat(productEntityFromDb.getImageUrl()).isEqualTo("domain.com")
        );
    }

    @DisplayName("상품 전체를 조회한다.")
    @Test
    void shouldReturnAllProductsWhenRequest() {
        jdbcTemplate.update("INSERT INTO product (name, price, image_url) VALUES (?, ?, ?)", "사과", 100, "domain.com");
        jdbcTemplate.update("INSERT INTO product (name, price, image_url) VALUES (?, ?, ?)", "당근", 100, "domain.com");

        final List<ProductEntity> productEntities = this.productDao.findAll();

        assertAll(
                () -> assertThat(productEntities).hasSize(2),
                () -> assertThat(productEntities.get(0).getName()).isEqualTo("사과"),
                () -> assertThat(productEntities.get(0).getPrice()).isEqualTo(100),
                () -> assertThat(productEntities.get(0).getImageUrl()).isEqualTo("domain.com"),
                () -> assertThat(productEntities.get(1).getName()).isEqualTo("당근")
        );
    }

    @DisplayName("상품을 수정한다.")
    @Test
    void shouldUpdateWhenRequest() {
        //given
        final KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO product (name, price, image_url) VALUES (?, ?, ?)", new String[]{"id"});
            preparedStatement.setString(1, "사과");
            preparedStatement.setLong(2, 100);
            preparedStatement.setString(3, "domain.com");
            return preparedStatement;
        }, keyHolder);

        long productId = keyHolder.getKey().longValue();
        ProductEntity productEntityToUpdate = ProductEntity.create(
                productId,
                "당근",
                1000,
                "domain.kr"
        );

        //when
        this.productDao.update(productEntityToUpdate);

        ProductEntity productEntityAfterUpdate = jdbcTemplate.queryForObject(
                "SELECT id, name, price, image_url FROM product WHERE id = ?",
                (resultSet, rowNumber) -> ProductEntity.create(
                        resultSet.getLong("id"),
                        resultSet.getString("name"),
                        resultSet.getLong("price"),
                        resultSet.getString("image_url")
                ), productId);

        //then
        assertAll(
                () -> assertThat(productEntityAfterUpdate.getId()).isEqualTo(productEntityToUpdate.getId()),
                () -> assertThat(productEntityAfterUpdate.getName()).isEqualTo(productEntityToUpdate.getName()),
                () -> assertThat(productEntityAfterUpdate.getPrice()).isEqualTo(productEntityToUpdate.getPrice()),
                () -> assertThat(productEntityAfterUpdate.getImageUrl()).isEqualTo(productEntityToUpdate.getImageUrl())
        );
    }

    @DisplayName("상품을 삭제한다.")
    @Test
    void shouldDeleteWhenRequest() {

        //given
        final KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update("INSERT INTO product (name, price, image_url) VALUES (?, ?, ?)", "당근", 1000, "domain.com");
        jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO product (name, price, image_url) VALUES (?, ?, ?)", new String[]{"id"});
            preparedStatement.setString(1, "사과");
            preparedStatement.setLong(2, 100);
            preparedStatement.setString(3, "domain.com");
            return preparedStatement;
        }, keyHolder);
        long id = keyHolder.getKey().longValue();

        //when
        this.productDao.deleteById(id);

        List<ProductEntity> productEntities = jdbcTemplate.query(
                "SELECT id, name, price, image_url FROM product",
                (resultSet, rowNumber) -> ProductEntity.create(
                        resultSet.getLong("id"),
                        resultSet.getString("name"),
                        resultSet.getLong("price"),
                        resultSet.getString("image_url")
                ));

        //then
        assertThat(productEntities).hasSize(1);
    }

    @DisplayName("상품 1개를 ID로 조회한다.")
    @Test
    void shouldFindProductByIdWhenRequest() {

        //given
        final KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO product (name, price, image_url) VALUES (?, ?, ?)", new String[]{"id"});
            preparedStatement.setString(1, "사과");
            preparedStatement.setLong(2, 100);
            preparedStatement.setString(3, "domain.com");
            return preparedStatement;
        }, keyHolder);
        long id = keyHolder.getKey().longValue();

        //when
        ProductEntity productEntity = this.productDao.findById(id).orElse(null);

        //then
        assertThat(productEntity.getId()).isEqualTo(id);
    }

    @DisplayName("없는 ID로 조회하면 빈 값을 반환한다.")
    @Test
    void shouldReturnEmptyValueWhenFindByIdNotExist() {
        // Product를 생성하지 않고 ID로 조회
        assertThat(this.productDao.findById(1L).isPresent()).isFalse();
    }
}
