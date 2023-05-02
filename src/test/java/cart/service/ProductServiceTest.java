package cart.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import cart.dto.ProductCategoryDto;
import cart.dto.request.ProductRequestDto;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class ProductServiceTest {

    @Autowired
    ProductService productService;

    @Test
    @DisplayName("상품 목록을 조회한다.")
    void findProducts() {
        //given
        final Long id = productService.register(
            new ProductRequestDto("name", "imageUrl", 1000, "description", List.of(1L, 2L))
        );

        //when
        final List<ProductCategoryDto> productCategoryDtos = productService.findAll();
        final ProductCategoryDto productCategoryDto = productCategoryDtos.get(0);

        //then
        assertAll(
            () -> assertThat(productCategoryDtos).hasSize(1),
            () -> assertThat(productCategoryDto.getId()).isEqualTo(id),
            () -> assertThat(productCategoryDto.getName()).isEqualTo("name"),
            () -> assertThat(productCategoryDto.getImageUrl()).isEqualTo("imageUrl"),
            () -> assertThat(productCategoryDto.getPrice()).isEqualTo(1000),
            () -> assertThat(productCategoryDto.getDescription()).isEqualTo("description"),
            () -> assertThat(productCategoryDto.getCategoryEntities()).hasSize(2),
            () -> assertThat(productCategoryDto.getCategoryEntities().get(0).getId()).isEqualTo(1L),
            () -> assertThat(productCategoryDto.getCategoryEntities().get(1).getId()).isEqualTo(2L)
        );
    }

    @Test
    @DisplayName("상품 정보를 수정한다.")
    void update() {
        //given
        final Long id = productService.register(
            new ProductRequestDto("name", "imageUrl", 1000, "description", List.of(1L, 2L))
        );

        //when
        productService.update(id, new ProductRequestDto("name2", "imageUrl2", 2000, "description2", List.of(3L, 4L)));

        //then
        final List<ProductCategoryDto> productCategoryDtos = productService.findAll();
        final ProductCategoryDto productCategoryDto = productCategoryDtos.get(0);

        assertAll(
            () -> assertThat(productCategoryDtos).hasSize(1),
            () -> assertThat(productCategoryDto.getId()).isEqualTo(id),
            () -> assertThat(productCategoryDto.getName()).isEqualTo("name2"),
            () -> assertThat(productCategoryDto.getImageUrl()).isEqualTo("imageUrl2"),
            () -> assertThat(productCategoryDto.getPrice()).isEqualTo(2000),
            () -> assertThat(productCategoryDto.getDescription()).isEqualTo("description2"),
            () -> assertThat(productCategoryDto.getCategoryEntities()).hasSize(2),
            () -> assertThat(productCategoryDto.getCategoryEntities().get(0).getId()).isEqualTo(3L),
            () -> assertThat(productCategoryDto.getCategoryEntities().get(1).getId()).isEqualTo(4L)
        );
    }

    @Test
    @DisplayName("상품을 삭제한다.")
    void delete() {
        //given
        final Long id = productService.register(
            new ProductRequestDto("name", "imageUrl", 1000, "description", List.of(1L, 2L))
        );

        //when
        productService.delete(id);

        //then
        final List<ProductCategoryDto> productCategoryDtos = productService.findAll();
        Assertions.assertThat(productCategoryDtos).hasSize(0);
    }
}
