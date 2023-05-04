package cart.dao;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import cart.entity.CartEntity;
import cart.entity.ProductEntity;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;

@JdbcTest
@Sql("/scheme.sql")
@Sql("/data.sql")
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
        final String sql = "insert into cart (member_id, product_id) values (1, 1)";
        jdbcTemplate.update(sql);

        //when
        final List<ProductEntity> productsByMemberId = cartDao.findProductsByMemberId(1L);
        final ProductEntity productEntity = productsByMemberId.get(0);

        //then
        assertAll(
                () -> assertThat(productsByMemberId).hasSize(1),
                () -> assertThat(productEntity.getName()).isEqualTo("피자"),
                () -> assertThat(productEntity.getImageUrl()).isEqualTo("url1"),
                () -> assertThat(productEntity.getPrice()).isEqualTo(20000)
        );
    }
}
