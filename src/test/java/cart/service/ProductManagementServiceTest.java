package cart.service;

import cart.dao.JdbcProductDao;
import cart.dto.ProductDto;
import cart.entity.ProductEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductManagementServiceTest {

    @InjectMocks
    ProductManagementService managementService;

    @Mock
    JdbcProductDao productDao;

    @DisplayName("모든 상품 데이터를 가져와서 반환하는지 확인한다")
    @Test
    void findAllTest() {
        final List<ProductEntity> data = List.of(
                ProductEntity.of(1L, "chicken", "https://cdn.polinews.co.kr/news/photo/201910/427334_3.jpg", 10000),
                ProductEntity.of(2L, "pizza", "https://cdn.polinews.co.kr/news/photo/201910/427334_3.jpg", 20000)
        );
        when(productDao.selectAll()).thenReturn(data);

        final List<ProductDto> productDtos = managementService.findAll();

        assertAll(
                () -> assertThat(productDtos.size()).isEqualTo(data.size()),
                () -> assertThat(productDtos.get(0).getName()).isEqualTo("chicken"),
                () -> assertThat(productDtos.get(0).getImage()).isEqualTo("https://cdn.polinews.co.kr/news/photo/201910/427334_3.jpg"),
                () -> assertThat(productDtos.get(0).getPrice()).isEqualTo(10000),
                () -> assertThat(productDtos.get(1).getName()).isEqualTo("pizza"),
                () -> assertThat(productDtos.get(1).getImage()).isEqualTo("https://cdn.polinews.co.kr/news/photo/201910/427334_3.jpg"),
                () -> assertThat(productDtos.get(1).getPrice()).isEqualTo(20000)
        );
    }

    @DisplayName("상품 데이터가 등록되는지 확인한다")
    @Test
    void saveTest() {
        final ProductDto productDto = ProductDto.of("pobi_doll", "https://cdn.polinews.co.kr/news/photo/201910/427334_3.jpg", 10000000);
        when(productDao.insert(any())).thenReturn(1L);
        managementService.save(productDto);

        assertDoesNotThrow(()->managementService.save(productDto));
    }

    @DisplayName("상품 데이터가 수정되는지 확인한다")
    @Test
    void updateTest() {
        final ProductDto productDto = ProductDto.of(1L, "pobi_doll", "https://cdn.polinews.co.kr/news/photo/201910/427334_3.jpg", 10000000);
        when(productDao.update(any())).thenReturn(1);

        assertDoesNotThrow(()->managementService.update(productDto));
    }

    @DisplayName("상품 데이터가 수정되지 않으면 예외가 발생하는지 확인한다")
    @Test
    void invalidUpdateTest() {
        final ProductDto productDto = ProductDto.of(3L, "pobi_doll", "https://cdn.polinews.co.kr/news/photo/201910/427334_3.jpg", 10000000);
        when(productDao.update(any())).thenReturn(0);

        assertThatThrownBy(()->managementService.update(productDto))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("상품 데이터가 삭제되는지 확인한다")
    @Test
    void deleteTest() {
        final ProductDto productDto = ProductDto.of(1L, null, null, null);
        when(productDao.delete(any())).thenReturn(1);

        assertDoesNotThrow(()->managementService.delete(productDto));
    }

    @DisplayName("상품 데이터가 삭제되지 않으면 예외가 발생하는지 확인한다")
    @Test
    void invalidDeleteTest() {
        final ProductDto productDto = ProductDto.of(3L, "pobi_doll", "https://cdn.polinews.co.kr/news/photo/201910/427334_3.jpg", 10000000);
        when(productDao.update(any())).thenReturn(0);

        assertThatThrownBy(()->managementService.update(productDto))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
