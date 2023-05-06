package cart.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.BDDMockito.willReturn;

import cart.dao.ProductDao;
import cart.dto.ProductDto;
import cart.entity.ProductEntity;
import cart.exception.ProductNotFoundException;
import java.util.List;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@WebMvcTest(ProductService.class)
class ProductServiceTest {

    private static final String NAME = "킨더조이";
    private static final String IMAGE_URL = "이미지경로";
    private static final int PRICE = 1_000;
    private static final long ID = 1L;
    public static final ProductEntity PRODUCT_ENTITY = ProductEntity.builder()
            .id(ID)
            .name(NAME)
            .price(PRICE)
            .imageUrl(IMAGE_URL)
            .build();

    @MockBean
    ProductDao productDao;

    @Autowired
    ProductService productService;

    @Test
    @DisplayName("상품을 등록한다.")
    void createProduct_success() {
        // given
        willReturn(PRODUCT_ENTITY).given(productDao).save(any());

        // when
        ProductDto productDto = productService.createProduct(NAME, PRICE, IMAGE_URL);

        // then
        assertAll(
                () -> assertThat(productDto.getId()).isEqualTo(ID),
                () -> assertThat(productDto.getName()).isEqualTo(NAME),
                () -> assertThat(productDto.getPrice()).isEqualTo(PRICE),
                () -> assertThat(productDto.getImageUrl()).isEqualTo(IMAGE_URL)
        );
    }

    @Test
    @DisplayName("모든 상품을 조회한다.")
    void findAllProducts_success() {
        // given
        List<ProductEntity> productEntities = List.of(PRODUCT_ENTITY);
        willReturn(productEntities).given(productDao).findAll();

        // when
        List<ProductDto> allProducts = productService.findAllProducts();
        ProductDto product = allProducts.get(0);

        // then
        assertAll(
                () -> assertThat(allProducts).hasSize(1),
                () -> assertThat(product.getId()).isEqualTo(ID),
                () -> assertThat(product.getName()).isEqualTo(NAME),
                () -> assertThat(product.getPrice()).isEqualTo(PRICE),
                () -> assertThat(product.getImageUrl()).isEqualTo(IMAGE_URL)
        );
    }

    @Test
    @DisplayName("id를 통해 상품을 삭제한다.")
    void deleteById_success() {
        // given
        willReturn(true).given(productDao).existsById(anyLong());
        willDoNothing().given(productDao).deleteById(anyLong());

        // when, then
        assertDoesNotThrow(
                () -> productService.deleteById(ID)
        );
    }

    @Test
    @DisplayName("존재하지 않는 상품을 삭제하면 예외가 발생한다.")
    void deleteById_productNotFound() {
        // given
        willReturn(false).given(productDao).existsById(anyLong());
        willDoNothing().given(productDao).deleteById(anyLong());

        // when, then
        assertThatThrownBy(() -> productService.deleteById(ID))
                .isInstanceOf(ProductNotFoundException.class)
                .hasMessage("존재하지 않는 상품의 ID 입니다.");
    }

    @Test
    @DisplayName("id를 통해 상품을 수정한다.")
    void updateProductById_success() {
        // given
        willReturn(true).given(productDao).existsById(anyLong());
        willDoNothing().given(productDao).update(any());

        // when
        ProductDto productDto = productService.updateProductById(ID, NAME, PRICE, IMAGE_URL);

        // then
        assertAll(
                () -> AssertionsForClassTypes.assertThat(productDto.getId()).isEqualTo(ID),
                () -> AssertionsForClassTypes.assertThat(productDto.getName()).isEqualTo(NAME),
                () -> AssertionsForClassTypes.assertThat(productDto.getPrice()).isEqualTo(PRICE),
                () -> AssertionsForClassTypes.assertThat(productDto.getImageUrl()).isEqualTo(IMAGE_URL)
        );
    }

    @Test
    @DisplayName("존재하지 않는 상품을 수정하면 예외가 발생한다.")
    void updateProductById_productNotFound() {
        // given
        willReturn(false).given(productDao).existsById(anyLong());
        willDoNothing().given(productDao).update(any());

        // when, then
        assertThatThrownBy(() -> productService.updateProductById(ID, NAME, PRICE, IMAGE_URL))
                .isInstanceOf(ProductNotFoundException.class)
                .hasMessage("존재하지 않는 상품의 ID 입니다.");
    }
}
