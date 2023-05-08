package cart.service;

import cart.dao.ProductDao;
import cart.domain.product.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static cart.service.ProductService.PRODUCT_ID_NOT_EXIST_ERROR_MESSAGE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {
    private static final Product testProduct = new Product("이오", 1, null);

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductDao productDao;

    @Test
    @DisplayName("상품을 저장한다")
    void save() {
        given(productDao.insert(any())).willReturn(1L);

        assertThat(productService.save("이오", 1, null)).isEqualTo(1L);
        verify(productDao, times(1)).insert(any());
    }


    @Test
    @DisplayName("Id로 상품을 조회한다")
    void findById() {
        given(productDao.findById(anyLong())).willReturn(Optional.of(testProduct));

        final Product actual = productService.findById(1L);

        assertThat(actual.getName()).isEqualTo("이오");
        verify(productDao, times(1)).findById(anyLong());
    }

    @Test
    @DisplayName("상품 리스트를 조회한다")
    void findAll() {
        given(productDao.findAll()).willReturn(List.of(testProduct));

        final List<Product> actual = productService.findAll();

        assertAll(
                () -> assertThat(actual.size()).isEqualTo(1),
                () -> assertThat(actual.get(0).getName()).isEqualTo("이오")
        );
        verify(productDao, times(1)).findAll();
    }

    @Test
    @DisplayName("상품을 갱신시 id가 유효하지 않으면 예외 발생")
    void updateInvalidId() {
        given(productDao.isExist(anyLong())).willReturn(false);

        assertThatThrownBy(() -> productService.update(-1L, "애쉬", 1000, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(PRODUCT_ID_NOT_EXIST_ERROR_MESSAGE);
        verify(productDao, never()).update(any());
    }

    @Test
    @DisplayName("상품을 갱신한다")
    void update() {
        given(productDao.isExist(anyLong())).willReturn(true);
        doNothing().when(productDao).update(any());

        assertThat(productService.update(1L, "카프카", 9999, null)).isEqualTo(1L);
        verify(productDao, times(1)).update(any());
    }

    @Test
    @DisplayName("Id로 상품 존재여부를 검증한다")
    void validateExistProductId() {
        given(productDao.isExist(1L)).willReturn(true);
        given(productDao.isExist(-1L)).willReturn(false);

        assertAll(
                () -> assertDoesNotThrow(() -> productService.validateProductIdExist(1L)),
                () -> assertThatThrownBy(() -> productService.validateProductIdExist(-1L))
                        .isInstanceOf(IllegalArgumentException.class)
                        .hasMessage(PRODUCT_ID_NOT_EXIST_ERROR_MESSAGE)
        );
        verify(productDao, times(2)).isExist(anyLong());
    }

    @Test
    @DisplayName("상품을 삭제한다")
    void delete() {
        given(productDao.isExist(anyLong())).willReturn(true);
        doNothing().when(productDao).deleteById(anyLong());

        assertDoesNotThrow(() -> productService.delete(1L));
        verify(productDao, times(1)).deleteById(anyLong());
    }
}
