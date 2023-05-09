package cart.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ProductRequestDtoTest {

    @Test
    @DisplayName("ProductRequestDto 가 ProductDto 로 변환하는 로직을 테스트한다.")
    void toDto() {
        ProductRequestDto productRequestDto = new ProductRequestDto("hello", 10000, "url");

        ProductDto productDto = productRequestDto.toDto();

        assertThat(productDto.getName()).isEqualTo("hello");
        assertThat(productDto.getPrice()).isEqualTo(10000);
        assertThat(productDto.getImageUrl()).isEqualTo("url");
    }

}
