package cart.domain.cart.service;

import static org.assertj.core.api.Assertions.assertThat;

import cart.cart.domain.CartRepository;
import cart.cart.service.dto.AuthorizedCartUserDto;
import cart.cart.usecase.FindAllProductsInCartUseCase;
import cart.domain.product.TestFixture;
import cart.product.domain.Product;
import cart.product.domain.ProductRepository;
import cart.product.service.dto.ProductResponseDto;
import cart.user.domain.CartUser;
import cart.user.domain.CartUserRepository;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest(webEnvironment = WebEnvironment.NONE)
class FindAllProductInCartServiceTest {

    @Autowired
    private FindAllProductsInCartUseCase findProductInCartService;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CartUserRepository cartUserRepository;

    @DisplayName("사용자 장바구니 상품 조회 테스트")
    @Test
    void findAllProductsInCart() {
        //given
        final CartUser cartUserA = TestFixture.CART_USER_A;
        cartUserRepository.save(cartUserA);

        final Product pizza = saveProductAndGet(TestFixture.PIZZA);
        final Product chicken = saveProductAndGet(TestFixture.CHICKEN);

        cartRepository.addProductInCart(cartUserA, pizza);
        cartRepository.addProductInCart(cartUserA, chicken);
        final AuthorizedCartUserDto userDto =
                new AuthorizedCartUserDto(cartUserA.getUserEmail(), cartUserA.getPassword());

        //when
        final List<ProductResponseDto> allProductsInCart = findProductInCartService.findAllProductsInCart(userDto);

        //then
        assertThat(allProductsInCart).hasSize(2);
    }

    private Product saveProductAndGet(final Product product) {
        final Long savedId = productRepository.save(product);
        return productRepository.findById(savedId).get();
    }
}
