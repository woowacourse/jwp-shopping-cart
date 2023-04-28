package cart.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class ProductCategoryEntityTest {

    @DisplayName("상품, 카테고리 맵핑테이블에서 상품과 카테고리의 ID를 조회한다.")
    @Test
    void getInfo() {
        //given
        final ProductCategoryEntity productCategoryEntity = new ProductCategoryEntity(1L, 1L);

        //when
        //then
        assertAll(
                () -> assertThat(productCategoryEntity.getProductId()).isEqualTo(1L),
                () -> assertThat(productCategoryEntity.getCategoryId()).isEqualTo(1L)
        );
    }
}
