package cart.domain.cart.service;

import static org.assertj.core.api.Assertions.assertThat;

import cart.cart.domain.Cart;
import cart.cart.domain.CartRepository;
import cart.cart.service.dto.AuthorizedCartUserDto;
import cart.cart.usecase.SaveOneProductInCartUseCase;
import cart.domain.product.TestFixture;
import cart.product.domain.ProductRepository;
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
class SaveOneProductInCartServiceTest {

    @Autowired
    private SaveOneProductInCartUseCase saveProductInCartService;

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
        final CartUser cartUserA = TestFixture.CART_USER_A;
        final Long savedId = productRepository.save(TestFixture.PIZZA);

        cartUserRepository.save(cartUserA);
        final AuthorizedCartUserDto userDto =
                new AuthorizedCartUserDto(cartUserA.getUserEmail(), cartUserA.getPassword());

        //when
        saveProductInCartService.addSingleProductInCart(userDto, savedId);

        //then
        final List<Cart> allCarts = cartRepository.findAll();
        assertThat(allCarts).hasSize(1);
    }
}
