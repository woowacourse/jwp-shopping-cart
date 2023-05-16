package cart.entity;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import cart.exception.DomainException;

class MemberTest {
    @Test
    @DisplayName("상품 엔티티를 생성한다.")
    void member() {
        assertDoesNotThrow(() -> Member.of(1L, "jaeyoun22@gmail.com", "1234"));
    }

    @ParameterizedTest
    @MethodSource("makeMember")
    @DisplayName("잘못된 값을 검증한다")
    void invalidProductTest(Long id, String email, String password) {
        assertThatThrownBy(() -> Member.of(id, email, password))
            .isInstanceOf(DomainException.class);

    }

    static Stream<Arguments> makeMember() {
        return Stream.of(Arguments.of(1L, "a", "asdf"),
            Arguments.of(1L, "jaeyoung22@gmail.com", ""));
    }
}
