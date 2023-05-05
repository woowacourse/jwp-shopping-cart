package cart.domain.cartitem;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import cart.domain.product.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CartItemServiceTest {

    private static final Long FIXTURE_MEMBER_ID = 1L;
    private static final Long FIXTURE_PRODUCT_ID = 1L;
    private static final Long FIXTURE_CART_ITEM_ID = 1L;
    private static final CartItem FIXTURE_INSERT_CART_ITEM = new CartItem(FIXTURE_MEMBER_ID, FIXTURE_PRODUCT_ID);

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
        cartItemService.add(FIXTURE_INSERT_CART_ITEM);

        // then
        verify(cartItemDao).insert(FIXTURE_INSERT_CART_ITEM);
    }

    @DisplayName("장바구니 아이템 등록 시 중복 정보가 있으면 예외를 던진다")
    @Test
    void addDuplicatedFail() {
        // given
        when(cartItemDao.isDuplicated(FIXTURE_MEMBER_ID, FIXTURE_PRODUCT_ID)).thenReturn(true);

        // when
        // then
        assertThatThrownBy(() -> cartItemService.add(FIXTURE_INSERT_CART_ITEM))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("장바구니 아이템 등록 시 상품 정보가 존재하지 않으면 예외를 던진다")
    @Test
    void addNotExistingProductFail() {
        // given
        doThrow(IllegalArgumentException.class)
                .when(productService)
                .validateIdExist(FIXTURE_PRODUCT_ID);

        // when
        // then
        assertThatThrownBy(() -> cartItemService.add(FIXTURE_INSERT_CART_ITEM))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("장바구니 아이템 삭제 시 DB에 해당하는 아이디의 정보를 삭제한다")
    @Test
    void delete() {
        // given
        when(cartItemDao.isExist(FIXTURE_CART_ITEM_ID)).thenReturn(true);

        // when
        cartItemService.deleteById(FIXTURE_CART_ITEM_ID);

        // then
        verify(cartItemDao).deleteById(FIXTURE_CART_ITEM_ID);
    }

    @DisplayName("장바구니 아이템 삭제 시 DB에 해당하는 아이디가 없으면 예외를 던진다")
    @Test
    void deleteNotExistingIdFail() {
        // given
        when(cartItemDao.isExist(FIXTURE_CART_ITEM_ID)).thenReturn(false);

        // when
        // then
        assertThatThrownBy(() -> cartItemService.deleteById(FIXTURE_CART_ITEM_ID))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
