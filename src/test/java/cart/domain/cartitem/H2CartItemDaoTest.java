package cart.domain.cartitem;

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

    private static final Long FIXTURE_MEMBER_ID_1 = 1L;
    private static final Long FIXTURE_PRODUCT_ID_1 = 1L;
    private static final CartItem FIXTURE_INSERTED_CART_ITEM = new CartItem(FIXTURE_MEMBER_ID_1, FIXTURE_PRODUCT_ID_1);
    private static final Long FIXTURE_PRODUCT_ID_2 = 2L;

    private final CartItemDao cartItemDao;

    @Autowired
    public H2CartItemDaoTest(final JdbcTemplate jdbcTemplate) {
        this.cartItemDao = new H2CartItemDao(jdbcTemplate);
    }


    @DisplayName("사용자 아이디와 상품 아이디를 전달받아 cartitem을 저장한다")
    @Test
    void insert() {
        // given
        CartItem inserted = cartItemDao.insert(FIXTURE_INSERTED_CART_ITEM);

        // when
        // then
        assertThat(cartItemDao.findById(inserted.getId()).get())
                .isEqualTo(inserted);
    }

    @DisplayName("해당 사용자 아이디의 cartitem을 모두 조회한다")
    @Test
    void findByMemberId() {
        // given
        CartItem inserted1 = cartItemDao.insert(FIXTURE_INSERTED_CART_ITEM);
        CartItem inserted2 = cartItemDao.insert(new CartItem(FIXTURE_MEMBER_ID_1, FIXTURE_PRODUCT_ID_2));

        // TODO 초기화 더미 데이터 상수화 시켜서 연동?
        // when
        // then
        assertThat(cartItemDao.findByMemberId(FIXTURE_MEMBER_ID_1))
                .containsOnly(
                        new CartItemDto(inserted1.getId(), "dummy", "https://ifh.cc/g/bQpAgl.jpg", 10000),
                        new CartItemDto(inserted2.getId(), "dummy2", "https://ifh.cc/g/WldLmN.jpg", 20000)
                );
    }

    @DisplayName("해당 사용자 아이디의 cartItem을 삭제한다")
    @Test
    void deleteById() {
        // given
        CartItem inserted = cartItemDao.insert(FIXTURE_INSERTED_CART_ITEM);

        // when
        cartItemDao.deleteById(inserted.getId());

        // then
        assertThat(cartItemDao.findById(inserted.getId()).isEmpty())
                .isTrue();
    }
}
