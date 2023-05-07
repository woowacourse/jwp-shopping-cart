package cart.domain.cart.service;

import static org.assertj.core.api.Assertions.assertThat;

import cart.cart.domain.CartRepository;
import cart.cart.service.dto.AuthorizedCartUserDto;
import cart.cart.usecase.DeleteOneProductInCartUseCase;
import cart.domain.product.TestFixture;
import cart.product.domain.Product;
import cart.product.domain.ProductRepository;
import cart.user.domain.CartUser;
import cart.user.domain.CartUserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest(webEnvironment = WebEnvironment.NONE)
class DeleteOneProductInCartServiceTest {

    @Autowired
    private DeleteOneProductInCartUseCase deleteProductInCartService;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CartUserRepository cartUserRepository;

    @DisplayName("사용자 장바구니 삭제 테스트")
    @Test
    void deleteProductInCart() {
        //given
        final CartUser cartUserA = TestFixture.CART_USER_A;
        cartUserRepository.save(cartUserA);

        final Product pizza = saveProductAndGet(TestFixture.PIZZA);
        final Product chicken = saveProductAndGet(TestFixture.CHICKEN);

        cartRepository.addProductInCart(cartUserA, pizza);
        cartRepository.addProductInCart(cartUserA, chicken);
        assertThat(cartRepository.findCartByCartUser(cartUserA).getProducts()).hasSize(2);
        final AuthorizedCartUserDto userDto =
                new AuthorizedCartUserDto(cartUserA.getUserEmail(), cartUserA.getPassword());

        //when
        deleteProductInCartService.deleteSingleProductInCart(userDto, pizza.getProductId());

        //then
        assertThat(cartRepository.findCartByCartUser(cartUserA).getProducts()).hasSize(1);
    }

    private Product saveProductAndGet(final Product product) {
        final Long savedId = productRepository.save(product);
        return productRepository.findById(savedId).get();
    }
}
