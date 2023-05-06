package cart.service;

import cart.dto.ProductDto;
import cart.exception.customexceptions.DataNotFoundException;
import cart.repository.dao.productDao.ProductDao;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@ExtendWith(MockitoExtension.class)
class ProductManagementServiceTest {

    @Mock
    private ProductDao productDao;

    @InjectMocks
    private ProductManagementService productManagementService;

    @Test
    void 업데이트시_해당_상품이_없는경우_예외를_던진다() {
        Long id = 1L;
        String name = "hardy";
        String imageUrl = "https://naver.png";
        int price = 4000;
        ProductDto productDto = new ProductDto(id, name, imageUrl, price);

        when(productDao.update(any()))
                .thenReturn(0);

        assertThatThrownBy(() -> productManagementService.updateProduct(productDto))
                .isInstanceOf(DataNotFoundException.class)
                .hasMessage("해당 상품을 찾을 수 없습니다.");
    }

    @Test
    void 삭제시_해당_상품이_없는경우_예외를_던진다() {
        when(productDao.deleteById(any()))
                .thenReturn(0);

        assertThatThrownBy(() -> productManagementService.deleteProduct(100L))
                .isInstanceOf(DataNotFoundException.class)
                .hasMessage("해당 상품을 찾을 수 없습니다.");
    }
}
