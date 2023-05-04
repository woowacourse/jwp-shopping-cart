package cart.dao.cart;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import cart.dao.product.ProductDao;
import cart.dao.product.ProductDaoImpl;
import cart.entity.cart.Cart;
import cart.entity.cart.Count;
import cart.entity.member.Email;
import cart.entity.member.Member;
import cart.entity.member.Password;
import cart.entity.product.Product;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
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
    private ProductDao productDao;

    @BeforeEach
    void setting() {
        cartDao = new CartDaoImpl(jdbcTemplate);
        productDao = new ProductDaoImpl(jdbcTemplate);
        jdbcTemplate.execute("INSERT INTO member(email, password) values ('ako@naver.com', 'ako')");
    }

    @AfterEach
    void clear_database() {
        jdbcTemplate.execute("TRUNCATE TABLE cart");
    }

    @Test
    @DisplayName("장바구니에 상품을 넣는다.")
    void insert_cart() {
        // given
        Long productId = productDao.insertProduct(new Product("연필", "이미지", 1000));
        Member member = new Member(1L, new Email("ako@naver.com"), new Password("ako"));
        Cart cart = new Cart(1L, productId, 2);

        // when
        Long insertedCart = cartDao.insertCart(cart);
        Optional<Cart> result = cartDao.findByMemberIdAndProductId(member, productId);

        // then
        assertThat(result.get().getMemberId()).isEqualTo(cart.getMemberId());
        assertThat(result.get().getProductId()).isEqualTo(cart.getProductId());
        assertThat(result.get().getCount()).isEqualTo(cart.getCount());
    }

    @Test
    @DisplayName("사용자와 상품의 아이드를 통해 장바구니 조회한다.")
    void find_cart_by_member_and_product() {
        // given
        Long productId = productDao.insertProduct(new Product("연필", "이미지", 1000));
        Member member = new Member(1L, new Email("ako@naver.com"), new Password("ako"));
        Cart cart = new Cart(1L, productId, 1);
        cartDao.insertCart(cart);

        // when
        Optional<Cart> result = cartDao.findByMemberIdAndProductId(member, productId);

        // then
        assertThat(result.get().getMemberId()).isEqualTo(cart.getMemberId());
        assertThat(result.get().getProductId()).isEqualTo(cart.getProductId());
        assertThat(result.get().getCount()).isEqualTo(cart.getCount());

    }

    @Test
    @DisplayName("장바구니의 데이터를 업데이트한다.")
    void update_cart_count() {
        // given
        Long productId = productDao.insertProduct(new Product("연필", "이미지", 1000));
        Member member = new Member(1L, new Email("ako@naver.com"), new Password("ako"));
        Cart cart = new Cart(1L, productId, 1);
        Long insertCart = cartDao.insertCart(cart);
        Cart updateCart = new Cart(insertCart, cart.getMemberId(), cart.getProductId(), new Count(cart.getCount() + 1));

        // when
        cartDao.updateCart(updateCart);
        Optional<Cart> result = cartDao.findByMemberIdAndProductId(member, productId);

        // then
        assertThat(result.get().getMemberId()).isEqualTo(updateCart.getMemberId());
        assertThat(result.get().getProductId()).isEqualTo(updateCart.getProductId());
        assertThat(result.get().getCount()).isEqualTo(updateCart.getCount());

    }

}