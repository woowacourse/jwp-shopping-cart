package woowacourse.shoppingcart.domain;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

class CustomerTest {

    private static final String USERNAME = "test";
    private static final String EMAIL = "test@email.com";
    private static final String PASSWORD = "1234567890";
    private static final String ADDRESS = "서울 강남구 테헤란로 411, 성담빌딩 13층 (선릉 캠퍼스)";
    private static final String PHONE_NUMBER = "010-0000-0000";

    @DisplayName("고객을 생성한다.")
    @Test
    void createCustomer() {
        assertDoesNotThrow(() -> new Customer(USERNAME, EMAIL, PASSWORD, ADDRESS, PHONE_NUMBER));
    }

    @DisplayName("고객 생성을 위해 필요한 데이터가 null인 경우 예외를 던진다.")
    @ParameterizedTest
    @MethodSource("generateInvalidCustomer")
    void createCustomer_error_null(String email, String password, String address, String phoneNumber) {
        assertThatThrownBy(() -> new Customer(USERNAME, email, password, address, phoneNumber))
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

    @DisplayName("이메일 형식이 맞지 않으면 예외를 던진다.")
    @ParameterizedTest
    @ValueSource(strings = {"@email.com", "test@", "testemail.com", "test@email", "email"})
    void createCustomer_error_emailFormat(String email) {
        assertThatThrownBy(() -> new Customer(USERNAME, email, PASSWORD, ADDRESS, PHONE_NUMBER))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("비밀번호 형식이 맞지 않으면 예외를 던진다.")
    @ParameterizedTest
    @ValueSource(strings = {"123456789", "123456789012345678901"})
    void createCustomer_error_passwordFormat(String password) {
        assertThatThrownBy(() -> new Customer(USERNAME, EMAIL, password, ADDRESS, PHONE_NUMBER))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("전화번호 형식이 맞지 않으면 예외를 던진다.")
    @ParameterizedTest
    @ValueSource(strings = {"0000-0000-0000", "-0000-0000", "000-0000", "0000"})
    void createCustomer_error_phoneNumberFormat(String phoneNumber) {
        assertThatThrownBy(() -> new Customer(USERNAME, EMAIL, PASSWORD, ADDRESS, phoneNumber))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
