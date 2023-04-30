package cart.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ProductTest {

    private static final long ID = 1L;
    private static final String NAME = "name";
    private static final int PRICE = 1000;
    private static final String IMAGE_URL = "htts://domain.com/image";

    @Test
    @DisplayName("이름 검증 테스트")
    void name_validation_test() {
        assertThatThrownBy(() -> new Product(ID, "", PRICE, IMAGE_URL))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("name이 존재해야 합니다.");
    }

    @Test
    @DisplayName("가격 검증 테스트")
    void price_validation_test() {
        assertThatThrownBy(() -> new Product(ID, NAME, -1, IMAGE_URL))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("price는 0 이상의 자연수 여야 합니다.");
    }

    @Test
    @DisplayName("이미지 주소 검증 테스트")
    void imageUrl_validation_test() {
        assertThatThrownBy(() -> new Product(ID, NAME, PRICE, "htt;//"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("imageUrl값이 형식에 맞지 않습니다.");
    }

}
