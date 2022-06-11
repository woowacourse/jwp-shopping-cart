package woowacourse.shoppingcart.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestConstructor;
import woowacourse.shoppingcart.domain.CartItem;
import woowacourse.shoppingcart.domain.Member;
import woowacourse.shoppingcart.domain.Product;

@JdbcTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
public class CartItemDaoTest {
    private final CartItemDao cartItemDao;
    private final ProductDao productDao;
    private final MemberDao memberDao;

    public CartItemDaoTest(JdbcTemplate jdbcTemplate) {
        cartItemDao = new CartItemDao(jdbcTemplate);
        productDao = new ProductDao(jdbcTemplate);
        memberDao = new MemberDao(jdbcTemplate);
    }

    @DisplayName("새로운 장바구니 상품을 저장한다.")
    @Test
    void save() {
        Long memberId = memberDao.save(new Member("abc@woowahan.com", "1q2w3e4r!", "우테코"));
        Product product = new Product("banana", 1_000, 10, "woowa1.com");
        productDao.save(product);
        CartItem cartItem = new CartItem(memberId, product, 1);

        Long cartItemId = cartItemDao.save(cartItem);

        assertThat(cartItemId).isNotNull();
    }

    @DisplayName("회원의 장바구니에 저장된 상품 리스트를 조회한다.")
    @Test
    void findByMemberId() {
        Long memberId = memberDao.save(new Member("abc@woowahan.com", "1q2w3e4r!", "우테코"));
        Product product = new Product("banana", 1_000, 10, "woowa1.com");
        productDao.save(product);
        CartItem cartItem = new CartItem(memberId, product, 1);
        Long cartItemId = cartItemDao.save(cartItem);
        Product product2 = new Product("apple", 2_000, 10, "woowa2.com");
        productDao.save(product2);
        CartItem cartItem2 = new CartItem(memberId, product2, 2);
        Long cartItem2Id = cartItemDao.save(cartItem2);

        List<CartItem> cartItems = cartItemDao.findByMemberId(memberId);
        CartItem persist = new CartItem(cartItemId, memberId, product, 1);
        CartItem persist2 = new CartItem(cartItem2Id, memberId, product2, 2);

        assertThat(cartItems).containsOnly(persist, persist2);
    }

    @DisplayName("회원의 장바구니에 저장된 특정 상품을 조회한다.")
    @Test
    void findByMemberIdAndProductId() {
        Long memberId = memberDao.save(new Member("abc@woowahan.com", "1q2w3e4r!", "우테코"));
        Product product = new Product("banana", 1_000, 10, "woowa1.com");
        Long productId = productDao.save(product);
        CartItem cartItem = new CartItem(memberId, product, 1);
        Long cartItemId = cartItemDao.save(cartItem);

        CartItem persist = cartItemDao.findByMemberIdAndProductId(memberId, productId)
                .orElseGet(() -> fail(""));
        CartItem expected = new CartItem(cartItemId, memberId, product, 1);

        assertThat(persist).isEqualTo(expected);
    }

    @DisplayName("회원의 장바구니에 상품이 저장되어 있는지 확인한다.")
    @Test
    void exists() {
        Long memberId = memberDao.save(new Member("abc@woowahan.com", "1q2w3e4r!", "우테코"));
        Product product = new Product("banana", 1_000, 10, "woowa1.com");
        Long productId = productDao.save(product);
        CartItem cartItem = new CartItem(memberId, product, 1);
        cartItemDao.save(cartItem);

        boolean actual = cartItemDao.exists(memberId, productId);

        assertThat(actual).isTrue();
    }

    @DisplayName("장바구니에 저장된 상품 개수를 변경한다.")
    @Test
    void updateQuantity() {
        Long memberId = memberDao.save(new Member("abc@woowahan.com", "1q2w3e4r!", "우테코"));
        Product product = new Product("banana", 1_000, 1, "woowa1.com");
        Long productId = productDao.save(product);
        CartItem cartItem = new CartItem(memberId, product, 1);
        Long cartItemId = cartItemDao.save(cartItem);

        cartItemDao.updateQuantity(cartItemId, productId, 2);
        CartItem persistCartItem = cartItemDao.findByMemberId(memberId)
                .get(0);

        assertThat(persistCartItem.getQuantity()).isEqualTo(2);
    }

    @DisplayName("장바구니에 저장된 상품을 제거한다.")
    @Test
    void delete() {
        Long memberId = memberDao.save(new Member("abc@woowahan.com", "1q2w3e4r!", "우테코"));
        Product product = new Product("banana", 1_000, 1, "woowa1.com");
        Long productId = productDao.save(product);
        CartItem cartItem = new CartItem(memberId, product, 1);
        cartItemDao.save(cartItem);

        cartItemDao.deleteByMemberIdAndProductId(memberId, productId);
        boolean exists = cartItemDao.exists(memberId, productId);

        assertThat(exists).isFalse();
    }
}
