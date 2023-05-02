package cart.dto;

import cart.entity.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ProductRequestDtoTest {

    @Test
    @DisplayName("Product로 변환하는 로직을 테스트한다.")
    void toEntity() {
        ProductRequestDto productRequestDto = new ProductRequestDto("hello", 10000, "url");

        Product product = productRequestDto.toEntity();

        assertThat(product.getName()).isEqualTo("hello");
        assertThat(product.getPrice()).isEqualTo(10000);
        assertThat(product.getImageUrl()).isEqualTo("url");
    }

}
