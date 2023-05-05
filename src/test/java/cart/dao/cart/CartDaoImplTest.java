package cart.dao.cart;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import cart.dto.cartitem.CartItem;
import cart.entity.cart.Cart;
import cart.entity.cart.Count;
import cart.entity.member.Email;
import cart.entity.member.Member;
import cart.entity.member.Password;
import cart.entity.product.ImageUrl;
import cart.entity.product.Name;
import cart.entity.product.Price;
import cart.entity.product.Product;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

@JdbcTest
class CartDaoImplTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private CartDao cartDao;

    @BeforeEach
    void setting() {
        cartDao = new CartDaoImpl(jdbcTemplate);
    }

    @Test
    @DisplayName("장바구니에 상품을 넣는다.")
    void insert_cart() {
        // given
        Product product = new Product(3L, new Name("볼펜"), new ImageUrl("이미지"), new Price(1500));
        Member member = new Member(1L, new Email("ako@wooteco.com"), new Password("ako"));
        Cart cart = new Cart(3L, member.getId(), product.getId(), new Count(1));

        // when
        Long insertedCart = cartDao.insertCart(cart);
        Optional<Cart> result = cartDao.findByMemberIdAndProductId(member, product.getId());

        // then
        assertThat(result.get().getMemberId()).isEqualTo(cart.getMemberId());
        assertThat(result.get().getProductId()).isEqualTo(cart.getProductId());
        assertThat(result.get().getCount()).isEqualTo(cart.getCount());
    }

    @Test
    @DisplayName("사용자와 상품의 아이드를 통해 장바구니 조회한다.")
    void find_cart_by_member_and_product() {
        // given
        Product product = new Product(1L, new Name("연필"), new ImageUrl("이미지"), new Price(1000));
        Member member = new Member(1L, new Email("ako@wooteco.com"), new Password("ako"));
        Cart cart = new Cart(1L, member.getId(), product.getId(), new Count(2));

        // when
        Optional<Cart> result = cartDao.findByMemberIdAndProductId(member, product.getId());

        // then
        assertThat(result.get().getMemberId()).isEqualTo(cart.getMemberId());
        assertThat(result.get().getProductId()).isEqualTo(cart.getProductId());
        assertThat(result.get().getCount()).isEqualTo(cart.getCount());

    }

    @Test
    @DisplayName("각 사용자의 장바구니 상품을 가져온다.")
    void find_cart_by_member() {
        // given
        Member member = new Member(1L, new Email("ako@wooteco.com"), new Password("ako"));

        // when
        List<CartItem> result = cartDao.findByMemberId(member);

        // then
        assertThat(result.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("장바구니의 데이터를 업데이트한다.")
    void update_cart_count() {
        // given
        Product product = new Product(1L, new Name("연필"), new ImageUrl("이미지"), new Price(1000));
        Member member = new Member(1L, new Email("ako@wooteco.com"), new Password("ako"));
        Cart cart = new Cart(1L, member.getId(), product.getId(), new Count(1));
        Cart updateCart = new Cart(cart.getId(), cart.getMemberId(), cart.getProductId(), new Count(cart.getCount() + 10));

        // when
        cartDao.updateCart(updateCart);
        Optional<Cart> result = cartDao.findByMemberIdAndProductId(member, product.getId());

        // then
        assertThat(result.get().getMemberId()).isEqualTo(updateCart.getMemberId());
        assertThat(result.get().getProductId()).isEqualTo(updateCart.getProductId());
        assertThat(result.get().getCount()).isEqualTo(updateCart.getCount());
    }

    @Test
    @DisplayName("사용자의 장바구니 상품을 제거한다.")
    void delete_cart_item() {
        // given
        Product product = new Product(1L, new Name("연필"), new ImageUrl("이미지"), new Price(1000));
        Member member = new Member(1L, new Email("ako@wooteco.com"), new Password("ako"));
        Cart cart = new Cart(1L, member.getId(), product.getId(), new Count(1));

        // when
        cartDao.deleteCart(member, product.getId());
        List<CartItem> result = cartDao.findByMemberId(member);

        // then
        assertThat(result.size()).isEqualTo(1);
    }

}