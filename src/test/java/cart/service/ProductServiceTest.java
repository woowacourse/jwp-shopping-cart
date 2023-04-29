package cart.service;

import cart.dto.ProductResponseDto;
import cart.dto.ProductSaveRequestDto;
import cart.dto.ProductUpdateRequestDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest
@Transactional
@AutoConfigureTestDatabase
class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @DisplayName("상품을 추가할 수 있다.")
    @Test
    void addProduct() {
        //given
        ProductSaveRequestDto productSaveRequestDto = new ProductSaveRequestDto("ocean", "image", 1000);

        //when & then
        assertThatNoException().isThrownBy(() -> productService.addProduct(productSaveRequestDto));
    }

    @DisplayName("상품을 찾을 수 있다.")
    @Test
    void findProducts() {
        //given
        ProductSaveRequestDto productSaveRequestDto = new ProductSaveRequestDto("ocean", "image", 1000);

        //when
        productService.addProduct(productSaveRequestDto);

        //then
        ProductResponseDto productResponseDto = productService.findProducts().get(0);
        assertAll(
                () -> assertThat(productResponseDto.getName()).isEqualTo(productSaveRequestDto.getName()),
                () -> assertThat(productResponseDto.getImage()).isEqualTo(productSaveRequestDto.getImage()),
                () -> assertThat(productResponseDto.getPrice()).isEqualTo(productSaveRequestDto.getPrice())
        );
    }

    @DisplayName("상품이 있을 때 update 할 수 있다.")
    @Test
    void updateProduct() {
        //given
        productService.addProduct(new ProductSaveRequestDto("오션", "이미지", 10000));
        ProductResponseDto product = productService.findProducts().get(0);
        //then
        assertThatNoException().isThrownBy(() -> productService.updateProduct(1L, new ProductUpdateRequestDto(product.getId(), "연어", "이미지", 100)));
    }

    @DisplayName("상품이 없을 때 update 시 예외가 발생한다.")
    @Test
    void updateProduct_Exception() {
        assertThatThrownBy(() -> productService.updateProduct(null, new ProductUpdateRequestDto()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 상품이 존재하지 않습니다.");
    }

    @DisplayName("상품이 있을 때 삭제할 수 있다.")
    @Test
    void deleteProduct() {
        //given
        productService.addProduct(new ProductSaveRequestDto("오션", "이미지", 10000));
        ProductResponseDto product = productService.findProducts().get(0);

        //then
        assertThatNoException().isThrownBy(() -> productService.deleteProduct(product.getId()));
    }

    @DisplayName("상품이 없을 때 삭제 시 예외가 발생한다.")
    @Test
    void deleteProduct_Exception() {
        assertThatThrownBy(() -> productService.deleteProduct(1L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 상품이 존재하지 않습니다.");
    }
}
