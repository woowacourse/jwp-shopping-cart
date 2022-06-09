package woowacourse.shoppingcart.domain;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import woowacourse.shoppingcart.domain.customer.Customer;

public class CustomerTest {

    private static final String EMAIL = "leo@woowahan.com";
    private static final String PASSWORD = "Leo1234!";
    private static final String NAME = "leo";
    private static final String PHONE = "010-1234-5678";
    private static final String ADDRESS = "Seoul";

    @Test
    @DisplayName("회원 정보 저장에 성공한다.")
    void createCustomer() {
        assertDoesNotThrow(() -> new Customer(EMAIL, NAME, PHONE, ADDRESS, PASSWORD));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "leo@naver", "@naver.com", "leonaver.com"})
    @DisplayName("올바르지 않은 형식의 이메일을 입력할 경우 예외가 발생한다.")
    void invalidEmail(String email) {
        assertThatThrownBy(() -> new Customer(email, NAME, PHONE, ADDRESS, PASSWORD))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("올바르지 않은 이메일 형식입니다.");
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "abcdefghijklmnopqrustvwxyzabcde"})
    @DisplayName("최대 30자 이하, 최소 1자 이상 범위 외의 이름을 입력할 경우 예외가 발생한다.")
    void invalidName(String name) {
        assertThatThrownBy(() -> new Customer(EMAIL, name, PHONE, ADDRESS, PASSWORD))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("올바르지 않은 이름 형식입니다.");
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "010-123-4567", "010-1234-567", "011-1234-5678", "010--1234", "01012345678"})
    @DisplayName("올바르지 않은 형식의 전화번호를 입력할 경우 예외가 발생한다.")
    void invalidPhoneNumber(String phone) {
        assertThatThrownBy(() -> new Customer(EMAIL, NAME, phone, ADDRESS, PASSWORD))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("올바르지 않은 전화번호 형식입니다.");
    }

    @Test
    @DisplayName("주소에 공백을 입력할 경우 예외가 발생한다.")
    void invalidAddress() {
        assertThatThrownBy(() -> new Customer(EMAIL, NAME, PHONE, "", PASSWORD))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("올바르지 않은 주소 형식입니다.");
    }
}
