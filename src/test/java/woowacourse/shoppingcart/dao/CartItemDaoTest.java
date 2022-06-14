package woowacourse.shoppingcart.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.shoppingcart.domain.Cart;

import javax.sql.DataSource;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Sql(scripts = {"classpath:schema.sql", "classpath:data.sql"})
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
public class CartItemDaoTest {
    private final CartItemDao cartItemDao;
    private final JdbcTemplate jdbcTemplate;

    public CartItemDaoTest(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        cartItemDao = new CartItemDao(dataSource);
    }

    @DisplayName("사용자별 장바구니 목록을 조회한다.")
    @Test
    void findProductIdsByMemberId() {
        List<Cart> carts = cartItemDao.findCartsByMemberId(1L);

        assertThat(carts.size()).isEqualTo(2);
    }

    @DisplayName("해당 사용자가 이미 그 상품을 장바구니에 담았다면 장바구니 id를 반환한다.")
    @Test
    void findIdExistByMemberProductId() {
        assertThat(cartItemDao.findIdIfExistByMemberProductId(1L, 1L)).isEqualTo(1L);
    }

    @DisplayName("해당 사용자가 그 상품을 장바구니에 담은적 없다면 장바구니 0을 반환한다.")
    @Test
    void findIdNotExistByMemberProductId() {
        assertThat(cartItemDao.findIdIfExistByMemberProductId(1L, 5L)).isEqualTo(0L);
    }

    @DisplayName("현재수량에서 하나 더한다.")
    @Test
    void plusQuantityById() {
        int originalQuantity = jdbcTemplate.queryForObject("SELECT quantity FROM cart_item WHERE id = 1", Integer.class);

        cartItemDao.plusQuantityById(1L);

        int resultQuantity = jdbcTemplate.queryForObject("SELECT quantity FROM cart_item WHERE id = 1", Integer.class);

        assertThat(resultQuantity).isEqualTo(originalQuantity + 1);
    }

    @DisplayName("장바구니에 새로운 상품을 추가한다.")
    @Test
    void add() {
        cartItemDao.add(2L, 1L, 1);

        boolean result = jdbcTemplate.queryForObject("SELECT EXISTS (SELECT * FROM cart_item WHERE member_id = 2 AND product_id = 1)", Boolean.class);
        assertThat(result).isTrue();
    }

    @DisplayName("장바구니 상품의 수량을 수정한다.")
    @Test
    void update() {
        cartItemDao.update(1L, 5);

        int result = jdbcTemplate.queryForObject("SELECT quantity FROM cart_item WHERE id = 1", Integer.class);
        assertThat(result).isEqualTo(5);
    }

    @DisplayName("장바구니를 삭제한다.")
    @Test
    void delete() {
        cartItemDao.delete(1L);

        boolean result = jdbcTemplate.queryForObject("SELECT EXISTS (SELECT * FROM cart_item WHERE id = 1)", Boolean.class);
        assertThat(result).isFalse();
    }

}
