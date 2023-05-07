package cart.service;

import cart.controller.dto.response.CartItemResponse;
import cart.database.dao.CartDao;
import cart.database.dao.ProductDao;
import cart.database.repository.CartRepository;
import cart.entity.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CartServiceTest {

    private CartService cartService;

    @Mock
    private ProductDao productDao;

    @Mock
    private CartDao cartDao;

    @Mock
    private CartRepository cartRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        cartService = new CartService(productDao, cartDao, cartRepository);
    }

    @DisplayName("장바구니에 아이템 추가")
    @Test
    public void addCart() {
        // given
        UserEntity userEntity = new UserEntity(1L, "user@test.com", "password");
        Long productId = 1L;
        Integer count = 1;

        when(productDao.existById(productId)).thenReturn(true);

        // when
        cartService.addCart(userEntity, productId);

        // then
        verify(cartDao, times(1)).create(userEntity.getId(), productId, count);
    }

    @DisplayName("ProductId가 유효하지 않은 경우 카트아이템 추가를 실패")
    @Test
    public void addCartIfNotExistProductId() {
        // given
        UserEntity userEntity = new UserEntity(1L, "user@test.com", "password");
        Long productId = 1L;
        Integer count = 1;

        when(productDao.existById(productId)).thenReturn(false);

        // when, then
        IllegalStateException exception = assertThrows(IllegalStateException.class,
                () -> cartService.addCart(userEntity, productId));
        assertEquals("제품 아이디가 없습니다.", exception.getMessage());

        verify(cartDao, never()).create(userEntity.getId(), productId, count);
    }

    @DisplayName("유저가 가진 장바구니 아이템 조회")
    @Test
    public void findCartItemsByUser() {
        // given
        UserEntity userEntity = new UserEntity(1L, "user@test.com", "password");

        List<CartItemResponse> expectedList = new ArrayList<>();
        expectedList.add(new CartItemResponse(1L, 1L, "Product 1", "test", 1000, 2));
        expectedList.add(new CartItemResponse(2L, 2L, "Product 2", "test", 2000, 1));

        when(cartRepository.findCartsWithProductByUserId(userEntity.getId())).thenReturn(expectedList);

        // when
        List<CartItemResponse> responses = cartService.findCartItemsByUser(userEntity);

        // then
        assertEquals(expectedList.size(), responses.size());
        for (int i = 0; i < expectedList.size(); i++) {
            assertEquals(expectedList.get(i).getCartId(), responses.get(i).getCartId());
            assertEquals(expectedList.get(i).getProductId(), responses.get(i).getProductId());
            assertEquals(expectedList.get(i).getName(), responses.get(i).getName());
            assertEquals(expectedList.get(i).getImageUrl(), responses.get(i).getImageUrl());
            assertEquals(expectedList.get(i).getPrice(), responses.get(i).getPrice());
            assertEquals(expectedList.get(i).getCount(), responses.get(i).getCount());
        }
    }

    @DisplayName("유저가 가진 장바구니에서 특정 아이템 제거")
    @Test
    public void deleteCartItemByUserAndProductId() {
        // given
        UserEntity userEntity = new UserEntity(1L, "user@test.com", "password");
        Long cartId = 1L;

        // when
        cartService.deleteCartByUserAndProductId(userEntity, cartId);

        // then
        verify(cartDao, times(1)).deleteByUserIdAndCartId(userEntity.getId(), cartId);
    }
}
