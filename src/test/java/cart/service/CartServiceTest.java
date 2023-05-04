package cart.service;

import cart.auth.MemberInfo;
import cart.dto.request.ProductRequestDto;
import cart.excpetion.CartException;
import cart.repository.CartDao;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CartServiceTest {

    private static final ProductRequestDto PRODUCT_DTO = new ProductRequestDto(1);
    private static final MemberInfo MEMBER_INFO = new MemberInfo(1, "email");
    @InjectMocks
    private CartService cartService;

    @Mock
    private CartDao cartDao;

    @DisplayName("만약 이미 장바구니에 해당 아이템이 등록되어 있다면 예외가 발생한다")
    @Test
    void addProduct_invalid_exitingCartItem() {
        //given
        given(cartDao.existingCartItem(anyInt(), anyInt()))
                .willReturn(true);

        //when,then
        assertThatThrownBy(() -> cartService.addProduct(MEMBER_INFO, PRODUCT_DTO))
                .isInstanceOf(CartException.class);
    }

    @DisplayName("만약 존재하지 않는 MemberId 혹은 ProductId 를 넣는다면 예외를 발생시킨다")
    @Test
    void addProduct_invalid_nonexistenceRequest() {
        //given
        doThrow(new DataIntegrityViolationException("")).when(cartDao).addProduct(anyInt(), anyInt());

        //when,then
        assertThatThrownBy(() -> cartService.addProduct(MEMBER_INFO, PRODUCT_DTO))
                .isInstanceOf(CartException.class);
    }

    @DisplayName("정상 요청 시 카트에 새로운 아이템을 넣는다")
    @Test
    void addProduct() {
        //given,
        given(cartDao.existingCartItem(anyInt(), anyInt()))
                .willReturn(false);

        //when
        cartService.addProduct(MEMBER_INFO, PRODUCT_DTO);

        //then
        verify(cartDao, times(1)).existingCartItem(anyInt(), anyInt());
        verify(cartDao, times(1)).addProduct(anyInt(), anyInt());
    }

    @DisplayName("카드에 등록되어 있는 정보에 대한 삭제 요청이 아니라면 예외가 발생한다")
    @Test
    void deleteProduct_invalid_nonexistenceCartData() {
        //given,
        given(cartDao.existingCartItem(anyInt(), anyInt()))
                .willReturn(false);

        //when,then
        assertThatThrownBy(() -> cartService.deleteProduct(MEMBER_INFO, 1))
                .isInstanceOf(CartException.class);
    }

    @DisplayName("만약 해당 유저의 cart 항목이 있따면 해당 Cart 아이템을 삭제한다")
    @Test
    void deleteProduct() {
        //given
        given(cartDao.existingCartItem(anyInt(), anyInt()))
                .willReturn(true);

        //when
        cartService.deleteProduct(MEMBER_INFO, 1);

        //then
        verify(cartDao, times(1)).existingCartItem(anyInt(), anyInt());
        verify(cartDao, times(1)).deleteProduct(anyInt(), anyInt());
    }
}
