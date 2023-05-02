package cart.persistence.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

import cart.persistence.entity.CartEntity;
import cart.persistence.entity.MemberCartEntity;
import cart.persistence.entity.MemberEntity;
import cart.persistence.entity.ProductEntity;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;

@JdbcTest
@Import({CartDao.class, MemberDao.class, ProductDao.class})
class CartDaoTest {

    private MemberEntity memberEntity;
    private ProductEntity productEntity;

    @Autowired
    private CartDao cartDao;

    @Autowired
    private MemberDao memberDao;

    @Autowired
    private ProductDao productDao;

    @BeforeEach
    void setUp() {
        memberEntity = new MemberEntity("journey@gmail.com", "USER", "password", "져니",
            "010-1234-5678");
        productEntity = new ProductEntity("치킨", "chicken_image_url", 20000, "KOREAN");
    }

    @Test
    @DisplayName("장바구니 정보를 저장한다.")
    void insert() {
        // given
        final long savedMemberId = memberDao.insert(memberEntity);
        final long savedProductId = productDao.insert(productEntity);
        final CartEntity cartEntity = new CartEntity(savedMemberId, savedProductId);

        // when
        final Long savedCartId = cartDao.insert(cartEntity);

        // then
        final Optional<CartEntity> cart = cartDao.findById(savedCartId);
        final CartEntity findCart = cart.get();
        assertThat(findCart)
            .extracting("id", "memberId", "productId")
            .containsExactly(savedCartId, savedMemberId, savedProductId);
    }

    @Test
    @DisplayName("유효한 장바구니 아이디가 주어지면, 장바구니 정보를 조회한다.")
    void findById_success() {
        // given
        final long savedMemberId = memberDao.insert(memberEntity);
        final long savedProductId = productDao.insert(productEntity);
        final CartEntity cartEntity = new CartEntity(savedMemberId, savedProductId);
        final Long savedCartId = cartDao.insert(cartEntity);

        // when
        final Optional<CartEntity> cart = cartDao.findById(savedCartId);

        // then
        final CartEntity findCart = cart.get();
        assertThat(findCart)
            .extracting("id", "memberId", "productId")
            .containsExactly(savedCartId, savedMemberId, savedProductId);
    }

    @Test
    @DisplayName("유효하지 않은 장바구니 아이디가 주어지면, 빈 값을 반환한다.")
    void findById_empty() {
        // when
        final Optional<CartEntity> cart = cartDao.findById(1L);

        // then
        assertThat(cart).isEmpty();
    }

    @Test
    @DisplayName("특정 사용자의 상품 리스트를 반환한다.")
    void getProductByMemberId() {
        // given
        final long savedMemberId = memberDao.insert(memberEntity);
        final long savedChickenId = productDao.insert(productEntity);
        final long savedSteakId = productDao.insert(
            new ProductEntity("스테이크", "steak_image_url", 40000, "WESTERN"));

        final CartEntity chickenEntity = new CartEntity(savedMemberId, savedChickenId);
        final CartEntity steakEntity = new CartEntity(savedMemberId, savedSteakId);
        cartDao.insert(chickenEntity);
        cartDao.insert(steakEntity);

        // when
        final List<MemberCartEntity> memberProductEntities = cartDao.getProductsByMemberId(
            savedMemberId);

        // then
        assertThat(memberProductEntities).hasSize(2);
        assertThat(memberProductEntities)
            .extracting("memberId", "productId", "productName", "productImageUrl", "productPrice",
                "productCategory")
            .containsExactly(
                tuple(savedMemberId, savedChickenId, "치킨", "chicken_image_url", 20000, "KOREAN"),
                tuple(savedMemberId, savedSteakId, "스테이크", "steak_image_url", 40000, "WESTERN"));
    }

    @Test
    @DisplayName("주어진 사용자 아이디와 상품 아이디에 해당하는 장바구니 정보를 삭제한다.")
    void deleteByMemberId() {
        // given
        final long savedMemberId = memberDao.insert(memberEntity);
        final long savedChickenId = productDao.insert(productEntity);

        final CartEntity chickenEntity = new CartEntity(savedMemberId, savedChickenId);
        cartDao.insert(chickenEntity);

        // when
        final int deletedCount = cartDao.deleteByMemberId(savedMemberId, savedChickenId);

        // then
        assertThat(deletedCount).isSameAs(1);
    }
}
