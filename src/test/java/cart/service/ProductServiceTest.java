package cart.service;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

import cart.domain.Product;
import cart.dto.ProductDto;
import cart.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.jdbc.Sql;
import java.util.List;

@SpringBootTest
@Sql("classpath:schema.sql")
@MockitoSettings
class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @MockBean
    private ProductRepository productRepository;

    @Test
    void 모든_상품_목록_조회() {
        Mockito.when(productRepository.findAll())
                .thenReturn(List.of(
                        new Product("item1", 1000, "https://"),
                        new Product("item1", 1000, "https://"),
                        new Product("item1", 1000, "https://")
                ));

        final var products = productService.findAll();

        assertThat(products.size()).isEqualTo(3);
    }

    @Test
    void 상품_등록() {
        Mockito.when(productRepository.insert(any(Product.class)))
                .thenReturn(4L);

        final var savedId = productService.register(new ProductDto("item1", 1000, "https://"));

        assertThat(savedId).isEqualTo(4L);
    }

    @Test
    void 상품_수정_성공() {
        Mockito.when(productRepository.isExist(anyLong()))
                .thenReturn(true);

        assertThatNoException().isThrownBy(
                () -> productService.updateProduct(1L, new ProductDto("new Name", 10, "new Image Url"))
        );
    }

    @Test
    void 존재하지_않는_ID의_상품을_수정시_예외_발생() {
        Mockito.when(productRepository.isExist(anyLong()))
                .thenReturn(false);

        assertThatIllegalArgumentException().isThrownBy(
                () -> productService.updateProduct(1L, new ProductDto("new Name", 10, "new Image Url"))
        );
    }

    @Test
    void 상품_삭제_성공() {
        Mockito.when(productRepository.isExist(anyLong()))
                .thenReturn(true);

        assertThatNoException().isThrownBy(
                () -> productService.deleteProduct(1L)
        );
    }

    @Test
    void 존재하지_않는_상품_삭제시_예외_발생() {
        Mockito.when(productRepository.isExist(anyLong()))
                .thenReturn(false);

        assertThatIllegalArgumentException().isThrownBy(
                () -> productService.deleteProduct(3L)
        ).withMessage("존재하지 않는 id 입니다.");
    }
}
