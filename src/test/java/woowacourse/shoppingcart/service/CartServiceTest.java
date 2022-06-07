package woowacourse.shoppingcart.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import woowacourse.shoppingcart.application.CartService;
import woowacourse.shoppingcart.dto.AddCartItemRequest;

@SpringBootTest
public class CartServiceTest {

    @Autowired
    private CartService cartService;

    @Test
    void 장바구니_상품_추가() {
        var addProductRequest = new AddCartItemRequest(1L, 1, true);
        cartService.addCart(1L, addProductRequest);

        var findAllCartItemResponse = cartService.getAllCartItem(1L);
        var findCartItemResponse = findAllCartItemResponse.getProducts().get(0);

        assertAll(
                () ->  assertThat(findCartItemResponse.getId()).isEqualTo(1L),
                () ->  assertThat(findCartItemResponse.getName()).isEqualTo("water"),
                () ->  assertThat(findCartItemResponse.getPrice()).isEqualTo(1000),
                () ->  assertThat(findCartItemResponse.getImageUrl()).isEqualTo("image_url"),
                () ->  assertThat(findCartItemResponse.getQuantity()).isEqualTo(1),
                () ->  assertThat(findCartItemResponse.getChecked()).isTrue()
        );
    }
}
