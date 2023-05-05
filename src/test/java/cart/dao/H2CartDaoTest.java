package cart.dao;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import cart.controller.dto.CartResponse;
import cart.entity.CartEntity;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;

@JdbcTest
@Sql("/scheme.sql")
@Sql("/cartDataSetUp.sql")
class H2CartDaoTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private CartDao cartDao;

    @BeforeEach
    void setUp() {
        cartDao = new H2CartDao(jdbcTemplate);
    }

    @Test
    void save() {
        //given
        final CartEntity cartEntity = new CartEntity(3L, 13L);
        final String sql = "select * from cart where id =?";

        //when
        final Long id = cartDao.save(cartEntity);
        final CartEntity actual = jdbcTemplate.queryForObject(sql,
                (rs, rowNum) -> new CartEntity(
                        rs.getLong("id"),
                        rs.getLong("member_id"),
                        rs.getLong("product_id")),
                id);
        final Long memberId = actual.getMemberId();
        final Long productId = actual.getProductId();

        //then
        assertAll(
                () -> assertThat(memberId).isEqualTo(3L),
                () -> assertThat(productId).isEqualTo(13L)
        );
    }

    @Test
    void findProductsByMemberId() {
        //given
        final String sql = "insert into cart (id, member_id, product_id) values (50, 60, 20)";
        jdbcTemplate.update(sql);

        //when
        final List<CartResponse> productsByMemberId = cartDao.findProductsByMemberId(60L);
        final CartResponse cartResponse = productsByMemberId.get(0);

        //then
        assertAll(
                () -> assertThat(productsByMemberId).hasSize(1),
                () -> assertThat(cartResponse.getName()).isEqualTo("피자"),
                () -> assertThat(cartResponse.getImageUrl()).isEqualTo("url1"),
                () -> assertThat(cartResponse.getPrice()).isEqualTo(20000)
        );
    }

    @Test
    void deleteById() {
        //given
        final String sql = "insert into cart (id, member_id, product_id) values (5, 10, 20)";
        jdbcTemplate.update(sql);

        //when
        cartDao.deleteById(5L);

        //then
        final String sql2 = "select * from cart where id = 5";
        final List<CartEntity> cartEntities = jdbcTemplate.query(sql2, (rs, rowNum) -> new CartEntity(
                rs.getLong("id"),
                rs.getLong("member_id"),
                rs.getLong("product_id")));
        assertThat(cartEntities).isEmpty();

    }
}
