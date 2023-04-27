package cart.service;

import cart.dto.ProductRequestDto;
import cart.dto.ProductResponseDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
class ProductServiceTest {

    private final ProductService productService;

    @Autowired
    public ProductServiceTest(ProductService productService) {
        this.productService = productService;
    }

    @DisplayName("상품이 있을 때 update 할 수 있다.")
    @Test
    void updateProduct() {
        //given
        productService.addProduct(new ProductRequestDto("오션", "이미지", 10000));

        ProductResponseDto productResponseDto = productService.findProducts().get(0);
        Long id = productResponseDto.getId();

        //then
        assertThatNoException().isThrownBy(() -> productService.updateProduct(new ProductRequestDto(id, "연어", "이미지", 100)));
    }

    @DisplayName("상품이 없을 때 update 시 예외가 발생한다.")
    @Test
    void updateProduct_Exception() {
        assertThatThrownBy(() -> productService.updateProduct(new ProductRequestDto()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 상품이 존재하지 않습니다.");
    }

    @DisplayName("상품이 있을 때 삭제할 수 있다.")
    @Test
    void deleteProduct() {
        //given
        productService.addProduct(new ProductRequestDto("오션", "이미지", 10000));

        ProductResponseDto productResponseDto = productService.findProducts().get(0);
        Long id = productResponseDto.getId();

        //then
        assertThatNoException().isThrownBy(() -> productService.deleteProduct(id));
    }

    @DisplayName("상품이 없을 때 삭제 시 예외가 발생한다.")
    @Test
    void deleteProduct_Exception() {
        assertThatThrownBy(() -> productService.deleteProduct(1L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 상품이 존재하지 않습니다.");
    }
}
