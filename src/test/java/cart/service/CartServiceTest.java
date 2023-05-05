package cart.service;

import cart.dao.CartDao;
import cart.dao.ProductDao;
import cart.dto.CartResponseDto;
import cart.entity.Cart;
import cart.entity.Product;
import cart.vo.Email;
import cart.vo.Name;
import cart.vo.Price;
import cart.vo.Url;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.BDDMockito.given;

class CartServiceTest {

    private CartService cartService;
    private CartDao cartDao;
    private ProductDao productDao;

    @BeforeEach
    void beforeEach() {
        cartDao = Mockito.mock(CartDao.class);
        productDao = Mockito.mock(ProductDao.class);
        cartService = new CartService(cartDao, productDao);
    }

    @Test
    @DisplayName("findAll 을 통해 ResponseDto 가 잘 반환되는지 확인한다.")
    void findAll() {
        Email email = Email.from("kpeel5839@a.com");
        given(cartDao.selectAll(email))
                .willReturn(new ArrayList<>(List.of(getCart(1L, email), getCart(2L, email))));
        given(productDao.findById(1L))
                .willReturn(getProduct(1L, "샐러드", 10000));
        given(productDao.findById(2L))
                .willReturn(getProduct(2L, "치킨", 10001));

        List<CartResponseDto> carts = cartService.findAll(email);

        assertThat(carts).hasSize(2);
        assertThat(carts.stream()
                .anyMatch(cartResponseDto -> cartResponseDto.getProductResponseDto()
                        .getName()
                        .equals("샐러드"))
        ).isTrue();
        assertThat(carts.stream()
                .anyMatch(cartResponseDto -> cartResponseDto.getProductResponseDto()
                        .getName()
                        .equals("치킨"))
        ).isTrue();
    }

    @Test
    @DisplayName("다른 사용자가 Cart 에 접근하여 삭제하려 할 때 예외를 반환")
    void removeByIdFail() {
        Email legalUserEmail = Email.from("kpeel5839@a.com");
        Email illegalUserEmail = Email.from("kpeel583@a.com");
        given(cartDao.findById(1L))
                .willReturn(getCart(1L, legalUserEmail));

        assertThatThrownBy(() -> cartService.removeById(1L, illegalUserEmail))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("삭제할 권한이 없습니다.");
    }

    @Test
    @DisplayName("정상적인 사용자가 Cart 에 접근하여 삭제하려 할 때 정상 삭제")
    void removeByIdSuccess() {
        Email email = Email.from("kpeel5839@a.com");
        given(cartDao.findById(1L))
                .willReturn(getCart(1L, email));

        assertDoesNotThrow(() -> cartService.removeById(1L, email));
    }

    private Cart getCart(long id, Email email) {
        return new Cart.Builder()
                .id(id)
                .email(email)
                .productId(id)
                .build();
    }

    private Product getProduct(long id, String name, int price) {
        return new Product.Builder()
                .id(id)
                .name(Name.from(name))
                .price(Price.from(price))
                .imageUrl(Url.from("url")).build();
    }

}
