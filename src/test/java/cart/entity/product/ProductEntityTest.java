package cart.entity.product;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class ProductEntityTest {

    @Test
    @DisplayName("상품 정보를 조회한다.")
    void getInfo() {
        //given
        final ProductEntity productEntity = new ProductEntity(1L, "name", "imageUrl", 1000, "description");

        //when
        //then
        assertAll(
                () -> assertThat(productEntity.getId()).isEqualTo(1L),
                () -> assertThat(productEntity.getName()).isEqualTo("name"),
                () -> assertThat(productEntity.getImageUrl()).isEqualTo("imageUrl"),
                () -> assertThat(productEntity.getPrice()).isEqualTo(1000),
                () -> assertThat(productEntity.getDescription()).isEqualTo("description")
        );
    }
}
