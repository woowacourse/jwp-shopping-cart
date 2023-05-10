package cart.service;

import cart.dao.ProductDao;
import cart.dto.ProductRequest;
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
@WebMvcTest(ProductService.class)
class ProductServiceTest {
    @MockBean
    private ProductDao productDao;

    @Autowired
    private ProductService productService;

    @Test
    void 모든_상품_목록을_가져온다() {
        given(productDao.findAll()).willReturn(List.of(FIRST_PRODUCT.PRODUCT_WITH_ID, SECOND_PRODUCT.PRODUCT_WITH_ID));
        assertThat(productService.findAll()).usingRecursiveComparison().isEqualTo(List.of(FIRST_PRODUCT.RESPONSE, SECOND_PRODUCT.RESPONSE));
    }

    @Test
    void 상품을_저장한다() {
        given(productDao.save(any())).willReturn(1L);

        long productId = productService.save(FIRST_PRODUCT.REQUEST);

        assertThat(productId).isEqualTo(1L);
    }

    @Test
    void 상품을_수정한다() {
        final ProductRequest productRequest = new ProductRequest("updatedProduct", "updatedImageUrl", 1000);
        assertThatNoException()
                .isThrownBy(() -> productService.update(1L, productRequest));
    }

    @Test
    void 상품을_삭제한다() {
        assertThatNoException()
                .isThrownBy(() -> productService.delete(1L));
    }
}
