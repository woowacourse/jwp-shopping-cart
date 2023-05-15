package cart.dao;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import cart.domain.Member;
import cart.domain.Product;
import cart.entity.CartItemEntity;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

@JdbcTest
class CartDaoTest {
    public static final Product PRODUCT = Product.builder()
            .name("오감자")
            .price(1_000)
            .imageUrl("imageUrl")
            .build();
    private final String EMAIL = "test@email.com";
    private final String PASSWORD = "12345678";
    private final Member MEMBER = Member.builder()
            .email(EMAIL)
            .password(PASSWORD)
            .build();
    private long cartId;
    private long memberId;
    private long productId;

    @Autowired
    JdbcTemplate jdbcTemplate;

    private MemberDao memberDao;
    private ProductDao productDao;
    private CartDao cartDao;

    @BeforeEach
    void setUp() {
        cartDao = new CartDao(jdbcTemplate);
        memberDao = new MemberDao(jdbcTemplate);
        productDao = new ProductDao(jdbcTemplate);
    }

    @Test
    @DisplayName("장바구니에 상품을 추가한다.")
    void add_success() {
        // given
        memberId = memberDao.save(MEMBER).getId();
        productId = productDao.save(PRODUCT).getId();

        // when
        cartId = cartDao.add(memberId, productId);

        // then
        assertThat(cartDao.findAllByMemberId(memberId)).hasSize(1);
    }

    @Test
    @DisplayName("장바구니에서 상품을 삭제한다.")
    void delete_success() {
        // given
        memberId = memberDao.save(MEMBER).getId();
        productId = productDao.save(PRODUCT).getId();
        cartId = cartDao.add(memberId, productId);

        // when

        cartDao.deleteById(memberId, cartId);

        // then
        assertThat(cartDao.findAllByMemberId(memberId)).hasSize(0);
    }

    @Test
    @DisplayName("특정 사용자의 장바구니에 있는 상품들을 반환한다.")
    void findAllByMemberId_success() {
        // given
        memberId = memberDao.save(MEMBER).getId();
        productId = productDao.save(PRODUCT).getId();
        cartDao.add(memberId, productId);

        // when
        List<CartItemEntity> all = cartDao.findAllByMemberId(memberId);
        CartItemEntity product = all.get(0);

        // then
        assertAll(
                () -> assertThat(all).hasSize(1),
                () -> assertThat(product.getId()).isEqualTo(productId),
                () -> assertThat(product.getName()).isEqualTo("오감자"),
                () -> assertThat(product.getPrice()).isEqualTo(1000)
        );
    }

    @Test
    @DisplayName("cartId 를 통해 특정 상품이 장바구니에 있는지 확인한다.")
    void existsById_success() {
        // given
        memberId = memberDao.save(MEMBER).getId();
        productId = productDao.save(PRODUCT).getId();
        cartId = cartDao.add(memberId, productId);

        // when
        Boolean actual = cartDao.existsById(cartId);

        // then
        assertThat(actual).isTrue();
    }
}
