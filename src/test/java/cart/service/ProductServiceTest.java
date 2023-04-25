package cart.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import cart.dto.request.ProductRequestDto;
import cart.dto.response.ProductResponseDto;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ProductServiceTest {

    @Autowired
    ProductService productService;

    @DisplayName("상품 목록을 조회한다.")
    @Test
    void findProducts() {
        //given
        productService.register(
            new ProductRequestDto("name", "imageUrl", 1000, "description", List.of(1L, 2L))
        );

        //when
        final List<ProductResponseDto> productResponseDtos = productService.findProducts();
        final ProductResponseDto productResponseDto = productResponseDtos.get(0);

        //then
        assertAll(
            () -> assertThat(productResponseDtos).hasSize(1),
            () -> assertThat(productResponseDto.getName()).isEqualTo("name"),
            () -> assertThat(productResponseDto.getImageUrl()).isEqualTo("imageUrl"),
            () -> assertThat(productResponseDto.getPrice()).isEqualTo(1000),
            () -> assertThat(productResponseDto.getDescription()).isEqualTo("description"),
            () -> assertThat(productResponseDto.getCategoryResponseDtos()).hasSize(2),
            () -> assertThat(productResponseDto.getCategoryResponseDtos().get(0).getId()).isEqualTo(1L),
            () -> assertThat(productResponseDto.getCategoryResponseDtos().get(1).getId()).isEqualTo(2L)
        );
    }
}
