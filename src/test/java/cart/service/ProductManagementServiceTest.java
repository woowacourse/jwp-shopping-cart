package cart.service;

import cart.dao.product.JdbcProductDao;
import cart.dto.ProductResponse;
import cart.domain.product.ProductEntity;
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
                ProductEntity.of(1L, "chicken", "https://image", 10_000),
                ProductEntity.of(2L, "pizza", "https://image2", 20_000)
        );
        when(productDao.selectAll()).thenReturn(data);

        final List<ProductResponse> productResponses = managementService.findAll();

        assertAll(
                () -> assertThat(productResponses.size()).isEqualTo(data.size()),
                () -> assertThat(productResponses.get(0).getName()).isEqualTo("chicken"),
                () -> assertThat(productResponses.get(0).getImage()).isEqualTo("https://image"),
                () -> assertThat(productResponses.get(0).getPrice()).isEqualTo(10_000),
                () -> assertThat(productResponses.get(1).getName()).isEqualTo("pizza"),
                () -> assertThat(productResponses.get(1).getImage()).isEqualTo("https://image2"),
                () -> assertThat(productResponses.get(1).getPrice()).isEqualTo(20_000)
        );
    }

    @DisplayName("상품 데이터가 등록되는지 확인한다")
    @Test
    void saveTest() {
        final ProductEntity productEntity = ProductEntity.of("pobi_doll", "https://image", 10_000_000);
        doNothing().when(productDao).insert(any());

        managementService.add(productEntity);

        verify(productDao, times(1)).insert(any());
    }

    @DisplayName("상품 데이터가 수정되는지 확인한다")
    @Test
    void updateTest() {
        final Long id = 1L;
        final ProductEntity productEntity = ProductEntity.of(1L, "pobi_doll", "https://image", 10_000_000);
        doNothing().when(productDao).updateById(any(), any());

        managementService.updateById(id, productEntity);

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
