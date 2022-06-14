package woowacourse.shoppingcart.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static woowacourse.TestFixture.product1;
import static woowacourse.TestFixture.product2;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.exception.nobodyexception.NotFoundProductException;

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
        given(productDao.findProducts())
                .willReturn(List.of(product1, product2));

        // when
        List<Product> products = productService.findProducts();

        // then
        assertThat(products).containsExactly(product1, product2);
    }

    @Test
    @DisplayName("존재하는 Product 1개를 조회한다.")
    void findProduct_exist_returnProduct() {
        // given
        given(productDao.findProductById(1L))
                .willReturn(product1);

        // when
        Product actual = productService.findProductById(1L);

        // then
        assertThat(actual).isEqualTo(product1);
    }

    @Test
    @DisplayName("존재하지 않는 Product를 조회하면 예외가 발생한다.")
    void findProduct_notExist_exceptionThrown() {
        // given
        given(productDao.findProductById(1L))
                .willThrow(new NotFoundProductException());

        // when, then
        assertThatThrownBy(() -> productService.findProductById(1L))
                .isInstanceOf(NotFoundProductException.class);
    }
}
