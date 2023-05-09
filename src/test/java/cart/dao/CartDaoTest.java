package cart.dao;

import cart.domain.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@Sql("classpath:initializeTestDb.sql")
class CartDaoTest {

    private final CartDao cartDao;
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public CartDaoTest(final JdbcTemplate jdbcTemplate) {
        this.cartDao = new CartDao(jdbcTemplate);
        this.jdbcTemplate = jdbcTemplate;
    }

    @DisplayName("Cart 객체를 받아 DB에 저장한다.")
    @Test
    void save() {
        //given
        // TODO: 5/4/23 이건 테스트가 jdbcTemplate을 의존 한건가? jdbcTempalte이 잘 된다고 어떻게 보장하지? 
        List<Long> before = jdbcTemplate.queryForList("SELECT id FROM carts ", Long.class);
        int beforeSize = before.size();
        Cart cart = new Cart(
                new User(1L, new Email("email1@email.com"), new Password("12345678")),
                new Item.Builder().id(3L).name(new Name("빌리 엘리어트")).imageUrl(new ImageUrl("https://t1.daumcdn.net/cfile/226F4D4C544F42CF34")).price(new Price(200000)).build()
        );
        //when
        cartDao.save(cart);
        List<Long> after = jdbcTemplate.queryForList("SELECT id FROM carts ", Long.class);
        //then
        assertThat(after).hasSize(beforeSize + 1);
    }

    @DisplayName("유저의 전체 장바구니를 조회한다.")
    @Test
    void findByUserId() {
        //when
        List<Cart> carts = cartDao.findByUserId(1L);
        //then
        assertThat(carts).hasSize(2);
    }

    @DisplayName("cart_id로 DB에서 제거한다.")
    @Test
    void delete() {
        //given
        List<Long> before = jdbcTemplate.queryForList("SELECT id FROM carts ", Long.class);
        int beforeSize = before.size();
        //when
        cartDao.delete(1L);
        List<Long> after = jdbcTemplate.queryForList("SELECT id FROM carts ", Long.class);
        //then
        assertThat(after).hasSize(beforeSize - 1);
    }
}
