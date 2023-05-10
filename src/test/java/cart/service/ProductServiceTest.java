package cart.service;

import cart.dao.ProductDao;
import cart.dto.UpdateProductRequestDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductDao productDao;

    @InjectMocks
    private ProductService productService;

    @DisplayName("update시 올바르지 않은 정보가 전달되면 예외를 반환한다.")
    @Test
    void updateProductFailTest() {
        when(productDao.update(any())).thenReturn(0);

        Assertions.assertThatThrownBy(() -> productService.updateProduct(new UpdateProductRequestDto(1, "image", "name", 1000)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("delete시 올바르지 않은 정보가 전달되면 예외를 반환한다.")
    @Test
    void deleteProductFailTest() {
        when(productDao.delete(anyInt())).thenReturn(0);

        Assertions.assertThatThrownBy(() -> productService.deleteProduct(1))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
