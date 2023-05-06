package cart.cart.dao;

import cart.cart.entity.Cart;
import cart.member.entity.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CartDaoTest {

    private static final String EMAIL = "rg970604@naver.com";
    private static final String PASSWORD = "password";
    private static final String CHICKEN_IMAGE = "https://nenechicken.com/17_new/images/menu/30005.jpg";
    private static final Long CHICKEN_PRICE = 18000L;

    private CartDao cartDao;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    public void setCartDaoTest() {
        cartDao = new CartDao(jdbcTemplate);
    }

    @Test
    void 장바구니_번호로_특정_상품_조회() {
        Cart cart = cartDao.findCartByCartId(1L);
        assertThat(cart.getId()).isSameAs(1L);
        assertThat(cart.getProduct().getName()).isEqualTo("치킨");
        assertThat(cart.getProduct().getImage()).isEqualTo(CHICKEN_IMAGE);
        assertThat(cart.getProduct().getPrice()).isEqualTo(CHICKEN_PRICE);

        Member member = new Member(1L, EMAIL, PASSWORD);
        assertThat(cart.getMember()).isEqualTo(member);
    }

    @Test
    void 해당_사용자의_장바구니_품목_전체조회() {
        List<Cart> carts = cartDao.findCartsByMemberId(1L);
        assertThat(carts.size()).isSameAs(2);
    }

    @Test
    void 장바구니_품목_추가() {
        cartDao.insertCart(1L, 1L);
        List<Cart> carts = cartDao.findCartsByMemberId(1L);
        assertThat(carts.size()).isSameAs(3);
    }

    @Test
    void 장바구니_번호로_장바구니_품목_삭제() {
        cartDao.deleteCartByCartId(1L);
        List<Cart> carts = cartDao.findCartsByMemberId(1L);
        assertThat(carts.size()).isSameAs(1);
    }

}