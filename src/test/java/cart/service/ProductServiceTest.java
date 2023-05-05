package cart.service;

import cart.dto.request.ProductCreateDto;
import cart.excpetion.ProductionServiceException;
import cart.repository.ProductRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    ProductRepository productRepository;

    @InjectMocks
    ProductService productService;

    @Test
    @DisplayName("product 생성 테스트")
    void create() {
        //given
        doNothing().when(productRepository).create(any());
        final ProductCreateDto request = new ProductCreateDto("name", "image.jpg", 1000);

        //when
        productService.create(request);

        //then
        verify(productRepository, times(1))
                .create(any());
    }

    @Test
    @DisplayName("product 생성 시 null 로 인한 예외가 발생한다")
    void invalid_create() {
        //given
        final ProductCreateDto request = new ProductCreateDto(null, "image.jpg", 1000);

        //expect
        Assertions.assertThatThrownBy(() -> productService.create(request))
                .isInstanceOf(ProductionServiceException.class);
    }
}
