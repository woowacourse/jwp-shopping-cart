package cart.service;

import cart.dao.CartProductDao;
import cart.domain.Product;
import cart.dto.CartProductRequest;
import cart.dto.ProductResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static cart.fixture.ProductFixture.FIRST_PRODUCT;
import static cart.fixture.ProductFixture.SECOND_PRODUCT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@SuppressWarnings("NonAsciiCharacters")
@WebMvcTest(CartProductService.class)
public class CartProductServiceTest {
    @MockBean
    private CartProductDao cartProductDao;

    @Autowired
    private CartProductService cartProductService;

    @Test
    void 사용자의_모든_장바구니_상품_목록을_가져온다() {
        List<Product> products = List.of(FIRST_PRODUCT.PRODUCT_WITH_ID, SECOND_PRODUCT.PRODUCT_WITH_ID);
        given(cartProductDao.findAllProductByMemberId(1L)).willReturn(products);

        List<ProductResponse> productResponses = List.of(FIRST_PRODUCT.RESPONSE, SECOND_PRODUCT.RESPONSE);
        assertThat(cartProductService.findAll(1L)).usingRecursiveComparison().isEqualTo(productResponses);
    }

    @Test
    void 사용자의_장바구니_상품을_저장한다() {
        given(cartProductDao.save(any())).willReturn(1L);

        long cartId = cartProductService.save(2L, new CartProductRequest(1L));

        assertThat(cartId).isEqualTo(1L);
    }

    @Test
    void 사용자의_장바구니_상품을_삭제한다() {
        assertThatNoException()
                .isThrownBy(() -> cartProductService.delete(1L, 1L));
    }
}
