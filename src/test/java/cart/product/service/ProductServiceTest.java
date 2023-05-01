package cart.product.service;

import cart.product.dao.ProductDao;
import cart.product.domain.Product;
import cart.product.dto.ProductRequest;
import cart.product.dto.ProductResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@WebMvcTest(ProductService.class)
class ProductServiceTest {
    @Autowired
    private ProductService productService;
    @MockBean
    private ProductDao productDao;
    private InOrder inOrder;
    private Product firstProduct;
    private Product secondProduct;
    
    @BeforeEach
    void setUp() {
        inOrder = inOrder(productDao);
        // given
        firstProduct = new Product(1L, "홍고", "https://ca.slack-edge.com/TFELTJB7V-U04M4NFB5TN-e18b78fabe81-512", 10_000_000);
        secondProduct = new Product(2L, "아벨", "https://ca.slack-edge.com/TFELTJB7V-U04LMNLQ78X-a7ef923d5391-512", 10_000_000);
        given(productDao.findAll()).willReturn(List.of(firstProduct, secondProduct));
    }
    
    @Test
    void 모든_상품_목록을_가져온다() {
        // when
        final ProductResponse firstProductResponse = ProductResponse.from(firstProduct);
        final ProductResponse secondProductResponse = ProductResponse.from(secondProduct);
        final List<ProductResponse> productResponses = productService.findAll();
        
        // then
        assertAll(
                () -> then(productDao).should(only()).findAll(),
                () -> assertThat(productResponses).containsExactly(firstProductResponse, secondProductResponse)
        );
    }
    
    @Test
    void 상품을_저장한다() {
        // expect
        assertAll(
                () -> assertThatNoException()
                        .isThrownBy(() -> productService.save(new ProductRequest("product", "abel.com", 1000))),
                () -> then(productDao).should(only()).save(any())
        );
    }
    
    @Test
    void 상품을_수정한다() {
        // expect
        assertAll(
                () -> assertThatNoException()
                        .isThrownBy(() -> productService.update(2L, new ProductRequest("product", "abel.com", 1000))),
                () -> then(productDao).should(inOrder).findAll(),
                () -> then(productDao).should(inOrder).update(any())
        );
    }
    
    @Test
    void 상품을_삭제한다() {
        // expect
        assertAll(
                () -> assertThatNoException()
                        .isThrownBy(() -> productService.delete(2L)),
                () -> then(productDao).should(inOrder).findAll(),
                () -> then(productDao).should(inOrder).delete(anyLong())
        );
    }
    
    @Test
    void 상품을_수정할_시_존재하지_않는_product_id를_전달하면_예외가_발생한다() {
        // expect
        assertAll(
                () -> assertThatIllegalArgumentException()
                        .isThrownBy(() -> productService.update(3L, null))
                        .withMessage("[ERROR] 존재하지 않는 product id 입니다."),
                () -> then(productDao).should(never()).update(any())
        );
    }
    
    @Test
    void 상품을_삭제할_시_존재하지_않는_product_id를_전달하면_예외가_발생한다() {
        // expect
        assertAll(
                () -> assertThatIllegalArgumentException()
                        .isThrownBy(() -> productService.delete(3L))
                        .withMessage("[ERROR] 존재하지 않는 product id 입니다."),
                () -> then(productDao).should(never()).delete(anyLong())
        );
    }
}
