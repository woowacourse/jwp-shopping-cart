package woowacourse.shoppingcart.domain.customer;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import woowacourse.shoppingcart.domain.customer.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
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
        assertDoesNotThrow(() -> new Customer(new Email(EMAIL), new Name(NAME), new Phone(PHONE), new Address(ADDRESS), Password.of(PASSWORD)));
    }

    @ValueSource(strings = {"", "leo@naver", "@naver.com", "leonaver.com"})
    @ParameterizedTest
    void invalidEmail(String email) {
        assertThatThrownBy(() -> new Customer(new Email(email), new Name(NAME), new Phone(PHONE), new Address(ADDRESS), Password.of(PASSWORD)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("올바르지 않은 이메일 형식입니다.");
    }

    @ValueSource(strings = {"", "abcdefghijklmnopqrustvwxyzabcde"})
    @ParameterizedTest
    void invalidName(String name) {
        assertThatThrownBy(() -> new Customer(new Email(EMAIL), new Name(name), new Phone(PHONE), new Address(ADDRESS), Password.of(PASSWORD)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("올바르지 않은 이름 형식입니다.");
    }

    @ValueSource(strings = {"", "010-123-4567", "010-1234-567", "011-1234-5678", "010--1234", "01012345678"})
    @ParameterizedTest
    void invalidPhoneNumber(String phone) {
        assertThatThrownBy(() -> new Customer(new Email(EMAIL), new Name(NAME), new Phone(phone), new Address(ADDRESS), Password.of(PASSWORD)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("올바르지 않은 전화번호 형식입니다.");
    }

    @Test
    void invalidAddress() {
        assertThatThrownBy(() -> new Customer(new Email(EMAIL), new Name(NAME), new Phone(PHONE), new Address(""), Password.of(PASSWORD)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("올바르지 않은 주소 형식입니다.");
    }

    @Test
    void isSamePassword() {
        Customer customer = new Customer(new Email(EMAIL), new Name(NAME), new Phone(PHONE), new Address(ADDRESS), Password.of(PASSWORD));
        final boolean result = customer.isSame(Password.of(PASSWORD));

        assertThat(result).isTrue();
    }
}
