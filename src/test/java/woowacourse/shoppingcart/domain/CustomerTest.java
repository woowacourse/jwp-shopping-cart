package woowacourse.shoppingcart.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class CustomerTest {

    private static final String EMAIL = "test@email.com";
    private static final String PASSWORD = "1234567890";
    private static final String ADDRESS = "서울 강남구 테헤란로 411, 성담빌딩 13층 (선릉 캠퍼스)";
    private static final String PHONE_NUMBER = "010-0000-0000";

    @DisplayName("고객을 생성한다.")
    @Test
    void createCustomer() {
        assertDoesNotThrow(() -> new Customer(EMAIL, PASSWORD, ADDRESS, PHONE_NUMBER));
    }

    @DisplayName("고객 생성을 위해 필요한 데이터가 null인 경우 예외를 던진다.")
    @ParameterizedTest
    @MethodSource("generateInvalidCustomer")
    void createCustomer_error_null(String email, String password, String address, String phoneNumber) {
        assertThatThrownBy(() -> new Customer(email, password, address, phoneNumber))
                .isInstanceOf(NullPointerException.class);
    }

    private static Stream<Arguments> generateInvalidCustomer() {
        return Stream.of(
                Arguments.of(null, PASSWORD, ADDRESS, PHONE_NUMBER),
                Arguments.of(EMAIL, null, ADDRESS, PHONE_NUMBER),
                Arguments.of(EMAIL, PASSWORD, null, PHONE_NUMBER),
                Arguments.of(EMAIL, PASSWORD, ADDRESS, null)
        );
    }
}
