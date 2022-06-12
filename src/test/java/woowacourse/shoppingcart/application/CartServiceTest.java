package woowacourse.shoppingcart.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static woowacourse.ShoppingCartFixture.잉_회원생성요청;
import static woowacourse.ShoppingCartFixture.제품_추가_요청1;
import static woowacourse.ShoppingCartFixture.제품_추가_요청2;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.TestConstructor.AutowireMode;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.shoppingcart.dto.request.CartAddRequest;
import woowacourse.shoppingcart.dto.request.CartDeleteRequest;
import woowacourse.shoppingcart.dto.request.CartUpdateRequest;
import woowacourse.shoppingcart.dto.response.CartResponse;
import woowacourse.shoppingcart.exception.NotInCustomerCartItemException;


@SpringBootTest
@TestConstructor(autowireMode = AutowireMode.ALL)
@Sql("/truncate.sql")
class CartServiceTest {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private ProductService productService;

    @Autowired
    private CartService cartService;

    private Long customerId;
    private Long productId1;
    private Long productId2;

    @BeforeEach
    void setUp() {
        customerId = customerService.create(잉_회원생성요청);
        productId1 = productService.addProduct(제품_추가_요청1);
        productId2 = productService.addProduct(제품_추가_요청2);
    }

    @DisplayName("장바구니에 제품을 추가한다.")
    @Test
    void addCart() {
        final Long 장바구니_추가됨 = cartService.addCart(customerId, new CartAddRequest(productId1));

        assertThat(장바구니_추가됨).isEqualTo(1L);
    }

    @DisplayName("장바구니를 조회한다.")
    @Test
    void findCartsByCustomerId() {
        final Long 장바구니_상품추가됨1 = cartService.addCart(customerId, new CartAddRequest(productId1));
        final Long 장바구니_상품추가됨2 = cartService.addCart(customerId, new CartAddRequest(productId2));

        final List<CartResponse> 장바구니_조회함 = cartService.findCartsByCustomerId(customerId);
        final CartResponse 장바구니_상품1 = 장바구니_조회함.get(0);
        final CartResponse 장바구니_상품2 = 장바구니_조회함.get(1);

        assertThat(장바구니_조회함.size()).isEqualTo(2);

        assertThat(장바구니_상품1.getId()).isEqualTo(장바구니_상품추가됨1);
        assertThat(장바구니_상품1.getProductId()).isEqualTo(productId1);
        assertThat(장바구니_상품1.getQuantity()).isEqualTo(1);
        assertThat(장바구니_상품1.getName()).isEqualTo(제품_추가_요청1.getName());
        assertThat(장바구니_상품1.getPrice()).isEqualTo(제품_추가_요청1.getPrice());
        assertThat(장바구니_상품1.getImageUrl()).isEqualTo(제품_추가_요청1.getImageUrl());

        assertThat(장바구니_상품2.getId()).isEqualTo(장바구니_상품추가됨2);
        assertThat(장바구니_상품2.getProductId()).isEqualTo(productId2);
        assertThat(장바구니_상품2.getQuantity()).isEqualTo(1);
        assertThat(장바구니_상품2.getName()).isEqualTo(제품_추가_요청2.getName());
        assertThat(장바구니_상품2.getPrice()).isEqualTo(제품_추가_요청2.getPrice());
        assertThat(장바구니_상품2.getImageUrl()).isEqualTo(제품_추가_요청2.getImageUrl());
    }

    @DisplayName("장바구니를 수정한다.")
    @Test
    void updateCart() {
        final Long 장바구니_상품추가됨1 = cartService.addCart(customerId, new CartAddRequest(productId1));

        final int newQuantity = 3;
        cartService.updateCart(customerId, new CartUpdateRequest(productId1, newQuantity));
        final List<CartResponse> 장바구니_조회함 = cartService.findCartsByCustomerId(customerId);
        final CartResponse 장바구니_상품 = 장바구니_조회함.get(0);

        assertThat(장바구니_상품.getId()).isEqualTo(장바구니_상품추가됨1);
        assertThat(장바구니_상품.getProductId()).isEqualTo(productId1);
        assertThat(장바구니_상품.getQuantity()).isEqualTo(newQuantity);
        assertThat(장바구니_상품.getName()).isEqualTo(제품_추가_요청1.getName());
        assertThat(장바구니_상품.getPrice()).isEqualTo(제품_추가_요청1.getPrice());
        assertThat(장바구니_상품.getImageUrl()).isEqualTo(제품_추가_요청1.getImageUrl());
    }

    @DisplayName("장바구니에 제품이 등록되어 있지 않은 경우, 장바구니를 수정할 수 없다.")
    @Test
    void updateCartWithNorFoundProductInCart() {
        final int newQuantity = 3;
        assertThatThrownBy(()
                -> cartService.updateCart(customerId, new CartUpdateRequest(productId1, newQuantity))
        ).isInstanceOf(NotInCustomerCartItemException.class);
    }

    @DisplayName("장바구니를 삭제한다.")
    @Test
    void deleteCart() {
        final Long 장바구니_상품추가됨1 = cartService.addCart(customerId, new CartAddRequest(productId1));
        final Long 장바구니_상품추가됨2 = cartService.addCart(customerId, new CartAddRequest(productId2));
        final List<Long> 삭제할_장바구니_제품들 = List.of(장바구니_상품추가됨1, 장바구니_상품추가됨2);

        cartService.deleteCart(customerId, new CartDeleteRequest(삭제할_장바구니_제품들));
        final List<CartResponse> 장바구니_조회함 = cartService.findCartsByCustomerId(customerId);

        assertThat(장바구니_조회함).isEmpty();
    }

    @DisplayName("장바구니에 삭제할 장바구니아이디가 없는 경우, 장바구니를 삭제할 수 없다.")
    @Test
    void deleteCartWithNotFoundCartId() {
        final Long 장바구니_상품추가됨1 = cartService.addCart(customerId, new CartAddRequest(productId1));
        final List<Long> 삭제할_장바구니_제품들 = List.of(장바구니_상품추가됨1, 2L);

        assertThatThrownBy(()
                -> cartService.deleteCart(customerId, new CartDeleteRequest(삭제할_장바구니_제품들))
        ).isInstanceOf(NotInCustomerCartItemException.class);
    }
}
