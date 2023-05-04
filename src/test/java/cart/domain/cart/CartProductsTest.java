package cart.domain.cart;

import static cart.domain.product.ProductFixture.NUNU_ID_PRODUCT;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import cart.domain.product.Product;
import cart.exception.AlreadyAddedProductException;
import org.junit.jupiter.api.Test;

@SuppressWarnings("NonAsciiCharacters")
class CartProductsTest {

    private final Product product = NUNU_ID_PRODUCT;

    @Test
    void 같은_상품이_여러번_추가되면_예외() {
        final CartProducts cartProducts = new CartProducts();
        cartProducts.add(product);

        assertThatThrownBy(() -> cartProducts.add(product))
                .isInstanceOf(AlreadyAddedProductException.class)
                .hasMessage("이미 장바구니에 담긴 상품입니다.");
    }
}
