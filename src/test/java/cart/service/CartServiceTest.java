package cart.service;

import cart.auth.MemberInfo;
import cart.domain.Cart;
import cart.domain.Product;
import cart.dto.response.ProductDto;
import cart.excpetion.CartException;
import cart.repository.CartRepository;
import cart.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CartServiceTest {

    private static final MemberInfo MEMBER_INFO = new MemberInfo(1, "email");
    private Cart defaultCart;
    @InjectMocks
    private CartService cartService;

    @Mock
    private CartRepository cartRepository;

    @Mock
    private ProductRepository productRepository;

    @BeforeEach
    void setting() {
        final List<Product> products = new ArrayList<>();
        products.add(new Product(1, "a", "", 1));
        defaultCart = new Cart(1, products);
    }

    @DisplayName("만약 이미 장바구니에 해당 아이템이 등록되어 있다면 예외가 발생한다")
    @Test
    void addProduct_invalid_exitingCartItem() {
        //given
        given(cartRepository.getCartOf(anyInt())).willReturn(defaultCart);
        given(productRepository.findBy(anyInt())).willReturn(Optional.of(new Product(1, "a", "", 1)));

        //when,then
        assertThatThrownBy(() -> cartService.addProduct(MEMBER_INFO, 1))
                .isInstanceOf(CartException.class);
    }

    @DisplayName("만약 존재하지 않는 MemberId 혹은 ProductId 를 넣는다면 예외를 발생시킨다")
    @Test
    void addProduct_invalid_nonexistenceRequest() {
        //given
        given(productRepository.findBy(anyInt()))
                .willReturn(Optional.empty());

        //when,then
        assertThatThrownBy(() -> cartService.addProduct(MEMBER_INFO, 1)).isInstanceOf(CartException.class);
    }

    @DisplayName("정상 요청 시 카트에 새로운 아이템을 넣는다")
    @Test
    void addProduct() {
        //given
        final int newItemId = 2;
        given(productRepository.findBy(anyInt()))
                .willReturn(Optional.of(new Product(newItemId, "a", "", 1)));
        given(cartRepository.getCartOf(anyInt()))
                .willReturn(defaultCart);
        doNothing().when(cartRepository).save(any());

        //when
        cartService.addProduct(MEMBER_INFO, newItemId);

        //then
        verify(cartRepository, times(1)).save(any());
        verify(cartRepository, times(1)).getCartOf(anyInt());
        verify(productRepository, times(1)).findBy(anyInt());
    }

    @DisplayName("카드에 등록되어 있는 정보에 대한 삭제 요청이 아니라면 예외가 발생한다")
    @Test
    void deleteProduct_invalid_nonexistenceCartData() {
        //given
        final int productNotInCart = 2;
        given(productRepository.findBy(anyInt()))
                .willReturn(Optional.of(new Product(productNotInCart, "a", "", 1)));
        given(cartRepository.getCartOf(anyInt()))
                .willReturn(defaultCart);

        //when,then
        assertThatThrownBy(() -> cartService.deleteProduct(MEMBER_INFO, productNotInCart)).isInstanceOf(CartException.class);
    }

    @DisplayName("만약 해당 유저의 cart 항목이 있다면 해당 Cart 아이템을 삭제한다")
    @Test
    void deleteProduct() {
        //given
        given(productRepository.findBy(anyInt()))
                .willReturn(Optional.of(new Product(1, "a", "", 1)));
        given(cartRepository.getCartOf(anyInt()))
                .willReturn(defaultCart);
        doNothing().when(cartRepository).save(any());

        //when
        cartService.deleteProduct(MEMBER_INFO, 1);

        //then
        verify(cartRepository, times(1)).save(any());
        verify(cartRepository, times(1)).getCartOf(anyInt());
        verify(productRepository, times(1)).findBy(anyInt());
    }

    @DisplayName("유저의 모든 장바구니 상품을 가져온다.")
    @Test
    void getProductsOf() {
        //given
        given(cartRepository.getCartOf(anyInt()))
                .willReturn(new Cart(1, List.of(
                                new Product(1, "name1", "image1", 1),
                                new Product(2, "name2", "image2", 2)
                        ))
                );

        //when
        final List<ProductDto> userProducts = cartService.getProductsOf(new MemberInfo(1, "email"));

        //then
        assertThat(userProducts).hasSize(2);
    }
}
