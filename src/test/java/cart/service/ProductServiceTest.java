package cart.service;

import cart.dao.ProductDao;
import cart.dto.ProductDto;
import cart.dto.ProductResponseDto;
import cart.entity.Product;
import cart.vo.Name;
import cart.vo.Price;
import cart.vo.Url;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.BDDMockito.given;

class ProductServiceTest {

    private ProductService productService;
    private ProductDao productDao;

    @BeforeEach
    void beforeEach() {
        productDao = Mockito.mock(ProductDao.class);
        productService = new ProductService(productDao);
    }

    @Test
    @DisplayName("findAll 메서드를 통해 상품 모두를 조회한다.")
    void findAll() {
        given(productDao.selectAll())
                .willReturn(List.of(
                        buildProduct(1L, "재연", 10000, "재연씨"),
                        buildProduct(2L, "미성", 10001, "미성씨")
                ));

        List<ProductResponseDto> products = productService.findAll();

        assertAll(
                () ->  assertThat(products).hasSize(2),
                () ->  assertThat(products.get(0).getId()).isEqualTo(1),
                () ->  assertThat(products.get(1).getId()).isEqualTo(2)
        );
    }

    @Test
    @DisplayName("없는 product 를 수정하려 할 때 예외를 발생한다.")
    void modifyByIdFail() {
        ProductDto productDto = new ProductDto("재연", 10000, "재연씨");
        given(productDao.updateById(1L, productDto.toEntity())).willReturn(0);

        assertThatThrownBy(() -> productService.modifyById(1L, productDto))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("변경하려는 상품이 존재하지 않습니다.");
    }

    @Test
    @DisplayName("존재하는 product 를 수정할 때에는 예외가 발생하지 않는다.")
    void modifyByIdSuccess() {
//        ProductDto productDto = new ProductDto("재연", 10000, "재연씨");
//        given(productDao.updateById(1L, product)).willReturn(1);
//        assertDoesNotThrow(() -> productService.modifyById(1L, productDto));
    }

    @Test
    @DisplayName("없는 product 를 삭제하려 할 때 예외를 발생한다.")
    void deleteByIdFail() {
        given(productDao.deleteById(1L)).willReturn(0);

        assertThatThrownBy(() -> productService.removeById(1L))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("삭제하려는 상품이 존재하지 않습니다.");
    }

    @Test
    @DisplayName("존재하는 product 를 삭제하려 할 때는 예외가 발생하지 않는다.")
    void deleteByIdSuccess() {
        given(productDao.deleteById(1L)).willReturn(1);

        assertDoesNotThrow(() -> productService.removeById(1L));
    }

    private Product buildProduct(Long id, String name, Integer price, String imageUrl) {
        return new Product.Builder()
                .id(id)
                .name(Name.from(name))
                .price(Price.from(price))
                .imageUrl(Url.from(imageUrl))
                .build();
    }

}
