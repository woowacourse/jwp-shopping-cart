package cart.service;

import cart.dao.CartDao;
import cart.dao.ProductDao;
import cart.dto.ProductResponseDto;
import cart.entity.CartEntity;
import cart.entity.ProductEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CartServiceTest {

    @Mock
    private ProductDao productDao;

    @Mock
    private CartDao cartDao;

    @InjectMocks
    private CartService cartService;

    @DisplayName("userId를 입력하여 해당 유저의 카트에 담긴 항목을 가져온다")
    @Test
    void getCartItemsByUserId() {
        when(cartDao.selectByUserId(1))
                .thenReturn(List.of(new CartEntity(1, 1), new CartEntity(1, 2)));

        when(productDao.selectById(1))
                .thenReturn(new ProductEntity(1, "name", 1000, "image"));

        when(productDao.selectById(2))
                .thenReturn(new ProductEntity(2, "name2", 2000, "image2"));

        List<ProductResponseDto> cartItems = cartService.getCartItems(1);

        assertAll(
                () -> assertThat(cartItems).hasSize(2),
                () -> assertThat(cartItems.get(0).getName()).isEqualTo("name"),
                () -> assertThat(cartItems.get(0).getPrice()).isEqualTo(1000),
                () -> assertThat(cartItems.get(0).getImage()).isEqualTo("image"),
                () -> assertThat(cartItems.get(1).getName()).isEqualTo("name2"),
                () -> assertThat(cartItems.get(1).getPrice()).isEqualTo(2000),
                () -> assertThat(cartItems.get(1).getImage()).isEqualTo("image2")
        );
    }
}
