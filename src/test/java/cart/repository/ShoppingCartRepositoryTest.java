package cart.repository;

import static cart.CartFixture.TEST_CART_RECORD;
import static cart.MemberFixture.TEST_MEMBER;
import static cart.ProductFixture.PRODUCT_ENTITY2;
import static org.assertj.core.api.Assertions.assertThat;

import cart.service.dto.CartResponse;
import cart.service.dto.ProductResponse;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;

@JdbcTest
@Import(JdbcShoppingCartRepository.class)
class ShoppingCartRepositoryTest {

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    @Test
    @DisplayName("memberId와 productId로 장바구니에 product를 추가하는 기능 테스트")
    void addProduct() {
        final ProductResponse productResponse = new ProductResponse(PRODUCT_ENTITY2);
        shoppingCartRepository.addProduct(TEST_MEMBER.getId(), PRODUCT_ENTITY2.getId());

        final List<CartResponse> responses = shoppingCartRepository.findAllProduct(TEST_MEMBER.getId());

        assertThat(responses)
                .extracting(CartResponse::getProductResponse)
                .contains(productResponse);
    }

    @Test
    @DisplayName("cartid로 장바구니에 있는 product를 삭제하는 기능 테스트")
    void removeProduct() {
        final List<CartResponse> responses = shoppingCartRepository.findAllProduct(TEST_MEMBER.getId());
        assertThat(responses).hasSize(1);

        shoppingCartRepository.removeProduct(TEST_CART_RECORD.getId());

        final List<CartResponse> afterRemoveResponses = shoppingCartRepository.findAllProduct(TEST_MEMBER.getId());
        assertThat(afterRemoveResponses).hasSize(0);
    }

    @Test
    @DisplayName("memberId로 모든 프로덕트 정보를 찾는 기능 테스트")
    void findAllProduct() {
        final List<CartResponse> responses = shoppingCartRepository.findAllProduct(TEST_MEMBER.getId());

        assertThat(responses).hasSize(1);
    }
}
