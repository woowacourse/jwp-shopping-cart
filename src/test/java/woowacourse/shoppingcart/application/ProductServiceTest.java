package woowacourse.shoppingcart.application;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;
import static woowacourse.utils.Fixture.치킨;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.domain.product.Product;
import woowacourse.shoppingcart.dto.ProductSaveRequest;
import woowacourse.shoppingcart.dto.ProductSaveResponse;

@ExtendWith({MockitoExtension.class})
class ProductServiceTest {

    @Mock
    private ProductDao productDao;
    @InjectMocks
    private ProductService productService;

    @Test
    @DisplayName("상품을 추가한다.")
    void test() {
        // given
        ProductSaveRequest request = new ProductSaveRequest(치킨.getName(), 치킨.getPrice(), 치킨.getImage());
        Product saved = new Product(1L, 치킨.getName(), 치킨.getPrice(), 치킨.getImage());
        given(productDao.save(any(Product.class))).willReturn(saved);

        // when
        ProductSaveResponse productResponse = productService.addProduct(request);

        // then
        assertAll(
                () -> assertThat(productResponse.getName()).isEqualTo(치킨.getName()),
                () -> assertThat(productResponse.getPrice()).isEqualTo(치킨.getPrice()),
                () -> assertThat(productResponse.getImage()).isEqualTo(치킨.getImage())
        );
    }
}
