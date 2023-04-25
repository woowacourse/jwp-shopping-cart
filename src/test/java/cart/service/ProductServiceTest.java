package cart.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import cart.dto.request.ProductRequestDto;
import cart.dto.response.ProductResponseDto;
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
        final List<ProductResponseDto> productResponseDtos = productService.findProducts();
        final ProductResponseDto productResponseDto = productResponseDtos.get(0);

        //then
        assertAll(
            () -> assertThat(productResponseDtos).hasSize(1),
            () -> assertThat(productResponseDto.getId()).isEqualTo(id),
            () -> assertThat(productResponseDto.getName()).isEqualTo("name"),
            () -> assertThat(productResponseDto.getImageUrl()).isEqualTo("imageUrl"),
            () -> assertThat(productResponseDto.getPrice()).isEqualTo(1000),
            () -> assertThat(productResponseDto.getDescription()).isEqualTo("description"),
            () -> assertThat(productResponseDto.getCategoryResponseDtos()).hasSize(2),
            () -> assertThat(productResponseDto.getCategoryResponseDtos().get(0).getId()).isEqualTo(1L),
            () -> assertThat(productResponseDto.getCategoryResponseDtos().get(1).getId()).isEqualTo(2L)
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
        final List<ProductResponseDto> productResponseDtos = productService.findProducts();
        final ProductResponseDto productResponseDto = productResponseDtos.get(0);

        assertAll(
            () -> assertThat(productResponseDtos).hasSize(1),
            () -> assertThat(productResponseDto.getId()).isEqualTo(id),
            () -> assertThat(productResponseDto.getName()).isEqualTo("name2"),
            () -> assertThat(productResponseDto.getImageUrl()).isEqualTo("imageUrl2"),
            () -> assertThat(productResponseDto.getPrice()).isEqualTo(2000),
            () -> assertThat(productResponseDto.getDescription()).isEqualTo("description2"),
            () -> assertThat(productResponseDto.getCategoryResponseDtos()).hasSize(2),
            () -> assertThat(productResponseDto.getCategoryResponseDtos().get(0).getId()).isEqualTo(3L),
            () -> assertThat(productResponseDto.getCategoryResponseDtos().get(1).getId()).isEqualTo(4L)
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
        final List<ProductResponseDto> productResponseDtos = productService.findProducts();
        Assertions.assertThat(productResponseDtos).hasSize(0);
    }
}
