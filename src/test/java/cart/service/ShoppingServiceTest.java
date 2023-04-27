package cart.service;

import cart.controller.dto.ProductDto;
import cart.exception.GlobalException;
import cart.persistence.dao.ProductDao;
import cart.persistence.entity.Product;
import cart.persistence.entity.ProductCategory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebMvcTest(ShoppingService.class)
class ShoppingServiceTest {

    private ProductDto productDto;

    @MockBean
    private ProductDao productDao;

    @Autowired
    private ShoppingService shoppingService;

    @BeforeEach
    void setUp() {
        productDto = new ProductDto(1L, "스테이크", "steakUrl", 40000, ProductCategory.WESTERN);
    }

    @DisplayName("전체 상품을 조회한다")
    @Test
    void getProducts() {
        // given
        final List<Product> products = List.of(
                new Product("치킨", "chickenUrl", 20000, ProductCategory.KOREAN),
                new Product("초밥", "chobobUrl", 30000, ProductCategory.JAPANESE),
                new Product("스테이크", "steakUrl", 40000, ProductCategory.WESTERN)
        );
        when(productDao.findAll()).thenReturn(products);

        // when
        final List<ProductDto> resultProducts = shoppingService.getProducts();

        // then
        assertAll(
                () -> assertThat(resultProducts).hasSize(3),
                () -> assertThat(resultProducts.get(0).getName()).isEqualTo("치킨"),
                () -> assertThat(resultProducts.get(1).getName()).isEqualTo("초밥"),
                () -> assertThat(resultProducts.get(2).getName()).isEqualTo("스테이크")
        );
    }

    @DisplayName("상품을 저장한다")
    @Test
    void save() {
        // given
        final List<Product> products = List.of(new Product("스테이크", "steakUrl", 40000, ProductCategory.WESTERN));
        when(productDao.insert(any())).thenReturn(1L);
        when(productDao.findAll()).thenReturn(products);

        // when
        shoppingService.save(productDto);

        // then
        final List<ProductDto> resultProducts = shoppingService.getProducts();
        assertAll(
                () -> assertThat(resultProducts).hasSize(1),
                () -> assertThat(resultProducts.get(0).getName()).isEqualTo("스테이크")
        );
    }

    @DisplayName("상품 수정을 정상적으로 진행한다")
    @Test
    void update_success() {
        // given
        when(productDao.update(any())).thenReturn(1);

        // when, then
        assertDoesNotThrow(() -> shoppingService.update(1L, productDto));
    }

    @DisplayName("상품 수정이 실패하면 예외가 발생한다")
    @Test
    void update_fail() {
        // given
        when(productDao.update(any())).thenReturn(0);

        // when, then
        assertThatThrownBy(() -> shoppingService.update(1L, productDto))
                .isInstanceOf(GlobalException.class);
    }

    @DisplayName("상품 삭제를 정상적으로 진행한다")
    @Test
    void delete_success() {
        // given
        when(productDao.deleteById(any())).thenReturn(1);

        // when, then
        assertDoesNotThrow(() -> shoppingService.delete(1L));
    }

    @DisplayName("상품 삭제가 실패하면 예외가 발생한다")
    @Test
    void delete_fail() {
        // given
        when(productDao.deleteById(any())).thenReturn(0);

        // when, then
        assertThatThrownBy(() -> shoppingService.delete(1L))
                .isInstanceOf(GlobalException.class);
    }

    @DisplayName("상품을 조회한다")
    @Test
    void getById_success() {
        // given
        final Product product = new Product("스테이크", "steakUrl", 40000, ProductCategory.WESTERN);
        when(productDao.findById(any())).thenReturn(Optional.of(product));

        // when
        final ProductDto productDto = shoppingService.getById(1L);

        // then
        assertAll(
                () -> assertThat(productDto.getName()).isEqualTo("스테이크"),
                () -> assertThat(productDto.getImageUrl()).isEqualTo("steakUrl"),
                () -> assertThat(productDto.getPrice()).isEqualTo(40000),
                () -> assertThat(productDto.getCategory()).isEqualTo(ProductCategory.WESTERN)
        );
    }

    @DisplayName("존재하지 않는 상품을 조회하면 예외가 발생한다.")
    @Test
    void getById_fail() {
        assertThatThrownBy(() -> shoppingService.getById(1L))
                .isInstanceOf(GlobalException.class);
    }
}
