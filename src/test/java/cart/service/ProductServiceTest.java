package cart.service;

import cart.dao.product.JdbcProductDao;
import cart.domain.product.Product;
import cart.dto.ProductResponse;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static cart.DummyData.DUMMY_PRODUCT_ONE;
import static cart.DummyData.INITIAL_PRODUCT_ONE;
import static cart.DummyData.INITIAL_PRODUCT_TWO;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class ProductServiceTest {

    @InjectMocks
    ProductService productService;

    @Mock
    JdbcProductDao productDao;

    @Test
    void 모든_상품_데이터를_가져와서_반환하는지_확인한다() {
        final List<Product> data = List.of(INITIAL_PRODUCT_ONE, INITIAL_PRODUCT_TWO);
        when(productDao.findAll()).thenReturn(data);

        final List<ProductResponse> productResponses = productService.findAll();

        assertAll(
                () -> assertThat(productResponses.size()).isEqualTo(data.size()),
                () -> assertThat(productResponses.get(0).getId()).isEqualTo(INITIAL_PRODUCT_ONE.getId()),
                () -> assertThat(productResponses.get(0).getName()).isEqualTo(INITIAL_PRODUCT_ONE.getName()),
                () -> assertThat(productResponses.get(0).getImage()).isEqualTo(INITIAL_PRODUCT_ONE.getImageUrl()),
                () -> assertThat(productResponses.get(0).getPrice()).isEqualTo(INITIAL_PRODUCT_ONE.getPrice()),
                () -> assertThat(productResponses.get(1).getId()).isEqualTo(INITIAL_PRODUCT_TWO.getId()),
                () -> assertThat(productResponses.get(1).getName()).isEqualTo(INITIAL_PRODUCT_TWO.getName()),
                () -> assertThat(productResponses.get(1).getImage()).isEqualTo(INITIAL_PRODUCT_TWO.getImageUrl()),
                () -> assertThat(productResponses.get(1).getPrice()).isEqualTo(INITIAL_PRODUCT_TWO.getPrice())
        );
    }

    @Test
    void 상품_데이터가_등록되는지_확인한다() {
        doNothing().when(productDao).insert(any());

        productService.add(DUMMY_PRODUCT_ONE);

        verify(productDao, times(1)).insert(any());
    }

    @Test
    void 상품_데이터가_수정되는지_확인한다() {
        final Long id = 1L;
        doNothing().when(productDao).updateById(any(), any());

        productService.updateById(id, DUMMY_PRODUCT_ONE);

        verify(productDao, times(1)).updateById(any(), any());
    }

    @Test
    void 상품_데이터가_삭제되는지_확인한다() {
        final Long id = 1L;
        doNothing().when(productDao).deleteById(any());

        productService.deleteById(id);

        verify(productDao, times(1)).deleteById(any());
    }
}
