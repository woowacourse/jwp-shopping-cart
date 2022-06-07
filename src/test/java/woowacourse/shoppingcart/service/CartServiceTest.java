package woowacourse.shoppingcart.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.shoppingcart.application.CartService;
import woowacourse.shoppingcart.dto.AddCartItemRequest;
import woowacourse.shoppingcart.dto.DeleteCartItemIdsRequest;
import woowacourse.shoppingcart.exception.NotInCustomerCartItemException;

@SpringBootTest
@Sql(scripts = {"classpath:schema.sql", "classpath:data.sql"})
public class CartServiceTest {

    @Autowired
    private CartService cartService;

    @BeforeEach
    void setUp() {
        var addProductRequest = new AddCartItemRequest(1L, 1, true);
        cartService.addCart(1L, addProductRequest);
    }

    @Test
    void 장바구니_상품_추가() {
        var findAllCartItemResponse = cartService.getAllCartItem(1L);
        var findCartItemResponse = findAllCartItemResponse.getProducts().get(0);

        assertAll(
                () -> assertThat(findCartItemResponse.getId()).isEqualTo(1L),
                () -> assertThat(findCartItemResponse.getName()).isEqualTo("water"),
                () -> assertThat(findCartItemResponse.getPrice()).isEqualTo(1000),
                () -> assertThat(findCartItemResponse.getImageUrl()).isEqualTo("image_url"),
                () -> assertThat(findCartItemResponse.getQuantity()).isEqualTo(1),
                () -> assertThat(findCartItemResponse.getChecked()).isTrue()
        );
    }

    @Test
    void 장바구니_선택_상품_제거() {
        cartService.deleteCart(1L, new DeleteCartItemIdsRequest(List.of(1L)));

        var findAllCartItemResponse = cartService.getAllCartItem(1L);
        assertThat(findAllCartItemResponse.getProducts().size()).isEqualTo(0);
    }

    @Test
    void 유저가_장바구니_아이템_아이디를_가지고_있지_않는_경우() {
        var invalidCartItemId = 2L;

        assertThatThrownBy(() -> cartService.deleteCart(1L, new DeleteCartItemIdsRequest(List.of(invalidCartItemId))))
                .isInstanceOf(NotInCustomerCartItemException.class);

    }
}
