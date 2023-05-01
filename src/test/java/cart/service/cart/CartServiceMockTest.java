package cart.service.cart;

import cart.domain.cart.Cart;
import cart.domain.member.Member;
import cart.domain.product.Product;
import cart.dto.product.ProductsResponseDto;
import cart.repository.cart.CartRepository;
import cart.service.CartService;
import cart.service.MemberService;
import cart.service.ProductService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static cart.factory.member.MemberFactory.createMember;
import static cart.factory.product.ProductFactory.createProduct;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class CartServiceMockTest {

    @InjectMocks
    private CartService cartService;

    @Mock
    private CartRepository cartRepository;

    @Mock
    private MemberService memberService;

    @Mock
    private ProductService productService;

    @Test
    @DisplayName("장바구니를 조회한다.")
    void returns_all_carts() {
        // given
        Member member = createMember();
        Product product = createProduct();

        given(cartRepository.findAllByMember(member)).willReturn(List.of(Cart.from(member, product)));
        given(productService.findById(any())).willReturn(product);

        // when
        ProductsResponseDto result = cartService.findAll(member);

        // then
        assertAll(
                () -> assertThat(result.getProducts().size()).isEqualTo(1),
                () -> assertThat(result.getProducts().get(0).getName()).isEqualTo(product.getName())
        );
    }

    @Test
    @DisplayName("장바구니에 상품을 추가한다.")
    void add_cart() {
        // given
        Long productId = 1L;
        Member member = createMember();
        Product product = createProduct();

        given(productService.findById(productId)).willReturn(product);

        // when
        cartService.addCart(member, productId);

        // then
        verify(cartRepository).save(any(Cart.class));
    }

    @Test
    @DisplayName("장바구니에서 상품을 삭제한다.")
    void delete_cart() {
        // given
        Long productId = 1L;

        Member member = createMember();
        Product product = createProduct();
        List<Cart> memberCart = List.of(Cart.from(member, product));

        given(productService.findById(productId)).willReturn(product);
        given(cartRepository.findAllByMember(member)).willReturn(memberCart);
        given(productService.findById(memberCart.get(0).getProductId())).willReturn(product);

        // when
        cartService.deleteCart(member, productId);

        // then
        verify(cartRepository).delete(member, product);
    }
}
