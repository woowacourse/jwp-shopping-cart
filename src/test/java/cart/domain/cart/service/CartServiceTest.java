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
        CartUser cartUserA = TestFixture.CART_USER_A;
        Long savedId = productRepository.save(TestFixture.PIZZA);
        cartUserRepository.save(cartUserA);
        AuthorizedCartUserDto request =
                new AuthorizedCartUserDto(cartUserA.getUserEmail(), cartUserA.getPassword());

        cartService.addProductInCart(request, savedId);

        List<Cart> allCarts = cartRepository.findAll();
        assertThat(allCarts).hasSize(1);
    }

    @DisplayName("사용자 장바구니 상품 조회 테스트")
    @Test
    void findAllProductsInCart() {
        CartUser cartUserA = TestFixture.CART_USER_A;
        cartUserRepository.save(cartUserA);
        Product pizza = saveProductAndGet(TestFixture.PIZZA);
        Product chicken = saveProductAndGet(TestFixture.CHICKEN);
        cartRepository.addProductInCart(cartUserA, pizza);
        cartRepository.addProductInCart(cartUserA, chicken);
        AuthorizedCartUserDto request =
                new AuthorizedCartUserDto(cartUserA.getUserEmail(), cartUserA.getPassword());

        List<ProductDto> allProductsInCart = cartService.findAllProductsInCart(request);

        assertThat(allProductsInCart).hasSize(2);
    }

    private Product saveProductAndGet(Product product) {
        Long savedId = productRepository.save(product);
        return productRepository.findById(savedId);
    }
}
