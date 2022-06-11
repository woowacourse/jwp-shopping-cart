package woowacourse.shoppingcart.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.dto.request.CartItemRequest;
import woowacourse.shoppingcart.dto.request.MemberCreateRequest;
import woowacourse.shoppingcart.dto.request.ProductRequest;
import woowacourse.shoppingcart.dto.response.CartItemResponse;
import woowacourse.shoppingcart.dto.response.ProductResponse;

@SpringBootTest
@Transactional
class CartServiceTest {

    @Autowired
    private MemberService memberService;
    @Autowired
    private ProductService productService;
    @Autowired
    private CartService cartService;

    @DisplayName("장바구니에 담으려는 상품이 담겨있지 않으면 새로 추가한다.")
    @Test
    void saveCartItem() {
        Long memberId = memberService.save(new MemberCreateRequest("abc@woowahan.com", "1q2w3e4r!", "우테코"));
        Long productId = productService.save(new ProductRequest("상품1", 1_000, 10, "www.woowa1.com"));
        CartItemRequest cartItemRequest = new CartItemRequest(productId, 1);

        Long cartItemId = cartService.addCartItem(memberId, cartItemRequest);

        assertThat(cartItemId).isNotNull();
    }

    @DisplayName("존재하지 않는 상품을 장바구니에 담으려 하면 예외가 발생한다.")
    @Test
    void saveNotExistProduct() {
        Long memberId = memberService.save(new MemberCreateRequest("abc@woowahan.com", "1q2w3e4r!", "우테코"));
        CartItemRequest cartItemRequest = new CartItemRequest(Long.MAX_VALUE, 1);

        assertThatThrownBy(() -> cartService.addCartItem(memberId, cartItemRequest))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("존재하지 않는 상품입니다.");
    }

    @DisplayName("상품 재고보다 많은 양을 장바구니에 담으려 하면 예외가 발생한다.")
    @Test
    void saveInsufficientProduct() {
        Long memberId = memberService.save(new MemberCreateRequest("abc@woowahan.com", "1q2w3e4r!", "우테코"));
        Long productId = productService.save(new ProductRequest("상품1", 1_000, 1, "www.woowa1.com"));
        CartItemRequest cartItemRequest = new CartItemRequest(productId, 2);

        assertThatThrownBy(() -> cartService.addCartItem(memberId, cartItemRequest))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("재고가 충분하지 않습니다.");
    }

    @DisplayName("장바구니의 모든 상품 정보를 조회한다.")
    @Test
    void findAll() {
        Long memberId = memberService.save(new MemberCreateRequest("abc@woowahan.com", "1q2w3e4r!", "우테코"));
        Long productId = productService.save(new ProductRequest("상품1", 1_000, 10, "www.woowa1.com"));
        CartItemRequest cartItemRequest = new CartItemRequest(productId, 1);
        Long product2Id = productService.save(new ProductRequest("상품2", 1_000, 10, "www.woowa2.com"));
        CartItemRequest cartItem2Request = new CartItemRequest(product2Id, 1);
        cartService.addCartItem(memberId, cartItemRequest);
        cartService.addCartItem(memberId, cartItem2Request);

        List<CartItemResponse> cartItems = cartService.findAll(memberId);

        assertThat(cartItems).hasSize(2)
                .extracting(CartItemResponse::getProduct)
                .extracting(ProductResponse::getId)
                .containsOnly(productId, product2Id);
    }

    @DisplayName("장바구니에 담으려는 상품이 이미 담겨 있으면 상품 개수를 추가하고 업데이트한다.")
    @Test
    void addCartItem() {
        Long memberId = memberService.save(new MemberCreateRequest("abc@woowahan.com", "1q2w3e4r!", "우테코"));
        Long productId = productService.save(new ProductRequest("상품1", 1_000, 10, "www.woowa1.com"));
        CartItemRequest cartItemRequest = new CartItemRequest(productId, 1);
        cartService.addCartItem(memberId, cartItemRequest);

        cartService.addCartItem(memberId, cartItemRequest);
        CartItemResponse cartItemResponse = cartService.findAll(memberId)
                .get(0);

        assertThat(cartItemResponse.getQuantity()).isEqualTo(2);
    }

    @DisplayName("장바구니 상품 수량 변경 요청이 들어오면 요청된 수량으로 변경한다.")
    @Test
    void updateCartItemQuantity() {
        Long memberId = memberService.save(new MemberCreateRequest("abc@woowahan.com", "1q2w3e4r!", "우테코"));
        Long productId = productService.save(new ProductRequest("상품1", 1_000, 10, "www.woowa1.com"));
        CartItemRequest cartItemRequest = new CartItemRequest(productId, 1);
        cartService.addCartItem(memberId, cartItemRequest);
        CartItemRequest updateRequest = new CartItemRequest(productId, 5);

        cartService.updateCartItemQuantity(memberId, updateRequest);
        CartItemResponse cartItemResponse = cartService.findAll(memberId)
                .get(0);

        assertThat(cartItemResponse.getQuantity()).isEqualTo(5);
    }

    @DisplayName("장바구니에 존재하지 않는 상품의 수량을 변경하려 하면 예외를 반환한다.")
    @Test
    void updateItemNotInCart() {
        Long memberId = memberService.save(new MemberCreateRequest("abc@woowahan.com", "1q2w3e4r!", "우테코"));
        Long productId = productService.save(new ProductRequest("상품1", 1_000, 10, "www.woowa1.com"));
        CartItemRequest updateRequest = new CartItemRequest(productId, 9);

        assertThatThrownBy(() -> cartService.updateCartItemQuantity(memberId, updateRequest))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("장바구니에 존재하지 않는 상품입니다.");
    }

    @DisplayName("장바구니에서 상품을 제거한다.")
    @Test
    void deleteCartItem() {
        Long memberId = memberService.save(new MemberCreateRequest("abc@woowahan.com", "1q2w3e4r!", "우테코"));
        Long productId = productService.save(new ProductRequest("상품1", 1_000, 10, "www.woowa1.com"));
        CartItemRequest cartItemRequest = new CartItemRequest(productId, 1);
        cartService.addCartItem(memberId, cartItemRequest);

        cartService.deleteCartItem(memberId, productId);
        List<CartItemResponse> cartItems = cartService.findAll(memberId);

        assertThat(cartItems).isEmpty();
    }

    @DisplayName("장바구니에 존재하지 않는 상품을 장바구니에서 제거하려고 하면 예외를 반환한다.")
    @Test
    void deleteItemNotInCart() {
        Long memberId = memberService.save(new MemberCreateRequest("abc@woowahan.com", "1q2w3e4r!", "우테코"));
        Long productId = productService.save(new ProductRequest("상품1", 1_000, 10, "www.woowa1.com"));

        assertThatThrownBy(() -> cartService.deleteCartItem(memberId, productId))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("장바구니에 존재하지 않는 상품입니다.");
    }
}
