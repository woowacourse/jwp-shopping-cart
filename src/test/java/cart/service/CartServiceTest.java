package cart.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import cart.dao.cart.CartDao;
import cart.dao.cart.CartEntity;
import cart.global.exception.cart.ProductNotFoundInCartException;
import cart.global.infrastructure.Credential;
import cart.service.dto.cart.CartAddProductRequest;
import cart.service.dto.cart.CartAllProductSearchResponse;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest
@Sql("/schema.sql")
@Sql("/data.sql")
class CartServiceTest {

    @Autowired
    private CartService cartService;

    @Autowired
    private CartDao cartDao;

    private Credential credential;

    @BeforeEach
    void setUp() {
        final CartEntity cartEntity = new CartEntity(1L, 2L);
        final CartEntity cartEntity2 = new CartEntity(1L, 1L);

        cartDao.save(cartEntity);
        cartDao.save(cartEntity2);

        credential = new Credential("email@email.com", "asdf1234");
    }

    @Test
    @DisplayName("searchAllCartProducts() : cart안의 모든 product들을 조회할 수 있다.")
    void test_searchAllCartProducts() {
        // given
        final int resultSize = 2;
        final Long resultCartId = 1L;

        // when
        List<CartAllProductSearchResponse> cartAllProductSearchResponses =
                cartService.searchAllCartProducts(credential);

        // expected
        assertAll(
                () -> assertThat(cartAllProductSearchResponses).hasSize(resultSize),
                () -> assertEquals(resultCartId, cartAllProductSearchResponses.get(0).getId())
        );
    }

    @Test
    @DisplayName("save() : cart에 product를 추가할 수 있다.")
    void test_save() {
        // given
        final Long productId = 1L;
        final CartAddProductRequest cartAddProductRequest = new CartAddProductRequest(productId);

        // when
        int beforeSize = cartService.searchAllCartProducts(credential).size();

        cartService.save(credential, cartAddProductRequest);

        int afterSize = cartService.searchAllCartProducts(credential).size();

        // expected
        assertAll(
                () -> assertThat(beforeSize + 1).isEqualTo(afterSize),
                () -> assertThat(cartDao.findById(productId)).isNotNull()
        );
    }

    @Test
    @DisplayName("deleteProduct() : cart의 product를 삭제할 수 있다.")
    void test_deleteProduct() {
        // given
        final Long cartProductId = 1L;

        // when
        int beforeSize = cartService.searchAllCartProducts(credential).size();

        cartService.deleteProduct(cartProductId);

        int afterSize = cartService.searchAllCartProducts(credential).size();

        // expected
        assertThat(beforeSize - 1).isEqualTo(afterSize);
    }

    @Test
    @DisplayName("deleteProduct() : 없는 product를 삭제하면 ProductNotFoundInCartException이 발생한다.")
    void test_deleteProduct_ProductNotFoundInCartException() {
        // given
        final Long cartProductId = 3L;

        // when & expected
        assertThatThrownBy(() -> cartService.deleteProduct(cartProductId))
                .isInstanceOf(ProductNotFoundInCartException.class);
    }
}
