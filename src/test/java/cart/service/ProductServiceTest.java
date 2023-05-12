package cart.service;

import cart.dto.ProductRequest;
import cart.dto.ProductResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.groups.Tuple.tuple;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest
@Transactional
class ProductServiceTest {

    private final ProductRequest productRequest = new ProductRequest("케로로", 1000,
        "https://i.namu.wiki/i/fXDC6tkjS6607gZSXSBdzFq_-12PLPWMcmOddg0dsqRq7Nl30Ek1r23BxxOTiERjGP4eyGmJuVPhxhSpOx2GDw.webp");

    @Autowired
    private ProductService productService;

    @DisplayName("addProduct 성공 테스트")
    @Test
    void successAddProduct() {
        assertDoesNotThrow(() -> productService.addProduct(productRequest));
    }

    @DisplayName("addProduct 실패 테스트 - 이름 길이 검증")
    @Test
    void addProduct_fail() {
        ProductRequest productRequest = new ProductRequest("케로케로케로케로케로케로케로케로케로케로케로케로케로", 1000,
            "https://i.namu.wiki/i/fXDC6tkjS6607gZSXSBdzFq_-12PLPWMcmOddg0dsqRq7Nl30Ek1r23BxxOTiERjGP4eyGmJuVPhxhSpOx2GDw.webp");
        assertThatThrownBy(() -> productService.addProduct(productRequest))
            .isExactlyInstanceOf(DataIntegrityViolationException.class);
    }

    @Test
    void selectAllProducts() {
        ProductRequest productRequest2 = new ProductRequest("쿠루루", 2000,
            "https://i.namu.wiki/i/fXDC6tkjS6607gZSXSBdzFq_-12PLPWMcmOddg0dsqRq7Nl30Ek1r23BxxOTiERjGP4eyGmJuVPhxhSpOx2GDw.webp");
        productService.addProduct(productRequest);
        productService.addProduct(productRequest2);

        List<ProductResponse> productEntities = productService.selectAllProducts();

        assertAll(
            () -> assertThat(productEntities).hasSize(2),
            () -> assertThat(productEntities).extracting("name", "price", "image")
                .contains(tuple(productRequest.getName(), productRequest.getPrice(), productRequest.getImage())
                    , tuple(productRequest2.getName(), productRequest2.getPrice(), productRequest2.getImage()))
        );
    }

    @Test
    void updateProduct() {
        int id = productService.addProduct(productRequest);
        ProductRequest updateProductDto = new ProductRequest("타마마", 100,
            "https://i.namu.wiki/i/fXDC6tkjS6607gZSXSBdzFq_-12PLPWMcmOddg0dsqRq7Nl30Ek1r23BxxOTiERjGP4eyGmJuVPhxhSpOx2GDw.webp");
        productService.updateProduct(updateProductDto, id);

        List<ProductResponse> productEntities = productService.selectAllProducts();

        assertThat(productEntities).extracting("name", "price", "image")
            .contains(tuple(updateProductDto.getName(), updateProductDto.getPrice(), updateProductDto.getImage()));
    }

    @Test
    void deleteProduct() {
        int id = productService.addProduct(productRequest);
        productService.deleteProduct(id);

        List<ProductResponse> productEntities = productService.selectAllProducts();

        assertThat(productEntities).hasSize(0);
    }
}
