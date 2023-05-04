package cart.domain.cart;

import cart.domain.member.Member;
import cart.domain.product.Product;
import cart.exception.CartItemDuplicatedException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static cart.factory.cart.CartFactory.createCart;
import static cart.factory.member.MemberFactory.createMember;
import static cart.factory.product.ProductFactory.createProduct;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class CartTest {

    @Test
    @DisplayName("품목을 추가할 때 중복 품목이면 예외를 발생한다.")
    void throws_exception_when_added_product_is_duplicated() {
        // given
        Member member = createMember();
        Product product = createProduct();
        Cart cart = createCart(member, product);

        // when & then
        assertThatThrownBy(() -> cart.addCartItem(product))
                .isInstanceOf(CartItemDuplicatedException.class);
    }
}
