package woowacourse.shoppingcart.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class UserNameTest {

    @ParameterizedTest
    @CsvSource({"1234,false", "12345,true", "aaaaaaaaaaaaaaaaaaaa,true", "1_2_3_4_5_6_7a,true", " a,false"})
    @DisplayName("유저 이름을 테스트한다.")
    void validateTest(String userName, boolean result) {
        if (result) {
            assertDoesNotThrow(() -> {
                new UserName(userName);
            });
            return;
        }
        assertThatThrownBy(() -> {
            new UserName(userName);
        }).isInstanceOf(IllegalArgumentException.class);
    }
}
