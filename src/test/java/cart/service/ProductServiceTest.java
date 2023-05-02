package cart.service;

import cart.dto.ProductRequestDto;
import cart.repository.ProductDao;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    ProductDao productDao;

    @InjectMocks
    ProductService productService;

    @Test
    @DisplayName("product 생성 테스트")
    void create() {
        //given
        when(productDao.create(any()))
                .thenReturn(1);
        final ProductRequestDto request = new ProductRequestDto("name", "image.jpg", 1000);

        //when
        productService.create(request);

        //then
        verify(productDao, times(1))
                .create(any());
    }

    @Test
    @DisplayName("product 생성 시 null 로 인한 예외가 발생한다")
    void invalid_create() {
        //given
        final ProductRequestDto request = new ProductRequestDto(null, "image.jpg", 1000);

        //expect
        Assertions.assertThatThrownBy(() -> productService.create(request))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
