package woowacourse.shoppingcart.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import woowacourse.shoppingcart.application.dto.ProductDetailServiceResponse;
import woowacourse.shoppingcart.application.dto.ProductSaveServiceRequest;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.exception.NotFoundProductException;

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
    void save() {
        //given
        final ProductSaveServiceRequest productRequest = new ProductSaveServiceRequest(PRODUCT_NAME, PRODUCT_PRICE,
                PRODUCT_IMAGE_URL);
        when(productDao.save(any(Product.class)))
                .thenReturn(PRODUCT_ID);

        //when
        final Long actual = productService.save(productRequest);

        //then
        assertThat(actual).isEqualTo(PRODUCT_ID);
    }

    @Test
    @DisplayName("id 에 해당하는 상품을 조회한다.")
    void findProductById() {
        //given
        final Long productId = saveProduct();
        final Product savedProduct = new Product(productId, PRODUCT_NAME, PRODUCT_PRICE, PRODUCT_IMAGE_URL);
        when(productDao.findProductById(productId))
                .thenReturn(Optional.of(savedProduct));

        //when
        final ProductDetailServiceResponse actual = productService.findProductById(productId);

        //then
        assertThat(actual).usingRecursiveComparison()
                .isEqualTo(
                        new ProductDetailServiceResponse(PRODUCT_ID, PRODUCT_NAME, PRODUCT_PRICE, PRODUCT_IMAGE_URL));
    }

    @Test
    @DisplayName("상품 조회시 id 에 해당하는 상품이 없을 경우 예외를 던진다.")
    void findProductById_invalidProductId_throwsException() {
        //when, then
        assertThatThrownBy(() -> productService.findProductById(300L))
                .isInstanceOf(NotFoundProductException.class);
    }

    @Test
    @DisplayName("id 에 해당하는 상품을 삭제한다.")
    void deleteProductById() {
        //given
        final Long productId = saveProduct();
        when(productDao.delete(productId))
                .thenReturn(1);

        //when, then
        assertThatCode(() -> productService.deleteProductById(productId))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("상품 삭제시 id 에 해당하는 상품이 없을 경우 예외를 던진다.")
    void deleteProductById_invalidProductId_throwsException() {
        //when, then
        assertThatThrownBy(() -> productService.deleteProductById(300L))
                .isInstanceOf(NotFoundProductException.class);
    }

    private Long saveProduct() {
        final ProductSaveServiceRequest productRequest = new ProductSaveServiceRequest(PRODUCT_NAME, PRODUCT_PRICE,
                PRODUCT_IMAGE_URL);
        when(productDao.save(any(Product.class)))
                .thenReturn(PRODUCT_ID);

        return productService.save(productRequest);
    }
}
