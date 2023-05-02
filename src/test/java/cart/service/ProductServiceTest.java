package cart.service;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

import cart.dao.ProductDao;
import cart.dto.ProductDto;
import cart.entity.ProductEntity;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.jdbc.Sql;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@Sql("classpath:schema.sql")
@MockitoSettings
class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @MockBean
    private ProductDao productDao;

    @Test
    void 모든_상품_목록_조회() {
        Mockito.when(productDao.findAll()).thenReturn(List.of(
                new ProductEntity(1L, "name1", 1000, "image1"),
                new ProductEntity(2L, "name2", 2000, "image2"),
                new ProductEntity(3L, "name3", 3000, "image3"))
        );

        final var products = productService.findAll();

        assertThat(products.size()).isEqualTo(3);
    }

    @Test
    void 상품_등록() {
        Mockito.when(productDao.insert(any(ProductEntity.class)))
                .thenReturn(4L);

        final var savedId = productService.register(new ProductDto("item1", 1000, "https://"));

        assertThat(savedId).isEqualTo(4L);
    }

    @Test
    void 상품_수정_성공() {
        Mockito.when(productDao.findById(anyLong()))
                .thenReturn(Optional.of(new ProductEntity(1L, "name1", 1000, "image1")));

        assertThatNoException().isThrownBy(() -> productService.updateProduct(1L, new ProductDto("new Name", 10, "new Image Url")));
    }

    @Test
    void 존재하지_않는_ID의_상품을_수정시_예외_발생() {
        Mockito.when(productDao.findById(anyLong()))
                .thenReturn(Optional.empty());

        assertThatIllegalArgumentException().isThrownBy(
                () -> productService.updateProduct(1L, new ProductDto("new Name", 10, "new Image Url"))
        );
    }

    @Test
    void 상품_삭제_성공() {
        Mockito.when(productDao.findById(anyLong()))
                .thenReturn(Optional.of(new ProductEntity(1L, "name1", 1000, "image1")));

        assertThatNoException().isThrownBy(() -> productService.deleteProduct(1L));
    }

    @Test
    void 존재하지_않는_상품_삭제시_예외_발생() {
        Mockito.when(productDao.findById(anyLong()))
                .thenReturn(Optional.empty());

        assertThatIllegalArgumentException().isThrownBy(
                () -> productService.deleteProduct(3L)
        );
    }
}
