package woowacourse.shoppingcart.application;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.EmptyResultDataAccessException;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.exception.notfound.ProductNotFoundException;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductDao productDao;

    @Test
    void findProductById() {
        // given
        when(productDao.findProductById(1L))
                .thenThrow(EmptyResultDataAccessException.class);

        // when, then
        assertThatThrownBy(() -> productService.findProductById(1L))
                .isInstanceOf(ProductNotFoundException.class);
    }

    @Test
    void deleteProductById() {
        // given
        when(productDao.delete(1L))
                .thenReturn(0);

        // when, then
        assertThatThrownBy(() -> productService.deleteProductById(1L))
                .isInstanceOf(ProductNotFoundException.class);
    }
}
