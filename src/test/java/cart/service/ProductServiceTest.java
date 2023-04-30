package cart.service;

import cart.dao.ProductDao;
import cart.domain.Product;
import cart.dto.ProductRequest;
import cart.dto.ProductResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.mockito.BDDMockito.given;

@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@WebMvcTest(ProductService.class)
class ProductServiceTest {
    @MockBean
    private ProductDao productDao;
    @Autowired
    private ProductService productService;
    private Product firstProduct;
    private Product secondProduct;
    
    @BeforeEach
    void setUp() {
        // given
        firstProduct = new Product(1L, "홍고", "https://ca.slack-edge.com/TFELTJB7V-U04M4NFB5TN-e18b78fabe81-512", 1_000_000_000);
        secondProduct = new Product(2L, "아벨", "https://ca.slack-edge.com/TFELTJB7V-U04LMNLQ78X-a7ef923d5391-512", 1_000_000_000);
        given(productDao.findAll()).willReturn(List.of(firstProduct, secondProduct));
    }
    
    @Test
    void 모든_상품_목록을_가져온다() {
        // when
        final ProductResponse firstProductResponse = ProductResponse.from(firstProduct);
        final ProductResponse secondProductResponse = ProductResponse.from(secondProduct);
        final List<ProductResponse> productResponses = productService.findAll();
        
        // then
        assertThat(productResponses).containsExactly(firstProductResponse, secondProductResponse);
    }

    @Test
    void 상품을_저장한다() {
        // expect
        assertThatNoException()
                .isThrownBy(() -> productService.save(new ProductRequest()));
    }

    @Test
    void 상품을_수정한다() {
        // expect
        assertThatNoException()
                .isThrownBy(() -> productService.update(2L, new ProductRequest()));
    }

    @Test
    void 상품을_삭제한다() {
        // expect
        assertThatNoException()
                .isThrownBy(() -> productService.delete(2L));
    }
}
