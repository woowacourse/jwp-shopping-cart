package woowacourse.shoppingcart.application;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.dto.ProductResponse;
import woowacourse.shoppingcart.dto.ProductsResponse;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

class ProductServiceTest {

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
                .willReturn(Optional.of(new Product(1L, "상품명", 1000, "imageUrl")));
        // when
        final ProductResponse productResponse = productService.findProductById(1L);
        final ProductResponse expected = new ProductResponse(1L, "상품명", 1000, "imageUrl");
        // then
        assertThat(productResponse)
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }

    @DisplayName("상품 목록을 조회한다.")
    @Test
    void findAllProducts() {
        // given
        given(productDao.findAll())
                .willReturn(
                        List.of(
                                new Product(1L, "상품명1", 1000, "imageUrl1"),
                                new Product(2L, "상품명2", 2000, "imageUrl2")
                        )
                );
        // when
        final ProductsResponse productsResponse = productService.findProducts();

        // then
        assertThat(productsResponse.getProducts()).hasSize(2);
    }

    @DisplayName("상품을 추가한다.")
    @Test
    void addProduct() {
        // given
        final Product product = new Product(1L, "상품명", 1000, "imageUrl");
        given(productDao.save(any()))
                .willReturn(product);
        // when
        final Product expected = new Product(1L, "상품명", 1000, "imageUrl");
        // then
        assertThat(product)
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
