package cart.domain.cartitem;

import static cart.fixture.CartItemFixture.CART_ITEM_MEMBER1_PRODUCT1;
import static cart.fixture.MemberFixture.MEMBER1;
import static cart.fixture.MemberFixture.MEMBER2;
import static cart.fixture.ProductFixture.PRODUCT1;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import cart.domain.product.ProductService;
import cart.infratstructure.MemberForbiddenException;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CartItemServiceTest {

    @Mock
    private CartItemDao cartItemDao;
    @Mock
    private ProductService productService;

    private CartItemService cartItemService;

    @BeforeEach
    void setUp() {
        cartItemService = new CartItemService(cartItemDao, productService);
    }

    @DisplayName("장바구니 아이템 등록 시 DB에 정보를 저장한다")
    @Test
    void add() {
        // given
        // when
        cartItemService.add(CART_ITEM_MEMBER1_PRODUCT1);

        // then
        verify(cartItemDao).insert(CART_ITEM_MEMBER1_PRODUCT1);
    }

    @DisplayName("장바구니 아이템 등록 시 중복 정보가 있으면 예외를 던진다")
    @Test
    void addDuplicatedFail() {
        // given
        when(cartItemDao.isDuplicated(MEMBER1.getId(), PRODUCT1.getId())).thenReturn(true);

        // when
        // then
        assertThatThrownBy(() -> cartItemService.add(CART_ITEM_MEMBER1_PRODUCT1))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("장바구니 아이템 등록 시 상품 정보가 존재하지 않으면 예외를 던진다")
    @Test
    void addNotExistingProductFail() {
        // given
        doThrow(IllegalArgumentException.class)
                .when(productService)
                .validateIdExist(PRODUCT1.getId());

        // when
        // then
        assertThatThrownBy(() -> cartItemService.add(CART_ITEM_MEMBER1_PRODUCT1))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("장바구니 아이템 삭제 시 DB에 해당하는 아이디의 정보를 삭제한다")
    @Test
    void delete() {
        // given
        when(cartItemDao.findById(CART_ITEM_MEMBER1_PRODUCT1.getId())).thenReturn(
                Optional.of(CART_ITEM_MEMBER1_PRODUCT1));

        // when
        cartItemService.deleteById(MEMBER1.getId(), CART_ITEM_MEMBER1_PRODUCT1.getId());

        // then
        verify(cartItemDao).deleteById(CART_ITEM_MEMBER1_PRODUCT1.getId());
    }

    @DisplayName("장바구니 아이템 삭제 시 DB에 해당하는 아이디가 없으면 예외를 던진다")
    @Test
    void deleteNotExistingIdFail() {
        // given
        when(cartItemDao.findById(CART_ITEM_MEMBER1_PRODUCT1.getId())).thenReturn(Optional.empty());

        // when
        // then
        assertThatThrownBy(() -> cartItemService.deleteById(MEMBER1.getId(), CART_ITEM_MEMBER1_PRODUCT1.getId()))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("장바구니 아이템 삭제 시 사용자 아이디가 일치하지 않으면 예외를 던진다")
    @Test
    void deleteNotMatchingMemberId() {
        // given
        when(cartItemDao.findById(CART_ITEM_MEMBER1_PRODUCT1.getId())).thenReturn(
                Optional.of(CART_ITEM_MEMBER1_PRODUCT1));

        // when
        // then
        assertThatThrownBy(() -> cartItemService.deleteById(MEMBER2.getId(), CART_ITEM_MEMBER1_PRODUCT1.getId()))
                .isInstanceOf(MemberForbiddenException.class);
    }
}
