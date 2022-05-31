package woowacourse.shoppingcart.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class CustomerTest {

    @DisplayName("비밀번호가 일치하는지 확인한다.")
    @ParameterizedTest
    @CsvSource(value = {"12345678:true", "1312321312323:false"}, delimiter = ':')
    void isSamePassword(final String password, final boolean expected) {
        // given
        final Customer customer = new Customer("클레이", "clay@gmail.com", "12345678");

        // when
        final boolean actual = customer.isSamePassword(password);

        // then
        assertThat(actual).isEqualTo(expected);
    }
}
