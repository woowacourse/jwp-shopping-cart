package woowacourse.shoppingcart.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

public class CustomerTest {

    private static final String EMAIL = "leo@woowahan.com";
    private static final String PASSWORD = "Leo1234!";
    private static final String NAME = "leo";
    private static final String PHONE = "010-1234-5678";
    private static final String ADDRESS = "Seoul";

    @Test
    void createCustomer() {
        assertDoesNotThrow(() -> new Customer(EMAIL, NAME, PHONE, ADDRESS, PASSWORD));
    }

    @ValueSource(strings = {"", "leo@naver", "@naver.com", "leonaver.com"})
    @ParameterizedTest
    void invalidEmail(String email) {
        assertThatThrownBy(() -> new Customer(email, NAME, PHONE, ADDRESS, PASSWORD))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("올바르지 않은 이메일 형식입니다.");
    }

    @ValueSource(strings = {"", "Bani12!", "Banileobanileo12!", "Banileobanileo!", "B12345678!", "Banileo1234"})
    @ParameterizedTest
    void invalidPassword(String password) {
        assertThatThrownBy(() -> new Customer(EMAIL, NAME, PHONE, ADDRESS, password))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("올바르지 않은 비밀번호 형식입니다.");
    }

    @ValueSource(strings = {"", "abcdefghijklmnopqrustvwxyzabcde"})
    @ParameterizedTest
    void invalidName(String name) {
        assertThatThrownBy(() -> new Customer(EMAIL, name, PHONE, ADDRESS, PASSWORD))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("올바르지 않은 이름 형식입니다.");
    }

    @ValueSource(strings = {"", "010-123-4567", "010-1234-567", "011-1234-5678", "010--1234", "01012345678"})
    @ParameterizedTest
    void invalidPhoneNumber(String phone) {
        assertThatThrownBy(() -> new Customer(EMAIL, NAME, phone, ADDRESS, PASSWORD))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("올바르지 않은 전화번호 형식입니다.");
    }

    @Test
    void invalidAddress() {
        assertThatThrownBy(() -> new Customer(EMAIL, NAME, PHONE, "", PASSWORD))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("올바르지 않은 주소 형식입니다.");
    }
}
