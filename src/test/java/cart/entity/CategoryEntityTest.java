package cart.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class CategoryEntityTest {

    @Test
    @DisplayName("카테고리 정보를 조회한다.")
    void getInfo() {
        //given
        final CategoryEntity categoryEntity = new CategoryEntity(1L, "한식");

        //when
        //then
        assertAll(
                () -> assertThat(categoryEntity.getId()).isEqualTo(1L),
                () -> assertThat(categoryEntity.getName()).isEqualTo("한식")
        );
    }
}
