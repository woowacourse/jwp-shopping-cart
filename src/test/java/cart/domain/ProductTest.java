package cart.domain;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import cart.exception.custom.ArgumentNotValidException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

class ProductTest {

    @Test
    @DisplayName("product를 정상적으로 생성한다")
    void create_success() {
        //when && then
        assertThatNoException().isThrownBy(() -> new Product("name", 1000, "testUrl"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "123456789012345678901"})
    @DisplayName("상품 이름의 길이가 1자 이상 20자 이하가 아닌 경우 예외가 발생한다.")
    void create_fail_by_name_length(String wrongValue) {
        //when && then
        assertThatThrownBy(() -> new Product(wrongValue, 1000, "test"))
                .isInstanceOf(ArgumentNotValidException.class)
                .hasMessage("상품의 이름은 1자 이상, 20자 이하입니다.");
    }

    @ParameterizedTest
    @ValueSource(ints = {1050, 1150})
    @DisplayName("상품 가격이 100원 단위가 아닌경우 예외가 발생한다")
    void create_fail_by_unit_of_price(int price) {
        //when && then
        assertThatThrownBy(() -> new Product("귤", price, "test"))
                .isInstanceOf(ArgumentNotValidException.class)
                .hasMessage("상품의 가격 단위는 100원 단위입니다.");
    }

    @ParameterizedTest
    @ValueSource(ints = {999, 0, -1000})
    @DisplayName("상품의 가격은 1000원 이상이여야한다.")
    void create_fail_by_range_of_price(int price) {
        //when && then
        assertThatThrownBy(() -> new Product("귤", price, "test"))
                .isInstanceOf(ArgumentNotValidException.class)
                .hasMessage("상품의 최소 가격은 1000원 이상입니다.");
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("이미지 URL이 빈값이면 예외를 반환한다.")
    void create_fail_by_wrong_image_url(String wrongUrl) {
        //when && then
        assertThatThrownBy(() -> new Product("귤", 1000, wrongUrl))
                .isInstanceOf(ArgumentNotValidException.class)
                .hasMessage("이미지 URL은 비어있을 수 없습니다.");
    }
}

