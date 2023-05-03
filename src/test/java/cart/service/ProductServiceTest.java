package cart.service;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.groups.Tuple.tuple;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.annotation.Transactional;

import cart.dto.ProductDto;
import cart.dto.ProductRequestDto;

@SpringBootTest
@Transactional
class ProductServiceTest {

    private final ProductRequestDto productRequestDto = new ProductRequestDto("케로로", 1000,
        "https://i.namu.wiki/i/fXDC6tkjS6607gZSXSBdzFq_-12PLPWMcmOddg0dsqRq7Nl30Ek1r23BxxOTiERjGP4eyGmJuVPhxhSpOx2GDw.webp");

    @Autowired
    private ProductService productService;

    @DisplayName("addProduct 성공 테스트")
    @Test
    void successAddProduct() {
        assertDoesNotThrow(() -> productService.addProduct(productRequestDto));
    }

    @DisplayName("addProduct 실패 테스트 - 이름 길이 검증")
    @Test
    void addProduct_fail() {
        ProductRequestDto productRequestDto = new ProductRequestDto("케로케로케로케로케로케로케로케로케로케로케로케로케로", 1000,
            "https://i.namu.wiki/i/fXDC6tkjS6607gZSXSBdzFq_-12PLPWMcmOddg0dsqRq7Nl30Ek1r23BxxOTiERjGP4eyGmJuVPhxhSpOx2GDw.webp");
        assertThatThrownBy(() -> productService.addProduct(productRequestDto))
            .isExactlyInstanceOf(DataIntegrityViolationException.class);
    }

    @Test
    void selectAllProducts() {
        ProductRequestDto productRequestDto2 = new ProductRequestDto("쿠루루", 2000,
            "https://i.namu.wiki/i/fXDC6tkjS6607gZSXSBdzFq_-12PLPWMcmOddg0dsqRq7Nl30Ek1r23BxxOTiERjGP4eyGmJuVPhxhSpOx2GDw.webp");
        productService.addProduct(productRequestDto);
        productService.addProduct(productRequestDto2);

        List<ProductDto> productEntities = productService.selectAllProducts();

        assertAll(
            () -> assertThat(productEntities).hasSize(2),
            () -> assertThat(productEntities).extracting("name", "price", "image")
                .contains(tuple(productRequestDto.getName(), productRequestDto.getPrice(), productRequestDto.getImage())
                    , tuple(productRequestDto2.getName(), productRequestDto2.getPrice(), productRequestDto2.getImage()))
        );
    }

    @Test
    void updateProduct() {
        int id = productService.addProduct(productRequestDto);
        ProductRequestDto updateProductDto = new ProductRequestDto("타마마", 100,
            "https://i.namu.wiki/i/fXDC6tkjS6607gZSXSBdzFq_-12PLPWMcmOddg0dsqRq7Nl30Ek1r23BxxOTiERjGP4eyGmJuVPhxhSpOx2GDw.webp");
        productService.updateProduct(updateProductDto, id);

        List<ProductDto> productEntities = productService.selectAllProducts();

        assertThat(productEntities).extracting("name", "price", "image")
            .contains(tuple(updateProductDto.getName(), updateProductDto.getPrice(), updateProductDto.getImage()));
    }

    @Test
    void deleteProduct() {
        int id = productService.addProduct(productRequestDto);
        productService.deleteProduct(id);

        List<ProductDto> productEntities = productService.selectAllProducts();

        assertThat(productEntities).hasSize(0);
    }
}
