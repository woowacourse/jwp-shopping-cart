package cart.service;

import cart.dao.ProductDao;
import cart.dto.ProductResponseDto;
import cart.entity.Product;
import cart.vo.Name;
import cart.vo.Price;
import cart.vo.Url;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
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

    private Product buildProduct(Long id, String name, Integer price, String imageUrl) {
        return new Product.Builder()
                .id(id)
                .name(Name.from(name))
                .price(Price.from(price))
                .imageUrl(Url.from(imageUrl))
                .build();
    }

}
