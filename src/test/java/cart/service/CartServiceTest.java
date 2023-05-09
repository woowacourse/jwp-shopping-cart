package cart.service;

import cart.dto.cart.CartItemDto;
import cart.dto.cart.UserDto;
import cart.entity.CartEntity;
import cart.entity.MemberEntity;
import cart.entity.ProductEntity;
import cart.repository.CartRepository;
import cart.repository.ProductRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CartServiceTest {

    @InjectMocks
    CartService cartService;
    @Mock
    CartRepository cartRepository;
    @Mock
    ProductRepository productRepository;

    @Test
    @DisplayName("장바구니에 상품을 추가한다.")
    void addItemTest_success() {
        long cartId = 1L;
        long userId = 1L;
        long productId = 1L;
        UserDto userDto = UserDto.fromMemberEntity(new MemberEntity(userId, "a@a.com", "1234"));
        CartEntity savedCartEntity = new CartEntity(cartId, userId, productId);
        ProductEntity productEntity = new ProductEntity(productId, "product1", "url1.com", 1000);

        when(cartRepository.save(any(CartEntity.class))).thenReturn(savedCartEntity);
        when(productRepository.findById(anyLong())).thenReturn(Optional.of(productEntity));

        CartItemDto expectDto = CartItemDto.fromCartIdAndProductEntity(cartId, productEntity);
        assertThat(cartService.addItem(userDto, productId)).isEqualTo(expectDto);
    }

    @Test
    @DisplayName("상품 정보를 찾지 못하면 예외가 발생한다.")
    void addItemTest_fail() {
        long userId = 1L;
        long productId = 1L;
        UserDto userDto = UserDto.fromMemberEntity(new MemberEntity(userId, "a@a.com", "1234"));
        when(productRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> cartService.addItem(userDto, productId))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("상품이 존재하지 않습니다.");
    }

    @Test
    @DisplayName("사용자의 카트 정보를 조회한다.")
    void findAllUserItemsTest() {
        long userId = 1L;
        long productId1 = 1L;
        long productId2 = 2L;
        UserDto userDto = UserDto.fromMemberEntity(new MemberEntity(userId, "a@a.com", "1234"));
        CartEntity cartEntity1 = new CartEntity(1L, userId, productId1);
        CartEntity cartEntity2 = new CartEntity(2L, userId, productId2);
        when(cartRepository.findByUserId(anyLong()))
                .thenReturn(List.of(cartEntity1, cartEntity2));

        ProductEntity productEntity1 = new ProductEntity(productId1, "product1", "url1.com", 10);
        ProductEntity productEntity2 = new ProductEntity(productId2, "product2", "url2.com", 20);
        when(productRepository.findById(productId1))
                .thenReturn(Optional.of(productEntity1));
        when(productRepository.findById(productId2))
                .thenReturn(Optional.of(productEntity2));

        CartItemDto cartItemDto1 = CartItemDto.fromCartIdAndProductEntity(cartEntity1.getId(), productEntity1);
        CartItemDto cartItemDto2 = CartItemDto.fromCartIdAndProductEntity(cartEntity2.getId(), productEntity2);

        assertThat(cartService.findAllUserItems(userDto))
                .isEqualTo(List.of(cartItemDto1, cartItemDto2));
    }

    @Test
    @DisplayName("카트 id에 해당하는 카트 정보를 삭제한다.")
    void deleteByIdTest() {
        Long id = 1L;
        doNothing().when(cartRepository).deleteById(any(Long.class));

        assertThatNoException().isThrownBy(() -> cartService.deleteById(id));
    }
}