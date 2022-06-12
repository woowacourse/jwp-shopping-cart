package woowacourse.shoppingcart.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.domain.customer.UserName;
import woowacourse.shoppingcart.dto.request.CreateCartItemRequest;
import woowacourse.shoppingcart.dto.request.CreateProductRequest;
import woowacourse.shoppingcart.dto.response.CartItemResponse;
import woowacourse.shoppingcart.exception.notfound.NotFoundCustomerException;
import woowacourse.shoppingcart.exception.notfound.NotFoundProductException;

@SpringBootTest
@Sql(scripts = {"classpath:schema.sql", "classpath:data.sql"})
@Transactional
class CartItemServiceTest {

    private static final UserName USER_NAME = new UserName("puterism");

    @Autowired
    private CartItemService cartItemService;

    @Autowired
    private ProductService productService;

    @Test
    void 장바구니_아이템_추가() {
        // given
        Long productId = productService.addProduct(new CreateProductRequest("MacBook Air", 1_400_000, "https://www.apple.com/v/macbook-air-m2/a/images/overview/compare/compare_mba__bjfeags91jyu_large_2x.png"));

        // when
        cartItemService.addCart(USER_NAME, new CreateCartItemRequest(productId));

        // then
        CartItemResponse cartItemResponse = cartItemService.findCartsByCustomerName(USER_NAME).get(0);

        assertAll(
                () -> assertThat(cartItemResponse.getProductId()).isEqualTo(productId),
                () -> assertThat(cartItemResponse.getName()).isEqualTo("MacBook Air"),
                () -> assertThat(cartItemResponse.getPrice()).isEqualTo(1_400_000),
                () -> assertThat(cartItemResponse.getQuantity()).isEqualTo(1)
        );
    }

    @Test
    void 기존에_있는_장바구니_아이템_추가_시_합쳐짐() {
        // given
        Long productId = productService.addProduct(new CreateProductRequest("MacBook Air", 1_400_000, "https://www.apple.com/v/macbook-air-m2/a/images/overview/compare/compare_mba__bjfeags91jyu_large_2x.png"));
        Long originCartItemId = cartItemService.addCart(USER_NAME, new CreateCartItemRequest(productId));

        // when
        cartItemService.addCart(USER_NAME, new CreateCartItemRequest(productId));

        // then
        CartItemResponse cartItemResponse = cartItemService.findCartsByCustomerName(USER_NAME).get(0);

        assertAll(
                () -> assertThat(cartItemResponse.getId()).isEqualTo(originCartItemId),
                () -> assertThat(cartItemResponse.getName()).isEqualTo("MacBook Air"),
                () -> assertThat(cartItemResponse.getQuantity()).isEqualTo(2)
        );
    }

    @Test
    void 없는_사용자_장바구니_아이템_추가() {
        // given
        Long productId = productService.addProduct(new CreateProductRequest("MacBook Air", 1_400_000, "https://www.apple.com/v/macbook-air-m2/a/images/overview/compare/compare_mba__bjfeags91jyu_large_2x.png"));

        // when & then
        assertThatThrownBy(() -> cartItemService.addCart(new UserName("ellie"), new CreateCartItemRequest(productId)))
                .isInstanceOf(NotFoundCustomerException.class);
    }

    @Test
    void 장바구니_없는_아이템_추가() {
        // when & then
        assertThatThrownBy(() -> cartItemService.addCart(USER_NAME, new CreateCartItemRequest(100L)))
                .isInstanceOf(NotFoundProductException.class);
    }
}
