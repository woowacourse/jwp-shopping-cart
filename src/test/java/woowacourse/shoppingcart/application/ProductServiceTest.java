package woowacourse.shoppingcart.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.domain.Page;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.dto.PageRequest;
import woowacourse.shoppingcart.dto.ProductResponse;
import woowacourse.shoppingcart.dto.ProductSaveRequest;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    private static final String NAME = "치킨";
    private static final int PRICE = 5000;
    private static final String IMAGE_URL = "www.chicken.com";

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductDao productDao;

    @DisplayName("상품을 등록한다.")
    @Test
    void addProduct() {
        final ProductSaveRequest productSaveRequest =
                new ProductSaveRequest(NAME, PRICE, IMAGE_URL);

        when(productDao.save(any(Product.class)))
                .thenReturn(1L);

        assertThat(productService.addProduct(productSaveRequest)).isOne();
    }

    @DisplayName("ID를 통해 상품을 조회한다.")
    @Test
    void findById() {
        // given
        final long productId = 1L;
        final ProductSaveRequest productSaveRequest =
                new ProductSaveRequest(NAME, PRICE, IMAGE_URL);
        final Product product = new Product(productId, productSaveRequest.getName(),
                productSaveRequest.getPrice(), productSaveRequest.getImageUrl());

        // when
        when(productDao.findProductById(any(Long.class)))
                .thenReturn(Optional.of(product));
        final ProductResponse response = productService.findById(productId);

        // then
        assertThat(response).usingRecursiveComparison()
                .isEqualTo(new ProductResponse(1L, NAME, PRICE, IMAGE_URL));
    }

    @DisplayName("페이지에 해당하는 존재하는 상품을 모두 조회한다.")
    @Test
    void findAllByPage() {
        // given
        final Product product1 = new Product(1L, NAME, PRICE, IMAGE_URL);
        final Product product2 = new Product(2L, "피자", 3000, "www.pizza.com");
        final PageRequest pageServiceRequest = new PageRequest(1, 2);

        // when
        when(productDao.findProducts(any(Page.class)))
                .thenReturn(List.of(product1, product2));

        // then
        assertThat(productService.findAllByPage(pageServiceRequest)).usingRecursiveComparison()
                .isEqualTo(List.of(
                        new ProductResponse(1L, NAME, PRICE, IMAGE_URL),
                        new ProductResponse(2L, "피자", 3000, "www.pizza.com")
                ));

    }

    @DisplayName("상품을 삭제한다.")
    @Test
    void deleteById() {
        // given
        final Product product = new Product(1L, NAME, PRICE, IMAGE_URL);

        // when
        when(productDao.findProductById(any(Long.class)))
                .thenReturn(Optional.of(product));

        productService.deleteById(product.getId());
        // then
        verify(productDao, times(1)).delete(product.getId());
    }
}
