package cart.domain.cartitem;

import static cart.fixture.CartItemFixture.CART_ITEM_MEMBER1_PRODUCT1;
import static cart.fixture.MemberFixture.MEMBER1;
import static cart.fixture.ProductFixture.PRODUCT1;
import static cart.fixture.ProductFixture.PRODUCT2;
import static org.assertj.core.api.Assertions.assertThat;

import cart.dto.CartItemDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

@JdbcTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class H2CartItemDaoTest {

    private final CartItemDao cartItemDao;

    @Autowired
    public H2CartItemDaoTest(final JdbcTemplate jdbcTemplate) {
        this.cartItemDao = new H2CartItemDao(jdbcTemplate);
    }


    @DisplayName("사용자 아이디와 상품 아이디를 전달받아 cartitem을 저장한다")
    @Test
    void insert() {
        // given
        CartItem inserted = cartItemDao.insert(CART_ITEM_MEMBER1_PRODUCT1);

        // when
        // then
        assertThat(cartItemDao.findById(inserted.getId()).get())
                .isEqualTo(inserted);
    }

    @DisplayName("해당 사용자 아이디의 cartitem을 모두 조회한다")
    @Test
    void findByMemberId() {
        // given
        CartItem inserted1 = cartItemDao.insert(CART_ITEM_MEMBER1_PRODUCT1);
        CartItem inserted2 = cartItemDao.insert(new CartItem(MEMBER1.getId(), PRODUCT2.getId()));

        // when
        // then
        assertThat(cartItemDao.findByMemberId(MEMBER1.getId()))
                .containsOnly(
                        new CartItemDto(inserted1.getId(), PRODUCT1.getName(), PRODUCT1.getImageUrl(),
                                PRODUCT1.getPrice()),
                        new CartItemDto(inserted2.getId(), PRODUCT2.getName(), PRODUCT2.getImageUrl(),
                                PRODUCT2.getPrice())
                );
    }

    @DisplayName("해당 사용자 아이디의 cartItem을 삭제한다")
    @Test
    void deleteById() {
        // given
        CartItem inserted = cartItemDao.insert(CART_ITEM_MEMBER1_PRODUCT1);

        // when
        cartItemDao.deleteById(inserted.getId());

        // then
        assertThat(cartItemDao.findById(inserted.getId()).isEmpty())
                .isTrue();
    }
}
