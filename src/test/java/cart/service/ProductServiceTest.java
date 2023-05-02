package cart.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.tuple;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import cart.controller.dto.ProductDto;
import cart.exception.ErrorCode;
import cart.exception.GlobalException;
import cart.persistence.dao.ProductDao;
import cart.persistence.entity.ProductEntity;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductDao productDao;

    @InjectMocks
    private ProductService productService;

    @DisplayName("전체 상품을 조회한다.")
    @Test
    void getProducts() {
        // given
        final List<ProductEntity> products = List.of(
            new ProductEntity("치킨", "chicken_image_url", 20000, "KOREAN"),
            new ProductEntity("초밥", "chobab_image_url", 30000, "JAPANESE"),
            new ProductEntity("스테이크", "steak_image_url", 40000, "WESTERN")
        );
        when(productDao.findAll()).thenReturn(products);

        // when
        final List<ProductDto> resultProducts = productService.getProducts();

        // then
        assertThat(resultProducts).hasSize(3);
        assertThat(resultProducts)
            .extracting("name", "imageUrl", "price", "category")
            .containsExactly(tuple("치킨", "chicken_image_url", 20000, "KOREAN"),
                tuple("초밥", "chobab_image_url", 30000, "JAPANESE"),
                tuple("스테이크", "steak_image_url", 40000, "WESTERN"));
    }

    @DisplayName("상품을 저장한다.")
    @Test
    void save() {
        // given
        final ProductDto productDto = new ProductDto(1L, "스테이크", "steak_image_url", 40000,
            "WESTERN");
        final ProductEntity productEntity = new ProductEntity(1L, "스테이크", "steak_image_url", 40000,
            "WESTERN");
        when(productDao.insert(any())).thenReturn(1L);
        when(productDao.findById(1L)).thenReturn(Optional.of(productEntity));

        // when
        final long savedProductId = productService.save(productDto);

        // then
        final ProductDto result = productService.getById(savedProductId);

        assertThat(result)
            .extracting("name", "price", "imageUrl", "category")
            .containsExactly("스테이크", 40000, "steak_image_url", "WESTERN");
    }

    @DisplayName("상품 수정을 정상적으로 진행한다.")
    @Test
    void update_success() {
        // given
        final ProductDto productDto = new ProductDto(1L, "스테이크", "steak_image_url", 40000,
            "WESTERN");
        when(productDao.updateById(any(), any())).thenReturn(1);

        // when, then
        assertDoesNotThrow(() -> productService.update(1L, productDto));
    }

    @ParameterizedTest(name = "상품 수정이 실패하면 예외가 발생한다.")
    @ValueSource(ints = {0, 2})
    void update_fail(final int updatedCount) {
        // given
        final ProductDto productDto = new ProductDto(1L, "스테이크", "steak_image_url", 40000,
            "WESTERN");
        when(productDao.updateById(any(), any())).thenReturn(updatedCount);

        // when, then
        assertThatThrownBy(() -> productService.update(1L, productDto))
            .isInstanceOf(GlobalException.class)
            .extracting("errorCode")
            .isEqualTo(ErrorCode.PRODUCT_INVALID_UPDATE);
    }

    @DisplayName("상품 삭제를 정상적으로 진행한다.")
    @Test
    void delete_success() {
        // given
        when(productDao.deleteById(any())).thenReturn(1);

        // when, then
        assertDoesNotThrow(() -> productService.delete(1L));
    }

    @ParameterizedTest(name = "상품 삭제가 실패하면 예외가 발생한다.")
    @ValueSource(ints = {0, 2})
    void delete_fail(final int deletedCount) {
        // given
        when(productDao.deleteById(any())).thenReturn(deletedCount);

        // when, then
        assertThatThrownBy(() -> productService.delete(1L))
            .isInstanceOf(GlobalException.class)
            .extracting("errorCode")
            .isEqualTo(ErrorCode.PRODUCT_INVALID_DELETE);
    }

    @DisplayName("유효한 상품 아이디가 주어지면 상품을 조회한다.")
    @Test
    void getById_success() {
        // given
        final ProductEntity productEntity = new ProductEntity("스테이크", "steak_image_url", 40000,
            "WESTERN");
        when(productDao.findById(any())).thenReturn(Optional.of(productEntity));

        // when
        final ProductDto productDto = productService.getById(1L);

        // then
        assertThat(productDto)
            .extracting("name", "price", "imageUrl", "category")
            .containsExactly("스테이크", 40000, "steak_image_url", "WESTERN");
    }

    @DisplayName("유효하지 않은 상품 아이디가 주어지면 예외가 발생한다.")
    @Test
    void getById_fail() {
        assertThatThrownBy(() -> productService.getById(1L))
            .isInstanceOf(GlobalException.class)
            .extracting("errorCode")
            .isEqualTo(ErrorCode.PRODUCT_NOT_FOUND);
    }
}
