package woowacourse.auth.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class AddressTest {
    
    @ParameterizedTest(name = "주소 : {0}")
    @ValueSource(strings = {" 호호네 ", " 호호네", "호호네 "})
    void 앞뒤_공백_제거_후_생성(String value) {
        Address address = new Address(value);
        Address expectedAddress = new Address("호호네");

        assertThat(address).isEqualTo(expectedAddress);
    }
}
