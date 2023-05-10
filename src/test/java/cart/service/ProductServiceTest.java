package cart.service;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

import cart.dao.CartDao;
import cart.dao.ProductDao;
import cart.dto.ProductDto;
import cart.entity.ProductEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.jdbc.Sql;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
@Sql("classpath:schema.sql")
class ProductServiceTest {

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductDao productDao;
    @Mock
    private CartDao cartDao;

    @Test
    void 모든_상품_목록_조회() {
        Mockito.when(productDao.findAll()).thenReturn(List.of(
                createProductEntity(1L),
                createProductEntity(2L),
                createProductEntity(3L))
        );

        final var products = productService.findAll();

        assertThat(products.size()).isEqualTo(3);
    }

    @Test
    void 상품_등록() {
        Mockito.when(productDao.insert(any(ProductEntity.class)))
                .thenReturn(4L);

        final var savedId = productService.register(createProductDto(1000));

        assertThat(savedId).isEqualTo(4L);
    }

    @Test
    void 상품_수정_성공() {
        Mockito.when(productDao.findById(anyLong()))
                .thenReturn(Optional.of(createProductEntity(1L)));

        assertThatNoException().isThrownBy(() -> productService.updateProduct(1L, new ProductDto("new Name", 10, "new Image Url")));
    }

    @Test
    void 존재하지_않는_ID의_상품을_수정시_예외_발생() {
        Mockito.when(productDao.findById(anyLong()))
                .thenReturn(Optional.empty());

        assertThatIllegalArgumentException().isThrownBy(
                () -> productService.updateProduct(1L, createProductDto(10))
        );
    }

    @Test
    void 상품_삭제_성공() {
        Mockito.when(productDao.findById(anyLong()))
                .thenReturn(Optional.of(createProductEntity(1L)));

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

    private ProductEntity createProductEntity(final long id) {
        return new ProductEntity(id, "name", 1000, "imageUrl");
    }

    private ProductDto createProductDto(final int price) {
        return new ProductDto("name", price, "imageUrl");
    }
}
