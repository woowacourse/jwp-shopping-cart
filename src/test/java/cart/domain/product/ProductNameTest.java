package cart.domain.product;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import cart.exception.GlobalException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class ProductNameTest {

    @ParameterizedTest(name = "상품 이름 생성 테스트")
    @ValueSource(strings = {"로이스가 해주는 라면", "민트초코 김치", "딸기 김치찌개", "까망베르 청국장"})
    void createProductName(String input) {
        assertDoesNotThrow(() -> ProductName.from(input));
    }

    @ParameterizedTest(name = "상품 이름은 공백으로만 이루어질 수 없다.")
    @ValueSource(strings = {"", "   "})
    void createProductNameFailureBlank(String input) {
        assertThatThrownBy(() -> ProductName.from(input))
                .isInstanceOf(GlobalException.class);
    }

    @ParameterizedTest(name = "상품 이름은 20자를 넘을 수 없다.")
    @ValueSource(strings = {"0123456789012345678900", "일이삼사오육칠팔구십일이삼사오육칠팔구십일"})
    void createProductNameFailureOverMaxLength(String input) {
        assertThatThrownBy(() -> ProductName.from(input))
                .isInstanceOf(GlobalException.class);
    }
}
