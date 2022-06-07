package woowacourse.shoppingcart.application;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.domain.ImageUrl;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.domain.ProductName;
import woowacourse.shoppingcart.dto.ProductResponse;
import woowacourse.shoppingcart.dto.ProductsResponse;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

class ProductServiceTest {

    public static final Product 상품1 = new Product(1L, new ProductName("상품1"), 1_000, new ImageUrl("imageUrl1"));
    public static final Product 상품2 = new Product(2L, new ProductName("상품2"), 2_000, new ImageUrl("imageUrl2"));

    private final ProductService productService;

    @Mock
    private ProductDao productDao;

    ProductServiceTest() {
        MockitoAnnotations.openMocks(this);
        this.productService = new ProductService(productDao);
    }

    @DisplayName("id로 상품을 찾는다.")
    @Test
    void findProductById() {
        // given
        given(productDao.findById(1L))
                .willReturn(Optional.of(상품1));
        // when
        final ProductResponse productResponse = productService.findProductById(1L);
        final ProductResponse expected = new ProductResponse(1L, "상품1", 1_000, "imageUrl1");
        // then
        assertThat(productResponse)
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }

    @DisplayName("상품 목록을 조회한다.")
    @Test
    void findAllProducts() {
        // given
        given(productDao.findAll()).willReturn(List.of(상품1, 상품2));
        // when
        final ProductsResponse productsResponse = productService.findProducts();

        // then
        assertThat(productsResponse.getProducts()).hasSize(2);
    }

    @DisplayName("상품을 추가한다.")
    @Test
    void addProduct() {
        // given
        given(productDao.save(any())).willReturn(상품1);
        // when
        final Product expected = new Product(1L, new ProductName("상품1"), 1_000, new ImageUrl("imageUrl1"));
        // then
        assertThat(상품1)
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }

    @DisplayName("상품을 삭제한다.")
    @Test
    void deleteProduct() {
        // given
        given(productDao.delete(1L)).willReturn(1);

        // when
        final int affectedRows = productService.deleteProductById(1L);

        // then
        assertThat(affectedRows).isEqualTo(1);
    }
}
