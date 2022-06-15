package woowacourse.shoppingcart.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static woowacourse.helper.fixture.MemberFixture.EMAIL;
import static woowacourse.helper.fixture.MemberFixture.NAME;
import static woowacourse.helper.fixture.MemberFixture.PASSWORD;
import static woowacourse.helper.fixture.MemberFixture.createMember;
import static woowacourse.helper.fixture.ProductFixture.createProduct;

import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.member.dao.MemberDao;
import woowacourse.shoppingcart.domain.Cart;

@JdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql("classpath:schema.sql")
public class CartItemDaoTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private CartItemDao cartItemDao;
    private ProductDao productDao;
    private MemberDao memberDao;

    @BeforeEach
    void setUp() {
        cartItemDao = new CartItemDao(jdbcTemplate);
        productDao = new ProductDao(jdbcTemplate);
        memberDao = new MemberDao(jdbcTemplate);
    }

    @DisplayName("멤버 아이디로 카트들을 가져온다.")
    @Test
    void findCartsByMemberId() {
        Long memberId = memberDao.save(createMember(EMAIL, PASSWORD, NAME));
        Long productId = productDao.save(createProduct("banana", 1_000, "woowa1.com"));
        Long productId2 = productDao.save(createProduct("apple", 2_000, "woowa2.com"));

        cartItemDao.addCartItem(memberId, productId);
        cartItemDao.addCartItem(memberId, productId2);

        assertThat(
                cartItemDao.findCartsByMemberId(memberId).stream()
                        .map(Cart::getName)
                        .collect(Collectors.toList())
        ).containsExactly("banana", "apple");
    }

    @DisplayName("카드 아이디로 카트를 가져온다.")
    @Test
    void findCartById() {
        Long memberId = memberDao.save(createMember(EMAIL, PASSWORD, NAME));
        Long productId = productDao.save(createProduct("banana", 1_000, "woowa1.com"));

        Long cartId = cartItemDao.addCartItem(memberId, productId);

        assertThat(cartItemDao.findCartById(cartId).getName()).isEqualTo("banana");
    }

    @DisplayName("멤버 아이디를 넣으면, 해당 장바구니 아이디들을 가져온다.")
    @Test
    void findIdsByMemberId() {
        Long memberId = memberDao.save(createMember(EMAIL, PASSWORD, NAME));
        Long productId = productDao.save(createProduct("banana", 1_000, "woowa1.com"));

        cartItemDao.addCartItem(memberId, productId);

        final List<Long> cartIds = cartItemDao.findIdsByMemberId(1L);
        assertThat(cartIds).containsExactly(1L);
    }

    @DisplayName("멤버 아이디와 상품 아이디로 장바구니 아이디를 찾는다.")
    @Test
    void findIdByMemberIdAndProductId() {
        Long memberId = memberDao.save(createMember(EMAIL, PASSWORD, NAME));
        Long productId = productDao.save(createProduct("banana", 1_000, "woowa1.com"));

        Long cartId = cartItemDao.addCartItem(memberId, productId);

        assertThat(cartItemDao.findIdByMemberIdAndProductId(memberId, productId)).isEqualTo(cartId);
    }

    @DisplayName("카트 상품이 이미 존재하는지 확인한다.")
    @Test
    void isValidCartItem() {
        Long memberId = memberDao.save(createMember(EMAIL, PASSWORD, NAME));
        Long productId = productDao.save(createProduct("banana", 1_000, "woowa1.com"));

        cartItemDao.addCartItem(memberId, productId);

        assertThat(cartItemDao.isValidCartItem(memberId, productId)).isTrue();
    }

    @DisplayName("카트에 아이템을 담으면, 담긴 카트 아이디를 반환한다.")
    @Test
    void addCartItem() {
        Long memberId = memberDao.save(createMember(EMAIL, PASSWORD, NAME));
        Long productId = productDao.save(createProduct("banana", 1_000, "woowa1.com"));

        assertThat(cartItemDao.addCartItem(memberId, productId)).isEqualTo(1L);
    }

    @DisplayName("카트 물품 수량을 하나 추가한다.")
    @Test
    void addQuantityCartItem() {
        Long memberId = memberDao.save(createMember(EMAIL, PASSWORD, NAME));
        Long productId = productDao.save(createProduct("banana", 1_000, "woowa1.com"));

        Long cartId = cartItemDao.addCartItem(memberId, productId);
        cartItemDao.addQuantityCartItem(memberId, productId);

        assertThat(cartItemDao.findCartById(cartId).getQuantity()).isEqualTo(2);
    }

    @DisplayName("카트 물품 수량을 업데이트 한다.")
    @Test
    void updateCartItemQuantity() {
        Long memberId = memberDao.save(createMember(EMAIL, PASSWORD, NAME));
        Long productId = productDao.save(createProduct("banana", 1_000, "woowa1.com"));

        Long cartId = cartItemDao.addCartItem(memberId, productId);
        cartItemDao.updateCartItemQuantity(cartId, 5);

        assertThat(cartItemDao.findCartById(cartId).getQuantity()).isEqualTo(5);
    }

    @DisplayName("카트 물품을 삭제한다.")
    @Test
    void deleteCartItem() {
        Long memberId = memberDao.save(createMember(EMAIL, PASSWORD, NAME));
        Long productId = productDao.save(createProduct("banana", 1_000, "woowa1.com"));

        Long cartId = cartItemDao.addCartItem(memberId, productId);
        cartItemDao.deleteCartItem(cartId);

        assertThat(cartItemDao.isValidCartItem(memberId, productId)).isFalse();
    }
}
