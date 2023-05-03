package cart.repository;

import cart.entity.CartEntity;
import cart.entity.ProductEntity;
import cart.entity.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;

import javax.sql.DataSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@JdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CartDaoTest {

    @Autowired
    private DataSource dataSource;

    private JdbcTemplate jdbcTemplate;
    private CartDao cartDao;

    @BeforeEach
    void setUp() {
        jdbcTemplate = new JdbcTemplate(dataSource);
        cartDao = new CartDao(dataSource);
    }

    @Test
    @DisplayName("장바구니에 추가 성공")
    @Sql(scripts = "/product_dummy_data.sql")
    void create_success() {
        // given
        final String findDitooUserSql = "select * from users where id = 1";
        final UserEntity ditooUser = jdbcTemplate.queryForObject(findDitooUserSql,
                (rs, rowNum) -> new UserEntity(
                        rs.getInt("id"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("name")
                ));
        final String findDitooProductSql = "select * from product where id = 2";
        final ProductEntity ditooProduct = jdbcTemplate.queryForObject(findDitooProductSql,
                (rs, rowNum) -> new ProductEntity(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("image"),
                        rs.getInt("price")
                ));

        // when
        final int id = cartDao.create(ditooUser, ditooProduct);
        final String sql = "select * from cart where user_id = 1 and product_id = 2";
        final CartEntity result = jdbcTemplate.queryForObject(sql,
                (rs, rowNum) -> new CartEntity(
                        rs.getInt("id"),
                        rs.getInt("user_id"),
                        rs.getInt("product_id")
                ));

        // then
        assertAll(
                () -> assertThat(result.getId()).isEqualTo(id),
                () -> assertThat(result.getUserId()).isEqualTo(ditooUser.getId()),
                () -> assertThat(result.getProductId()).isEqualTo(ditooProduct.getId())
        );
    }
}