package cart.dao;

import cart.domain.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@JdbcTest
@Sql("classpath:initializeTestDb.sql")
class CartDaoTest {

    private final CartDao cartDao;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final RowMapper<Cart> cartRowMapper = (resultSet, rowNumber) -> new Cart.Builder()
            .id(resultSet.getLong("id"))
            .userId(resultSet.getLong("user_id"))
            .itemId(resultSet.getLong("item_id"))
            .build();
    private final RowMapper<CartData> cartDataRowMapper = (resultSet, rowNumber) -> new CartData(
            resultSet.getLong("id"),
            new Name(resultSet.getString("name")),
            new ImageUrl(resultSet.getString("image_url")),
            new Price(resultSet.getInt("price"))
    );

    @Autowired
    CartDaoTest(final NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.cartDao = new CartDao(namedParameterJdbcTemplate);
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @DisplayName("장바구니를 저장한다")
    @Test
    void save() {
        //given
        Cart cart = new Cart.Builder().userId(1L)
                                      .itemId(1L)
                                      .build();
        //when
        Long cartId = cartDao.save(cart);
        //then
        Cart findCart = namedParameterJdbcTemplate.queryForObject(
                "SELECT * FROM carts WHERE id = :id",
                new MapSqlParameterSource("id", cartId),
                cartRowMapper
        );
        assertThat(findCart).isEqualTo(
                new Cart.Builder()
                        .id(cartId)
                        .userId(1L)
                        .itemId(1L)
                        .build()
        );
    }

    @DisplayName("특정 사용자의 장바구니의 전체 목록을 조회한다")
    @Test
    void findAll() {
        //when
        List<CartData> carts = cartDao.findAll(1L);
        //then
        assertThat(carts).hasSize(2);
    }

    @DisplayName("아이디를 통해 장바구니를 조회한다")
    @Test
    void findById() {
        //when
        Cart findCart = cartDao.findById(1L)
                               .orElseThrow();
        //then
        assertThat(findCart).isEqualTo(
                new Cart.Builder()
                        .id(1L)
                        .userId(1L)
                        .itemId(1L)
                        .build()
        );
    }

    @DisplayName("없는 아이디를 조회하면 빈값을 반환한다")
    @Test
    void findByNotExistId() {
        //when
        Optional<Cart> findCart = cartDao.findById(100L);
        //then
        assertThat(findCart).isEmpty();
    }

    @DisplayName("장바구니를 삭제한다")
    @Test
    void deleteBy() {
        //given
        Cart targetCart = namedParameterJdbcTemplate.queryForObject(
                "SELECT * FROM carts WHERE id = :id",
                new MapSqlParameterSource("id", 1L),
                cartRowMapper
        );
        //when
        cartDao.deleteBy(targetCart.getId());
        //then
        List<Cart> findCarts = namedParameterJdbcTemplate.query(
                "SELECT * FROM carts",
                cartRowMapper
        );
        assertThat(findCarts).doesNotContain(targetCart);
    }
}
