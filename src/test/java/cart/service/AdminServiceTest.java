package cart.service;

import cart.dto.ProductRequestDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest
@Transactional
class AdminServiceTest {

    private final ProductRequestDto productRequestDto = new ProductRequestDto("케로로", 1000, "https://i.namu.wiki/i/fXDC6tkjS6607gZSXSBdzFq_-12PLPWMcmOddg0dsqRq7Nl30Ek1r23BxxOTiERjGP4eyGmJuVPhxhSpOx2GDw.webp");

    @Autowired
    private AdminService adminService;

    @DisplayName("updateProduct 성공 테스트")
    @Test
    void updateProduct() {
        ProductRequestDto updateProductDto = new ProductRequestDto("타마마", 2000, "https://i.namu.wiki/i/fXDC6tkjS6607gZSXSBdzFq_-12PLPWMcmOddg0dsqRq7Nl30Ek1r23BxxOTiERjGP4eyGmJuVPhxhSpOx2GDw.webp");
        int productId = adminService.addProduct(productRequestDto);

        assertDoesNotThrow(() -> adminService.updateProduct(updateProductDto, productId));
    }

    @DisplayName("updateProduct 실패 테스트")
    @Test
    void failUpdateProduct() {
        ProductRequestDto updateProductDto = new ProductRequestDto("타마마", 2000, "https://i.namu.wiki/i/fXDC6tkjS6607gZSXSBdzFq_-12PLPWMcmOddg0dsqRq7Nl30Ek1r23BxxOTiERjGP4eyGmJuVPhxhSpOx2GDw.webp");
        int productId = adminService.addProduct(productRequestDto);

        assertThatThrownBy(() -> adminService.updateProduct(productRequestDto, productId + 1))
                .hasMessage("수정하려는 제품이 존재하지 않습니다.");
    }

    @DisplayName("deleteProduct 성공 테스트")
    @Test
    void deleteProduct() {
        int productId = adminService.addProduct(productRequestDto);
        assertDoesNotThrow(() -> adminService.deleteProduct(productId));
    }

    @DisplayName("deleteProduct 실패 테스트")
    @Test
    void failDeleteProduct() {
        int productId = adminService.addProduct(productRequestDto);
        assertThatThrownBy(() -> adminService.deleteProduct(productId + 1))
                .hasMessage("삭제하려는 제품이 존재하지 않습니다.");
    }

}