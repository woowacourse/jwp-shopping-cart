package cart.domain.cart.service;

import static org.assertj.core.api.Assertions.assertThat;

import cart.domain.cart.Cart;
import cart.domain.cart.CartRepository;
import cart.domain.cart.service.dto.AuthorizedCartUserDto;
import cart.domain.product.Product;
import cart.domain.product.ProductRepository;
import cart.domain.product.TestFixture;
import cart.domain.product.service.dto.ProductDto;
import cart.domain.user.CartUser;
import cart.domain.user.CartUserRepository;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest(webEnvironment = WebEnvironment.NONE)
class CartServiceTest {

    @Autowired
    private CartService cartService;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CartUserRepository cartUserRepository;

    @DisplayName("장바구니 상품 추가 테스트")
    @Test
    void addProductInCart() {
        //given
        CartUser cartUserA = TestFixture.CART_USER_A;
        Long savedId = productRepository.save(TestFixture.PIZZA);

        cartUserRepository.save(cartUserA);
        AuthorizedCartUserDto userDto =
                new AuthorizedCartUserDto(cartUserA.getUserEmail(), cartUserA.getPassword());

        //when
        cartService.addProductInCart(userDto, savedId);

        //then
        List<Cart> allCarts = cartRepository.findAll();
        assertThat(allCarts).hasSize(1);
    }

    @DisplayName("사용자 장바구니 상품 조회 테스트")
    @Test
    void findAllProductsInCart() {
        //given
        CartUser cartUserA = TestFixture.CART_USER_A;
        cartUserRepository.save(cartUserA);

        Product pizza = saveProductAndGet(TestFixture.PIZZA);
        Product chicken = saveProductAndGet(TestFixture.CHICKEN);

        cartRepository.addProductInCart(cartUserA, pizza);
        cartRepository.addProductInCart(cartUserA, chicken);
        AuthorizedCartUserDto userDto =
                new AuthorizedCartUserDto(cartUserA.getUserEmail(), cartUserA.getPassword());

        //when
        List<ProductDto> allProductsInCart = cartService.findAllProductsInCart(userDto);

        //then
        assertThat(allProductsInCart).hasSize(2);
    }

    @DisplayName("사용자 장바구니 삭제 테스트")
    @Test
    void deleteProductInCart() {
        //given
        CartUser cartUserA = TestFixture.CART_USER_A;
        cartUserRepository.save(cartUserA);

        Product pizza = saveProductAndGet(TestFixture.PIZZA);
        Product chicken = saveProductAndGet(TestFixture.CHICKEN);

        cartRepository.addProductInCart(cartUserA, pizza);
        cartRepository.addProductInCart(cartUserA, chicken);
        assertThat(cartRepository.findCartByCartUser(cartUserA).getProducts()).hasSize(2);
        AuthorizedCartUserDto userDto =
                new AuthorizedCartUserDto(cartUserA.getUserEmail(), cartUserA.getPassword());

        //when
        cartService.deleteProductInCart(userDto, pizza.getProductId());

        //then
        assertThat(cartRepository.findCartByCartUser(cartUserA).getProducts()).hasSize(1);
    }

    private Product saveProductAndGet(Product product) {
        Long savedId = productRepository.save(product);
        return productRepository.findById(savedId);
    }
}
