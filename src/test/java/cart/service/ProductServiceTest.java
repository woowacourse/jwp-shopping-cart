package cart.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import cart.dao.CategoryDao;
import cart.dao.ProductCategoryDao;
import cart.dao.ProductDao;
import cart.dto.ProductCategoryDto;
import cart.dto.request.ProductRequestDto;
import cart.entity.CategoryEntity;
import cart.entity.ProductCategoryEntity;
import cart.entity.product.ProductEntity;
import java.util.List;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductDao productDao;

    @Mock
    private CategoryDao categoryDao;

    @Mock
    private ProductCategoryDao productCategoryDao;

    @InjectMocks
    private ProductService productService;

    @Test
    @DisplayName("상품 목록을 조회한다.")
    void findProducts() {
        //given
        final ProductEntity productEntity = new ProductEntity(
            1L,
            "name",
            "imageUrl",
            1000,
            "description"
        );
        final CategoryEntity categoryEntity = new CategoryEntity(1L, "category");

        when(productDao.findAll())
            .thenReturn(List.of(productEntity));
        when(productDao.findById(any()))
            .thenReturn(Optional.of(productEntity));
        when(categoryDao.findAllInId(any()))
            .thenReturn(List.of(categoryEntity));

        //when
        final List<ProductCategoryDto> productCategoryDtos = productService.findAll();

        //then
        verify(productDao).findAll();
        verify(productDao).findById(any());
        verify(categoryDao).findAllInId(any());

        final ProductCategoryDto productCategoryDto = productCategoryDtos.get(0);
        assertAll(
            () -> assertThat(productCategoryDtos).hasSize(1),
            () -> assertThat(productCategoryDto.getId()).isOne(),
            () -> assertThat(productCategoryDto.getName()).isEqualTo(productEntity.getName()),
            () -> assertThat(productCategoryDto.getImageUrl()).isEqualTo(productEntity.getImageUrl()),
            () -> assertThat(productCategoryDto.getPrice()).isEqualTo(productEntity.getPrice()),
            () -> assertThat(productCategoryDto.getDescription()).isEqualTo(productEntity.getDescription()),
            () -> assertThat(productCategoryDto.getCategoryEntities()).hasSize(1),
            () -> assertThat(productCategoryDto.getCategoryEntities().get(0)).isEqualTo(categoryEntity)
        );
    }

    @Test
    @DisplayName("상품 정보를 수정한다.")
    void update() {
        //given
        when(productDao.findById(any())).thenReturn(
            Optional.of(new ProductEntity(
                "name",
                "imageUrl",
                1000,
                "description"
            ))
        );
        when(productCategoryDao.findAll(any())).thenReturn(
            List.of(new ProductCategoryEntity(1L, 1L))
        );

        //when
        productService.update(
            1L, new ProductRequestDto(
                "name2",
                "imageUrl2",
                2000,
                "description2",
                List.of(3L, 4L)
            )
        );

        //then
        verify(productDao).findById(any());
        verify(productDao).update(any());
        verify(productCategoryDao).findAll(any());
        verify(productCategoryDao, times(2)).save(any());
    }

    @Test
    @DisplayName("상품을 삭제한다.")
    void delete() {
        //given
        final ProductEntity productEntity = new ProductEntity(
            "name",
            "imageUrl",
            1000,
            "description"
        );
        when(productDao.findById(any())).thenReturn(
            Optional.of(productEntity)
        );
        when(productCategoryDao.findAll(any())).thenReturn(
            List.of(new ProductCategoryEntity(1L, 1L))
        );

        //when
        productService.delete(1L);

        //then
        final List<ProductCategoryDto> productCategoryDtos = productService.findAll();
        Assertions.assertThat(productCategoryDtos).hasSize(0);
    }
}
