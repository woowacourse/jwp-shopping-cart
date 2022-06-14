package woowacourse.shoppingcart.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static woowacourse.utils.Fixture.맥주;
import static woowacourse.utils.Fixture.치킨;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.domain.product.Product;
import woowacourse.shoppingcart.dto.product.ProductFindResponse;
import woowacourse.shoppingcart.dto.product.ProductSaveRequest;
import woowacourse.shoppingcart.dto.product.ProductSaveResponse;
import woowacourse.shoppingcart.exception.ProductNotFoundException;

@ExtendWith({MockitoExtension.class})
class ProductServiceTest {

    @Mock
    private ProductDao productDao;
    @InjectMocks
    private ProductService productService;

    @Test
    @DisplayName("상품을 추가한다.")
    void add_product() {
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

    @Test
    @DisplayName("상품을 있는 경우 상품을 조회해서 반환한다.")
    void find_product_exist() {
        // given
        Product saved = new Product(1L, 치킨.getName(), 치킨.getPrice(), 치킨.getImage());
        given(productDao.findProductById(any(Long.class))).willReturn(Optional.of(saved));

        // when
        ProductFindResponse findResponse = productService.findProductById(1L);

        // then
        assertAll(
                () -> assertThat(findResponse.getName()).isEqualTo(치킨.getName()),
                () -> assertThat(findResponse.getPrice()).isEqualTo(치킨.getPrice()),
                () -> assertThat(findResponse.getImage()).isEqualTo(치킨.getImage())
        );
    }

    @Test
    @DisplayName("상품이 없는 경우 예외를 반환한다.")
    void find_product_not_exist() {
        // given
        Product saved = new Product(1L, 치킨.getName(), 치킨.getPrice(), 치킨.getImage());
        given(productDao.findProductById(any(Long.class))).willReturn(Optional.empty());

        // when
        assertThatThrownBy(() -> productService.findProductById(1L))
                .isInstanceOf(ProductNotFoundException.class);
    }

    @Test
    @DisplayName("상품들의 목록을 반환한다.")
    void find_products() {
        // given
        List<Product> saves = List.of(치킨, 맥주);
        given(productDao.findProducts()).willReturn(saves);

        // when
        assertThat(productService.findProducts()).hasSize(2)
                .extracting("name")
                .containsExactly(치킨.getName(), 맥주.getName());
    }
}
