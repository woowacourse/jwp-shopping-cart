package cart.persistence.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import cart.persistence.entity.CartProductEntity;
import java.sql.PreparedStatement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

@JdbcTest
class H2CartProductDaoTest {

    private final CartProductDao cartProductDao;

    private final RowMapper<CartProductEntity> rowMapper = (resultSet, rowNumber) -> CartProductEntity.create(
            resultSet.getLong("id"),
            resultSet.getLong("member_id"),
            resultSet.getLong("product_id")
    );

    private long memberId;
    private long productId1;
    private long productId2;

    @Autowired
    private JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    @Autowired
    H2CartProductDaoTest(JdbcTemplate jdbcTemplate) {
        this.cartProductDao = new H2CartProductDao(jdbcTemplate);
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("cart_product")
                .usingGeneratedKeyColumns("id");
    }

    @BeforeEach
    void saveDummies() {
        final KeyHolder keyHolder = new GeneratedKeyHolder();
        // Dummy Member 생성
        this.jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO member (email, password, name, phone_number) VALUES (?, ?, ?, ?)", new String[]{"id"});
            preparedStatement.setString(1, "email.test.com");
            preparedStatement.setString(2, "1234abcd!@");
            preparedStatement.setString(3, "Test Name");
            preparedStatement.setString(4, "01012341234");
            return preparedStatement;
        }, keyHolder);
        this.memberId = keyHolder.getKey().longValue();

        // Dummy Product 1 생성
        this.jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO product (name, price, image_url) VALUES (?, ?, ?)", new String[]{"id"});
            preparedStatement.setString(1, "사과");
            preparedStatement.setInt(2, 100);
            preparedStatement.setString(3, "domain.com");
            return preparedStatement;
        }, keyHolder);
        this.productId1 = keyHolder.getKey().longValue();

        // Dummy Product 2 생성
        this.jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO product (name, price, image_url) VALUES (?, ?, ?)", new String[]{"id"});
            preparedStatement.setString(1, "당근");
            preparedStatement.setInt(2, 100);
            preparedStatement.setString(3, "domain.com");
            return preparedStatement;
        }, keyHolder);
        this.productId2 = keyHolder.getKey().longValue();
    }

//    @DisplayName("모든 장바구니 상품을 불러온다.")
//    @Test

    @DisplayName("장바구니 상품을 저장한다.")
    @Test
    void shouldSaveCartProductWhenRequest() {
        CartProductEntity cartProductEntity = CartProductEntity.createToSave(this.memberId, this.productId1);
        long savedId = this.cartProductDao.save(cartProductEntity);

        String sql = "SELECT id, member_id, product_id FROM cart_product WHERE id = ?";
        CartProductEntity cartProductEntityFromDb = this.jdbcTemplate.queryForObject(sql, rowMapper, savedId);

        assertAll(
                () -> assertThat(cartProductEntityFromDb.getMemberId()).isEqualTo(cartProductEntity.getMemberId()),
                () -> assertThat(cartProductEntityFromDb.getProductId()).isEqualTo(cartProductEntity.getProductId())
        );
    }

    @DisplayName("장바구니 상품이 이미 존재하면, 저장된 ID를 반환한다.")
    @Test
    void shouldReturnSavedIdWhenCartProductExistAlready() {
        CartProductEntity cartProductEntity = CartProductEntity.createToSave(this.memberId, this.productId1);
        SqlParameterSource parameterSource = new BeanPropertySqlParameterSource(cartProductEntity);
        long cartProductId = this.simpleJdbcInsert.executeAndReturnKey(parameterSource).longValue();

        long idSavedAlready = this.cartProductDao.save(cartProductEntity);

        assertThat(idSavedAlready).isEqualTo(cartProductId);
    }

    @DisplayName("장바구니 상품을 삭제하고 true를 반환한다.")
    @Test
    void shouldReturnTrueWhenDeleteCartProduct() {
        CartProductEntity cartProductEntityToSave = CartProductEntity.createToSave(this.memberId, this.productId1);
        SqlParameterSource parameterSource = new BeanPropertySqlParameterSource(cartProductEntityToSave);
        long savedId = this.simpleJdbcInsert.executeAndReturnKey(parameterSource).longValue();

        CartProductEntity cartProductEntityToDelete = CartProductEntity.createToSave(this.memberId, this.productId1);
        boolean isDeleted = this.cartProductDao.delete(cartProductEntityToDelete);

        String sql = "SELECT id, member_id, product_id FROM cart_product WHERE id = ?";

        assertAll(
                () -> assertThat(isDeleted).isTrue(),
                () -> assertThatThrownBy(() -> this.jdbcTemplate.queryForObject(sql, rowMapper, savedId))
                        .isInstanceOf(EmptyResultDataAccessException.class)
        );
    }

    @DisplayName("없는 장바구니 상품을 삭제하면 false를 반환한다.")
    @Test
    void shouldReturnFalseWhenRequestToDeleteCartProductNotExist() {
        CartProductEntity cartProductEntityToDelete = CartProductEntity.createToSave(this.memberId, this.productId1);
        boolean isDeleted = this.cartProductDao.delete(cartProductEntityToDelete);

        assertThat(isDeleted).isFalse();
    }
}
