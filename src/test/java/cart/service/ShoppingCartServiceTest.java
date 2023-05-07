package cart.service;

import static cart.fixture.CartFixture.TEST_CART_RECORD;
import static cart.fixture.MemberFixture.TEST_MEMBER1;
import static cart.fixture.ProductFixture.PRODUCT_ENTITY3;
import static org.assertj.core.api.Assertions.assertThat;

import cart.fixture.ProductFixture;
import cart.repository.JdbcMemberRepository;
import cart.repository.JdbcShoppingCartRepository;
import cart.service.dto.CartResponse;
import cart.service.dto.MemberInfo;
import cart.service.dto.ProductResponse;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;

@JdbcTest
@Import({JdbcMemberRepository.class, JdbcShoppingCartRepository.class, ShoppingCartService.class})
class ShoppingCartServiceTest {

    private static final MemberInfo MEMBER_INFO = new MemberInfo(TEST_MEMBER1.getEmail(), TEST_MEMBER1.getPassword());
    @Autowired
    private ShoppingCartService shoppingCartService;

    @Test
    @DisplayName("memberInfo로 장바구니에 담은 product들 조회")
    public void findAllTest() {
        final ProductResponse productResponse = ProductResponse.from(ProductFixture.PRODUCT_ENTITY1);

        final List<CartResponse> allProduct = shoppingCartService.findAllProduct(MEMBER_INFO);

        assertThat(allProduct)
                .extracting(CartResponse::getProductResponse)
                .containsExactly(productResponse);
    }

    @Test
    @Transactional
    @DisplayName("memberInfo와 productid로 장바구니에 값을 추가하는 기능 테스트")
    public void addCartTest() {
        final ProductResponse productResponse = ProductResponse.from(PRODUCT_ENTITY3);

        shoppingCartService.addCartProduct(MEMBER_INFO, PRODUCT_ENTITY3.getId());

        final List<CartResponse> allProduct = shoppingCartService.findAllProduct(MEMBER_INFO);
        assertThat(allProduct)
                .extracting(CartResponse::getProductResponse)
                .contains(productResponse);
    }

    @Test
    @Transactional
    @DisplayName("cartId로 장바구니에 있는 item을 제거하는 기능 테스트")
    public void removeProduct() {
        shoppingCartService.removeProduct(TEST_CART_RECORD.getId());

        final List<CartResponse> cartResponses = shoppingCartService.findAllProduct(MEMBER_INFO);
        assertThat(cartResponses).hasSize(0);
    }
}
