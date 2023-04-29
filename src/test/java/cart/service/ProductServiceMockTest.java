package cart.service;

import cart.dao.CategoryDao;
import cart.dao.ProductCategoryDao;
import cart.dao.ProductDao;
import cart.dto.response.ProductResponseDto;
import cart.entity.CategoryEntity;
import cart.entity.ProductCategoryEntity;
import cart.entity.product.ProductEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.BDDMockito.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class ProductServiceMockTest {

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductDao productDao;
    @Mock
    private ProductCategoryDao productCategoryDao;
    @Mock
    private CategoryDao categoryDao;

    @Test
    @DisplayName("상품 목록을 조회한다.")
    void findProducts() {
        final List<ProductEntity> products = List.of(
                new ProductEntity(
                        1L,
                        "name",
                        "imageUrl",
                        1000,
                        "description"
                )
        );
        final List<ProductCategoryEntity> productCategories = List.of(
                new ProductCategoryEntity(1L, 1L),
                new ProductCategoryEntity(1L, 2L)
        );
        final List<CategoryEntity> categories = List.of(
                new CategoryEntity(1L, "카페"),
                new CategoryEntity(2L, "한식")
        );

        given(productDao.findAll()).willReturn(products);
        given(productCategoryDao.findAll(any())).willReturn(productCategories);
        given(categoryDao.findAllInId(any())).willReturn(categories);

        final List<ProductResponseDto> result = productService.findProducts();

        assertAll(
                () -> assertThat(result).hasSize(1),
                () -> assertThat(result.get(0).getId()).isEqualTo(1L),
                () -> assertThat(result.get(0).getName()).isEqualTo("name"),
                () -> assertThat(result.get(0).getImageUrl()).isEqualTo("imageUrl"),
                () -> assertThat(result.get(0).getPrice()).isEqualTo(1000),
                () -> assertThat(result.get(0).getDescription()).isEqualTo("description"),
                () -> assertThat(result.get(0).getCategoryResponseDtos()).hasSize(2),
                () -> assertThat(result.get(0).getCategoryResponseDtos().get(0).getId()).isEqualTo(1L),
                () -> assertThat(result.get(0).getCategoryResponseDtos().get(1).getId()).isEqualTo(2L)
        );
    }
}
