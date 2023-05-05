package cart.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import cart.dao.CartDao;
import cart.entity.cart.CartEntity;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CartServiceTest {

    @Mock
    private CartDao cartDao;

    @InjectMocks
    private CartService cartService;


    @DisplayName("상품과 고객의 Id를 전달하여 DAO에서 데이터를 생성한다.")
    @Test
    void add() {
        //given
        final CartEntity cartEntity = new CartEntity(1L, 1L);
        final ArgumentCaptor<CartEntity> cartEntityArgumentCaptor = ArgumentCaptor.forClass(CartEntity.class);
        when(cartDao.save(any()))
            .thenReturn(1L);

        //when
        final Long savedId = cartService.save(cartEntity);

        //then
        verify(cartDao).save(cartEntityArgumentCaptor.capture());
        final CartEntity captorValue = cartEntityArgumentCaptor.getValue();

        assertAll(
            () -> assertThat(savedId).isEqualTo(1L),
            () -> assertThat(captorValue.getCustomerId()).isEqualTo(1L),
            () -> assertThat(captorValue.getProductId()).isEqualTo(1L)
        );
    }

    @DisplayName("카트의 Id를 전달하여 DAO에서 데이터를 삭제한다.")
    @Test
    void delete() {
        //given
        final ArgumentCaptor<Long> longArgumentCaptor = ArgumentCaptor.forClass(Long.class);
        doNothing().when(cartDao).delete(any());

        //when
        cartService.delete(1L);

        //then
        verify(cartDao).delete(longArgumentCaptor.capture());
        assertThat(longArgumentCaptor.getValue()).isEqualTo(1L);
    }

    @DisplayName("상품과 고객의 Id를 전달하여 DAO에서 데이터를 삭제한다.")
    @Test
    void deleteByCustomerIdAndProductId() {
        //given
        final ArgumentCaptor<Long> longArgumentCaptor = ArgumentCaptor.forClass(Long.class);
        doNothing()
            .when(cartDao).deleteByCustomerIdAndProductId(any(), any());

        //when
        cartService.deleteByCustomerIdAndProductId(1L, 1L);

        //then
        verify(cartDao).deleteByCustomerIdAndProductId(longArgumentCaptor.capture(), longArgumentCaptor.capture());
        final List<Long> captorAllValues = longArgumentCaptor.getAllValues();
        assertThat(captorAllValues).containsExactly(1L, 1L);
    }
}
