package woowacourse.shoppingcart.application;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import woowacourse.shoppingcart.application.dto.ProductSaveServiceRequest;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.domain.Product;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    private static final long PRODUCT_ID = 1L;
    private static final String PRODUCT_NAME = "김치 치즈 볶음밥";
    private static final int PRODUCT_PRICE = 10_000;
    private static final String PRODUCT_IMAGE_URL = "www.naver.com";
    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductDao productDao;

    @Test
    @DisplayName("상품을 추가한다.")
    void addProduct() {
        //given
        final ProductSaveServiceRequest productRequest = new ProductSaveServiceRequest(PRODUCT_NAME, PRODUCT_PRICE,
            PRODUCT_IMAGE_URL);
        when(productDao.save(any(Product.class)))
            .thenReturn(PRODUCT_ID);

        //when
        Long actual = productService.addProduct(productRequest);

        //then
        assertThat(actual).isEqualTo(PRODUCT_ID);
    }
}
