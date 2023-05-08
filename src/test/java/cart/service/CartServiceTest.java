package cart.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.BDDMockito.given;

import cart.dao.CartDao;
import cart.dto.CartItemResponseDto;
import cart.entity.CartItem;
import cart.entity.Product;
import cart.exception.ExistProductException;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class CartServiceTest {

    private final static String EMAIL = "email1@email.com";

    private CartService cartService;
    private CartDao cartDao;
    private ProductService productService;

    @BeforeEach
    void setUp() {
        cartDao = Mockito.mock(CartDao.class);
        productService = Mockito.mock(ProductService.class);
        cartService = new CartService(cartDao, productService);
    }

    @Test
    @DisplayName("장바구니에 담긴 상품을 모두 조회한다.")
    void findAll() {
        given(cartDao.findAll(EMAIL))
                .willReturn(List.of(
                        buildCartItem(1, "치킨", 10000, "imageUrl")
                ));

        List<CartItemResponseDto> cartItems = cartService.findAll(EMAIL);

        assertAll(
                () -> assertThat(cartItems.get(0).getId()).isEqualTo(1),
                () -> assertThat(cartItems.get(0).getName()).isEqualTo("치킨"),
                () -> assertThat(cartItems.get(0).getPrice()).isEqualTo(10000),
                () -> assertThat(cartItems.get(0).getImageUrl()).isEqualTo("imageUrl")
        );

    }

    @Test
    @DisplayName("장바구니에 상품을 추가한다.")
    void add() {
        given(cartDao.findCartIdByProductId(1, EMAIL))
                .willReturn(Collections.emptyList());
        given(cartDao.save(1, EMAIL))
                .willReturn(1);

        assertDoesNotThrow(
                () -> cartService.add(1, EMAIL)
        );

    }

    @Test
    @DisplayName("장바구니에 상품 추가 시 이미 담은 상품이면 예외가 발생한다.")
    void validateExistCartItem_add() {
        given(cartDao.findCartIdByProductId(1, EMAIL))
                .willReturn(List.of(1));

        assertThatThrownBy(
                () -> cartService.add(1, EMAIL)
        ).isInstanceOf(ExistProductException.class)
                .hasMessage("이미 장바구니에 담은 상품입니다.");

    }

    @Test
    @DisplayName("장바구니에 담긴 상품을 삭제한다.")
    void remove() {
        given(cartDao.findById(1))
                .willReturn(Optional.of(
                        buildCartItem(1, "치킨", 10000, "imageUrl")
                ));

        assertDoesNotThrow(
                () -> cartService.remove(1)
        );
    }

    @Test
    @DisplayName("장바구니에 존재하지 않는 상품을 삭제하면 예외가 발생한다.")
    void validateExistCartItem_remove() {
        given(cartDao.findById(1))
                .willReturn(Optional.empty());

        assertThatThrownBy(
                () -> cartService.remove(1)
        ).isInstanceOf(NoSuchElementException.class)
                .hasMessage("존재하지 않는 cart id 입니다.");
    }

    private CartItem buildCartItem(int id, String name, int price, String imageUrl) {
        return new CartItem.Builder()
                .id(1)
                .product(
                        new Product.Builder()
                                .id(id)
                                .name(name)
                                .price(price)
                                .imageUrl(imageUrl)
                                .build()
                )
                .build();
    }
}
