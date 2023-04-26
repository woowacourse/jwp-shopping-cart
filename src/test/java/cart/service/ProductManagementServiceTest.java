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
import static org.junit.jupiter.api.Assertions.assertAll;
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
                ProductEntity.of(1L, "chicken", "image", 10_000),
                ProductEntity.of(2L, "pizza", "image2", 20_000)
        );
        when(productDao.selectAll()).thenReturn(data);

        final List<ProductDto> productDtos = managementService.findAll();

        assertAll(
                () -> assertThat(productDtos.size()).isEqualTo(data.size()),
                () -> assertThat(productDtos.get(0).getName()).isEqualTo("chicken"),
                () -> assertThat(productDtos.get(0).getImage()).isEqualTo("image"),
                () -> assertThat(productDtos.get(0).getPrice()).isEqualTo(10_000),
                () -> assertThat(productDtos.get(1).getName()).isEqualTo("pizza"),
                () -> assertThat(productDtos.get(1).getImage()).isEqualTo("image2"),
                () -> assertThat(productDtos.get(1).getPrice()).isEqualTo(20_000)
        );
    }

    @DisplayName("상품 데이터가 등록되는지 확인한다")
    @Test
    void saveTest() {
        final ProductDto productDto = ProductDto.of("pobi_doll", "image", 10_000_000);
        doNothing().when(productDao).insert(any());

        managementService.save(productDto);

        verify(productDao, times(1)).insert(any());
    }

    @DisplayName("상품 데이터가 수정되는지 확인한다")
    @Test
    void updateTest() {
        final Long id = 1L;
        final ProductDto productDto = ProductDto.of(1L, "pobi_doll", "image", 10_000_000);
        doNothing().when(productDao).updateById(any(), any());

        managementService.updateById(id, productDto);

        verify(productDao, times(1)).updateById(any(), any());
    }

    @DisplayName("상품 데이터가 삭제되는지 확인한다")
    @Test
    void deleteTest() {
        final Long id = 1L;
        doNothing().when(productDao).deleteById(any());

        managementService.deleteById(id);

        verify(productDao, times(1)).deleteById(any());
    }
}
