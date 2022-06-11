package woowacourse.shoppingcart.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestConstructor;
import woowacourse.member.dao.MemberDao;
import woowacourse.member.domain.Member;
import woowacourse.shoppingcart.ShoppingCartTest;
import woowacourse.shoppingcart.domain.CartItem;

@JdbcTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class CartItemDaoTest extends ShoppingCartTest {

    private final CartItemDao cartItemDao;
    private final MemberDao memberDao;

    public CartItemDaoTest(JdbcTemplate jdbcTemplate) {
        cartItemDao = new CartItemDao(jdbcTemplate);
        memberDao = new MemberDao(jdbcTemplate);
    }

    @BeforeEach
    void setUp() {
        Member member = new Member("abc@woowacourse.com", "1q2w3e4r!", "닉네임");
        memberDao.save(member);
    }

    @DisplayName("장바구니에 상품을 추가한다.")
    @Test
    void addCartItem() {
        assertThatCode(() -> cartItemDao.addCartItem(1L, 1L, 5))
                .doesNotThrowAnyException();
    }

    @DisplayName("이미 회원의 장바구니에 담긴 상품인지 반환한다.")
    @ParameterizedTest
    @CsvSource({"1, 1, true", "2, 1, false", "1, 2, false", "2, 2, false"})
    void isAlreadyInCart(long memberId, long productId, boolean expected) {
        cartItemDao.addCartItem(1L, 1L, 5);

        boolean actual = cartItemDao.isAlreadyInCart(memberId, productId);

        assertThat(actual).isEqualTo(expected);
    }

    @DisplayName("장바구니에 담긴 모든 상품과 수량을 반환한다.")
    @Test
    void findAllByMemberId() {
        cartItemDao.addCartItem(1L, 1L, 5);
        cartItemDao.addCartItem(1L, 2L, 8);
        cartItemDao.addCartItem(1L, 3L, 10);
        CartItem first = new CartItem(1L, 1L, "캠핑 의자", 35000, 100,
                "https://thawing-fortress-83192.herokuapp.com/static/images/camping-chair.jpg", 5);
        CartItem second = new CartItem(2L, 2L, "그릴", 100000, 100,
                "https://thawing-fortress-83192.herokuapp.com/static/images/grill.jpg", 8);
        CartItem third = new CartItem(3L, 3L, "아이스박스", 20000, 100,
                "https://thawing-fortress-83192.herokuapp.com/static/images/icebox.jpg", 10);

        List<CartItem> actual = cartItemDao.findAll(1L);

        assertThat(actual).isEqualTo(List.of(first, second, third));
    }

    @DisplayName("장바구니에 담긴 상품의 수량을 변경한다.")
    @Test
    void updateCartItemQuantity() {
        cartItemDao.addCartItem(1L, 1L, 5);

        cartItemDao.updateCartItemQuantity(1L, 1L, 6);
        CartItem item = cartItemDao.findAll(1L)
                .get(0);

        assertThat(item.getQuantity()).isEqualTo(6);
    }

    @DisplayName("장바구니에 담긴 상품을 삭제한다.")
    @Test
    void deleteCartItem() {
        cartItemDao.addCartItem(1L, 1L, 5);

        cartItemDao.deleteCartItem(1L, 1L);
        List<CartItem> cartItems = cartItemDao.findAll(1L);

        assertThat(cartItems).isEmpty();
    }
}
