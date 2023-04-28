package cart.service;

import cart.controller.dto.ProductDto;
import cart.exception.GlobalException;
import cart.persistence.dao.ProductDao;
import cart.persistence.entity.Product;
import cart.persistence.entity.ProductCategory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.tuple;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebMvcTest(ProductService.class)
class ProductServiceTest {

    @MockBean
    private ProductDao productDao;

    @Autowired
    private ProductService productService;

    @DisplayName("전체 상품을 조회한다")
    @Test
    void getProducts() {
        // given
        final List<Product> products = List.of(
                new Product("치킨", "chicken_image_url", 20000, ProductCategory.KOREAN),
                new Product("초밥", "chobab_image_url", 30000, ProductCategory.JAPANESE),
                new Product("스테이크", "steak_image_url", 40000, ProductCategory.WESTERN)
        );
        when(productDao.findAll()).thenReturn(products);

        // when
        final List<ProductDto> resultProducts = productService.getProducts();

        // then
        assertThat(resultProducts).hasSize(3);
        assertThat(resultProducts)
                .extracting("name", "imageUrl", "price", "category")
                .contains(tuple("치킨", "chicken_image_url", 20000, ProductCategory.KOREAN),
                        tuple("초밥", "chobab_image_url", 30000, ProductCategory.JAPANESE),
                        tuple("스테이크", "steak_image_url", 40000, ProductCategory.WESTERN));
    }

    @DisplayName("상품을 저장한다")
    @Test
    void save() {
        // given
        final List<Product> products = List.of(new Product("스테이크", "steak_image_url", 40000, ProductCategory.WESTERN));
        final ProductDto productDto = new ProductDto(1L, "스테이크", "steak_image_url", 40000, ProductCategory.WESTERN);
        when(productDao.insert(any())).thenReturn(1L);
        when(productDao.findAll()).thenReturn(products);

        // when
        productService.save(productDto);

        // then
        final List<ProductDto> resultProducts = productService.getProducts();

        assertThat(resultProducts).hasSize(1);
        assertThat(resultProducts)
                .extracting("name", "price", "imageUrl", "category")
                .contains(tuple("스테이크", "steak_image_url", 40000, ProductCategory.WESTERN));
    }

    @DisplayName("상품 수정을 정상적으로 진행한다")
    @Test
    void update_success() {
        // given
        final ProductDto productDto = new ProductDto(1L, "스테이크", "steak_image_url", 40000, ProductCategory.WESTERN);
        when(productDao.updateById(any(), any())).thenReturn(1);

        // when, then
        assertDoesNotThrow(() -> productService.update(1L, productDto));
    }

    @DisplayName("상품 수정이 실패하면 예외가 발생한다")
    @Test
    void update_fail() {
        // given
        final ProductDto productDto = new ProductDto(1L, "스테이크", "steak_image_url", 40000, ProductCategory.WESTERN);
        when(productDao.updateById(any(), any())).thenReturn(0);

        // when, then
        assertThatThrownBy(() -> productService.update(1L, productDto))
                .isInstanceOf(GlobalException.class);
    }

    @DisplayName("상품 삭제를 정상적으로 진행한다")
    @Test
    void delete_success() {
        // given
        when(productDao.deleteById(any())).thenReturn(1);

        // when, then
        assertDoesNotThrow(() -> productService.delete(1L));
    }

    @DisplayName("상품 삭제가 실패하면 예외가 발생한다")
    @Test
    void delete_fail() {
        // given
        when(productDao.deleteById(any())).thenReturn(0);

        // when, then
        assertThatThrownBy(() -> productService.delete(1L))
                .isInstanceOf(GlobalException.class);
    }

    @DisplayName("상품을 조회한다")
    @Test
    void getById_success() {
        // given
        final Product product = new Product("스테이크", "steak_image_url", 40000, ProductCategory.WESTERN);
        when(productDao.findById(any())).thenReturn(Optional.of(product));

        // when
        final ProductDto productDto = productService.getById(1L);

        // then
        assertThat(productDto)
                .extracting("name", "price", "imageUrl", "category")
                .contains("스테이크", 40000, "steak_image_url", ProductCategory.WESTERN);
    }

    @DisplayName("존재하지 않는 상품을 조회하면 예외가 발생한다.")
    @Test
    void getById_fail() {
        assertThatThrownBy(() -> productService.getById(1L))
                .isInstanceOf(GlobalException.class);
    }
}
