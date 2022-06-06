package woowacourse.shoppingcart.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.domain.Product;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductDao productDao;

    @InjectMocks
    private ProductService productService;

    @Test
    @DisplayName("전체 Product들을 조회한다.")
    void findProducts_right_productsReturned() {
        // given
        Product product1 = new Product(1L, "product1", 1000, "url1");
        Product product2 = new Product(2L, "product1", 2000, "url2");

        given(productDao.findProducts())
                .willReturn(List.of(product1, product2));

        // when
        List<Product> products = productService.findProducts();

        // then
        assertThat(products).containsExactly(product1, product2);
    }
}
